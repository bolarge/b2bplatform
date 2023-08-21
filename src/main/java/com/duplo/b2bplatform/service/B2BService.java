package com.duplo.b2bplatform.service;

import com.duplo.b2bplatform.datatransfer.BusinessRecord;
import com.duplo.b2bplatform.datatransfer.DepartmentRecord;
import com.duplo.b2bplatform.datatransfer.GenericResponse;
import com.duplo.b2bplatform.datatransfer.UserRecord;
import com.duplo.b2bplatform.domain.onboarding.Business;

import java.util.Optional;
import java.util.UUID;

public interface B2BService {

    //Business Object Operations
    GenericResponse<BusinessRecord> openBusinessAccount(BusinessRecord businessRecord);
    GenericResponse<UserRecord> addStaffToABusiness(Integer id, UserRecord userRecord);
    GenericResponse<DepartmentRecord> createDepartmentForABusiness(Integer id, DepartmentRecord departmentRecord);
    Optional<Business> getByEntityId(Integer id);
    Optional<Business> getByBusinessId(String businessId);
}
