package com.duplo.b2bplatform.rest;

import com.duplo.b2bplatform.datatransfer.PurchaseOrderRecord;
import com.duplo.b2bplatform.service.B2BService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Tag(name = "PurchaseOrders", description = "PurchaseOrder Resource")
@RequestMapping("v1/purchase-order")
@RestController
public class PurchaseOrderResource {

    private final B2BService b2BService;
    @Resource
    private WebClient webClient;
    @PostMapping("/{departmentId}")
    public ResponseEntity<?> createPurchaseOrderRecord(@PathVariable("departmentId") Integer departmentId, @Valid @RequestBody PurchaseOrderRecord purchaseOrderRecord){
        var requestResponse = b2BService.createPurchaseOrder(departmentId, purchaseOrderRecord);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPurchaseOrderUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(requestResponse.getData().purchaseOrderId()).toUri();
        responseHeaders.setLocation(newPurchaseOrderUri);
        return new ResponseEntity<>(requestResponse, responseHeaders, HttpStatus.CREATED);
    }
    @GetMapping("/{departmentId}/calculate-credit-score")
    public ResponseEntity<?> getCreditScore(@PathVariable("departmentId") Integer departmentId){
        var queryResponse = b2BService.calculateCreditScore(departmentId);
        return ResponseEntity.ok(queryResponse);
    }
    @GetMapping("/{departmentId}/get-transaction-report")
    public ResponseEntity<?> getPurchaseOrderReport(@PathVariable("departmentId") Integer departmentId){
        var queryResponse = b2BService.getDepartmentPurchaseOrderReport(departmentId);
        return ResponseEntity.ok(queryResponse);
    }
}
