package com.duplo.b2bplatform.service.impl;

import com.duplo.b2bplatform.dataaccess.BusinessRepository;
import com.duplo.b2bplatform.datatransfer.BusinessRecord;
import com.duplo.b2bplatform.datatransfer.DepartmentRecord;
import com.duplo.b2bplatform.datatransfer.GenericResponse;
import com.duplo.b2bplatform.datatransfer.UserRecord;
import com.duplo.b2bplatform.domain.onboarding.*;
import com.duplo.b2bplatform.service.B2BService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class B2BServiceImpl implements B2BService {

    private final BusinessRepository businessRepository;
    @Override
    public GenericResponse<BusinessRecord> openBusinessAccount(BusinessRecord businessRecord) {
        var business = new Business();
        business.setBusinessId(UUID.randomUUID());
        business.setBusinessName(businessRecord.businessName());
        business.setBusinessEmail(businessRecord.businessEmail());

        var staff = new User();
        staff.setEmail(businessRecord.email());
        staff.setFirstName(businessRecord.firstName());
        staff.setLastName(businessRecord.lastName());
        staff.setMobilePhone(businessRecord.phoneNumber());
        staff.setEmploymentType(EmploymentType.OWNER);

        var department = new Department();
        department.setDepartmentName(businessRecord.departmentName());
        department.setDepartmentDescription(businessRecord.departmentDescription());

        business.addUserToBusiness(staff);
        business.addDepartmentToBusiness(department);
        business = businessRepository.save(business);

        var requestResponse = new GenericResponse<BusinessRecord>();
        var bRecord = new BusinessRecord(business.getBusinessName(), business.getBusinessEmail(), staff.getFirstName(),
                staff.getLastName(),staff.getEmail(),staff.getMobilePhone(), department.getDepartmentName(),
                businessRecord.departmentDescription(), business.getId(), String.valueOf(business.getBusinessId()),"");
        requestResponse.setData(bRecord);
        requestResponse.setMessage(String.format("%s business account was successfully created", business.getBusinessName()));
        return requestResponse;
    }

    @Override
    public GenericResponse<UserRecord> addStaffToABusiness(Integer id, UserRecord userRecord) {
        var queriedBusiness = getByEntityId(id);
        var foundBusiness = queriedBusiness.orElseGet(queriedBusiness::orElseThrow);

        var newStaff = new User(Title.Mr, userRecord.email(), userRecord.firstName(), userRecord.lastName(), userRecord.phoneNumber(),
                EmploymentType.STAFF);
        foundBusiness.addUserToBusiness(newStaff);
        foundBusiness = businessRepository.save(foundBusiness);

        var requestResponse = new GenericResponse<UserRecord>();
        var uRecord = new UserRecord(newStaff.getTitle().toString(), newStaff.getEmail(), newStaff.getFirstName(), newStaff.getLastName(),
                newStaff.getMobilePhone(), newStaff.getEmploymentType().toString(), foundBusiness.getId(), foundBusiness.getBusinessId().toString(),
                "", "");
        requestResponse.setData(uRecord);
        requestResponse.setMessage(String.format("%s %s has been created as a staff of %s business account", newStaff.getFirstName(), newStaff.getLastName(),
                foundBusiness.getBusinessName()));
        return requestResponse;
    }

    @Override
    public GenericResponse<DepartmentRecord> createDepartmentForABusiness(Integer id, DepartmentRecord departmentRecord) {
        var queriedBusiness = getByEntityId(id);
        var foundBusiness = queriedBusiness.orElseGet(queriedBusiness::orElseThrow);

        var newDepartment = new Department(departmentRecord.departmentName(), departmentRecord.departmentEmail(),
                departmentRecord.description());
        foundBusiness.addDepartmentToBusiness(newDepartment);

        var requestResponse = new GenericResponse<DepartmentRecord>();
        var dRecord = new DepartmentRecord(newDepartment.getDepartmentName(), newDepartment.getDepartmentDescription(),
                newDepartment.getDepartmentEmail(), String.valueOf(foundBusiness.getBusinessId()), foundBusiness.getId(),
                "");

        requestResponse.setData(dRecord);
        requestResponse.setMessage(String.format("%s has been successfully created under %s business account",
                newDepartment.getDepartmentName(), foundBusiness.getBusinessName()));
        return requestResponse;
    }

    @Override
    public Optional<Business> getByEntityId(Integer id) {
        return businessRepository.findById(id);
    }

    @Override
    public Optional<Business> getByBusinessId(String businessId) {
        var bId = UUID.fromString(businessId);
        return businessRepository.findBusinessByBusinessId(bId);
    }

}
