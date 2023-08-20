package com.duplo.b2bplatform.datatransfer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BusinessRecord(@NotBlank @NotNull @Size(min = 2, max = 5, message = "Title is required!") String businessName,
                             @NotBlank @NotNull @Email(message = "Valid Email is required!") String businessEmail,
                             @NotBlank @NotNull @Size(min = 3, max = 15, message = "first name is required!") String firstName,
                             @NotBlank @NotNull @Size(min = 3, max = 15, message = "last name is required!") String lastName,
                             @NotBlank @NotNull @Size(min = 11, max = 15, message = "Valid phone number is required!") String phoneNumber,
                             Integer id,
                             String businessId,
                             String departmentId) {
}
