package com.duplo.b2bplatform.datatransfer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PurchaseOrderRecord(@NotBlank @NotNull @Size(min = 5, max = 25, message = "Item name is required!") String itemName,
                                  @NotBlank @NotNull (message = "Item description is required!") String itemDescription,
                                  @NotBlank @NotNull (message = "Unit price is required!") String itemUnitPrice,
                                  @NotBlank @NotNull @Size(min = 1, max = 15, message = "Quantity must be greater than 0!") String orderQuantity,
                                  @NotBlank @NotNull (message = "Staff email id is required!") String staffEmail,
                                 String totalOrderPrice,
                                 String orderStatus,
                                 String businessId, String purchaseOrderId) {
}
