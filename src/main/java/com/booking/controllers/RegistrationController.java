package com.booking.controllers;

import com.booking.models.Employee;
import com.booking.models.Role;
import com.booking.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private EmployeeRepo employeeRepo;


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addEmployee(Employee employee, Model model){
        Employee employeeFromDb = employeeRepo.findByLogin(employee.getLogin());
        if(employeeFromDb!=null){
            model.addAttribute("message", "An employee for such a login already exists!");
            return "registration";
        }
        employeeRepo.save(employee);
        return "redirect:/login";
    }
}
