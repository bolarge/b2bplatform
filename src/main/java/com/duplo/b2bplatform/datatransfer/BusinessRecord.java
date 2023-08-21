package com.duplo.b2bplatform.datatransfer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BusinessRecord(@NotBlank @NotNull @Size(min = 5, max = 25, message = "Business name is required!") String businessName,
                             @NotBlank @NotNull @Email(message = "Valid business email is required!") String businessEmail,
                             @NotBlank @NotNull @Size(min = 3, max = 15, message = "first name is required!") String firstName,
                             @NotBlank @NotNull @Size(min = 3, max = 15, message = "last name is required!") String lastName,
                             @NotBlank @NotNull @Email(message = "Valid user email is required!") String email,
                             @NotBlank @NotNull @Size(min = 11, max = 15, message = "Valid phone number is required!") String phoneNumber,
                             @NotBlank @NotNull (message = "Department name is required!") String departmentName,
                             @NotBlank @NotNull (message = "Department description is required!") String departmentDescription,
                             Integer id,
                             String businessId,
                             String departmentId) {
}
