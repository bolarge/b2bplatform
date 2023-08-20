package com.duplo.b2bplatform.datatransfer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentRecord(@NotBlank @NotNull @Size(min = 2, max = 5, message = "Title is required!") String departmentName,
                               @NotBlank @NotNull @Email(message = "Valid Email is required!") String departmentEmail,
                               @NotBlank @NotNull @Size(min = 3, max = 15, message = "Dept head is required!") String departmentHead,
                               @NotBlank @NotNull @Size(min = 3, max = 15, message = "last name is required!") String businessId,
                               @NotBlank @NotNull @Size(min = 11, max = 15, message = "Valid phone number is required!") String phoneNumber,
                               Integer id,
                               String departmentId) {
}
