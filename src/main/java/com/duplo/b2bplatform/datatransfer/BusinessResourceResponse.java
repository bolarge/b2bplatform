package com.duplo.b2bplatform.datatransfer;

import com.duplo.b2bplatform.domain.onboarding.Department;
import com.duplo.b2bplatform.domain.onboarding.User;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class BusinessResourceResponse {
    private String businessId;
    private String businessName;
    private String businessEmail;
    private String firstName;
    private String lastName;
    private Iterable<Department> theDepartments;
    private Iterable<User> theStaff;
}
