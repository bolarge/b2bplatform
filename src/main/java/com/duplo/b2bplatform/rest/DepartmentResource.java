package com.duplo.b2bplatform.rest;

import com.duplo.b2bplatform.service.B2BService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Tag(name = "Departments", description = "Department Resource")
@RequestMapping("v1/department")
@RestController
public class DepartmentResource {

    private final B2BService b2BService;
    @PutMapping("/{id}/user/{userId}")
    public ResponseEntity<?> addStaffToABusinessDepartment(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId){
        var requestResponse = b2BService.addStaffToABusinessDepartment(id, userId);
        return ResponseEntity.ok(requestResponse);
    }
}
