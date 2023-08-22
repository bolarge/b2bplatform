package com.duplo.b2bplatform.datatransfer;

public record PurchaseOrderSummary(String totalNumberOfOrders, String totalAmountOfOrders, String totalNumberOfOrdersToday,
                                   String totalAmountOfOrdersToday) {
}
