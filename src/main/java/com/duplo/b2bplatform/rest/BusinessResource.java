package com.duplo.b2bplatform.rest;

import com.duplo.b2bplatform.datatransfer.BusinessRecord;
import com.duplo.b2bplatform.datatransfer.DepartmentRecord;
import com.duplo.b2bplatform.datatransfer.UserRecord;
import com.duplo.b2bplatform.service.B2BService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Businesses", description = "Business Resource")
@RequestMapping("v1/business")
@RestController
public class BusinessResource {

    private final B2BService b2BService;
    @PostMapping("")
    public ResponseEntity<?> createBusinessAccount(@Valid @RequestBody BusinessRecord businessRecord){
        var requestResponse = b2BService.openBusinessAccount(businessRecord);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newBusinessUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(requestResponse.getData().id()).toUri();
        responseHeaders.setLocation(newBusinessUri);
        return new ResponseEntity<>(requestResponse, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/user")
    public ResponseEntity<?> addStaffToBusinessAccount(@PathVariable("id") Integer id, @Valid @RequestBody UserRecord userRecord){
        var requestResponse = b2BService.addStaffToABusiness(id, userRecord);
        return ResponseEntity.ok(requestResponse);
    }

    @PutMapping("/{id}/department")
    public ResponseEntity<?> createDepartmentUnderABusinessAccount(@PathVariable("id") Integer id, @Valid @RequestBody DepartmentRecord departmentRecord){
        var requestResponse = b2BService.createDepartmentForABusiness(id, departmentRecord);
        return ResponseEntity.ok(requestResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBusinessById(@PathVariable("id") Integer id){
        var queryResponse = b2BService.getByEntityId(id);
        return ResponseEntity.ok(queryResponse);
    }

  /*  @GetMapping("/{id}/credit-score")
    public ResponseEntity<?> getCreditScore(@PathVariable("id") Integer id){
        var queryResponse = b2BService.calculateCreditScore(id);
        return ResponseEntity.ok(queryResponse);
    }*/
}
