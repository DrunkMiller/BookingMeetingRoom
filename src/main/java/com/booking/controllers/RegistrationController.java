package com.booking.controllers;

import com.booking.advice.EmployeeExistHandler;
import com.booking.models.Employee;
import com.booking.models.Role;
import com.booking.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
public class RegistrationController {
    @Autowired
    private EmployeeRepo employeeRepo;

    @GetMapping(value = "/registration")
    public Employee registration(){
        Employee employee = new Employee();
        Set<Role> set = new HashSet<>(Collections.singletonList(Role.USER));
        employee.setRoles(set);
        return employee;
    }
    @PostMapping(value = "/registration")
    public Employee addEmployee(@Valid Employee employee, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()){
//            return ;
//        }
        Employee employeeFromDb = employeeRepo.findByLogin(employee.getLogin());
        if (employeeFromDb != null) {
            throw new EmployeeExistHandler();
        }
        employeeRepo.save(employee);
        return employee;
    }

}
