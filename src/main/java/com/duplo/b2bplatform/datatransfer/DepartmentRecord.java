package com.duplo.b2bplatform.datatransfer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentRecord(@NotBlank @NotNull @Size(min = 2, max = 25, message = "Department name is required!") String departmentName,
                               @NotBlank @NotNull (message = "Description is required!") String description,
                               @NotBlank @NotNull @Email(message = "Valid Email is required!") String departmentEmail,
                               @NotBlank @NotNull @Size(min = 3, max = 15, message = "Business Id is required!") String businessId,
                               Integer id,
                               String departmentId) {
}
