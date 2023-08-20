package com.duplo.b2bplatform.service;

import com.duplo.b2bplatform.datatransfer.BusinessRecord;
import com.duplo.b2bplatform.datatransfer.GenericResponse;

public interface B2BPlatformService {

    //Business Object Operations
    GenericResponse<BusinessRecord> openBusinessAccount(BusinessRecord businessRecord);
}
