package com.utm.stanislav.parkingapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/test")
public class RestTestController {
    
    
    @GetMapping("/test1")
    @PreAuthorize("hasRole('USER')")
    public void testSomething() {
        System.out.println("I got here somehow");
    }
    
    @GetMapping("/test2")
    @PreAuthorize("hasRole('ADMIN')")
    public void testSomethingYet() { System.out.println("I got here somehow again!");}
}
