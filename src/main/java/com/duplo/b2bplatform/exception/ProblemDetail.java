package com.duplo.b2bplatform.exception;

import java.util.List;
import java.util.Map;

public record ProblemDetail(String title, int status, String detail, long timeStamp,
                            String developerMessage, Map<String, List<ValidationError>> validationErrors) {}
