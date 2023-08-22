package com.duplo.b2bplatform.service;

import com.duplo.b2bplatform.datatransfer.*;
import com.duplo.b2bplatform.domain.onboarding.Business;

import java.util.Optional;
public interface B2BService {
    //PurchaseOrder Object Operations
    GenericResponse<PurchaseOrderRecord> createPurchaseOrder(Integer departmentId, PurchaseOrderRecord purchaseOrderRecord);
    GenericResponse<PurchaseOrderSummary> getDepartmentPurchaseOrderReport(Integer departmentId);

    //Business Object Operations
    GenericResponse<BusinessRecord> openBusinessAccount(BusinessRecord businessRecord);
    GenericResponse<UserRecord> addStaffToABusiness(Integer id, UserRecord userRecord);
    GenericResponse<DepartmentRecord> createDepartmentForABusiness(Integer id, DepartmentRecord departmentRecord);
    Optional<Business> getByEntityId(Integer id);
    Optional<Business> getByBusinessId(String businessId);

    //Department Object Operations
    GenericResponse<DepartmentRecord> addStaffToABusinessDepartment(Integer departmentId, Integer userId);
    GenericResponse<String> calculateCreditScore(Integer businessId);
    GenericResponse<DepartmentRecord> addStaffToABusinessDepartment(Integer departmentId, UserRecord userRecord);
}
