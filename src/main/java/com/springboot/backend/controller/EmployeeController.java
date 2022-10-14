package com.springboot.backend.controller;

import com.springboot.backend.exception.ResourceNotFoundException;
import com.springboot.backend.model.Employee;
import com.springboot.backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins="http://localhost:4200")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee setEmployee(@RequestBody Employee employee ){
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not exist with id :"+id));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employeeDetails, @PathVariable Long id){

        Employee employee= employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not exist with id :"+id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        Employee updatedEmployeeDetails = employeeRepository.save(employee);

        return ResponseEntity.ok(updatedEmployeeDetails);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable("id") Long id){

        Employee employee= employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not exist with id :"+id));

        employeeRepository.delete(employee);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
