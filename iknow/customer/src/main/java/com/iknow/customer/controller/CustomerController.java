package com.iknow.customer.controller;

import com.iknow.customer.model.Customer;
import com.iknow.customer.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @ApiOperation(value = "Create a new customer", notes = "Provide customer details to create a new customer")
    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update an existing customer", notes = "Provide an ID, name, and surname to update an existing customer")
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestParam String name, @RequestParam String surname) {
        Customer customer = customerService.updateCustomer(id, name, surname);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Get a list of all customers", notes = "No input required")
    @GetMapping("/list")
    public ResponseEntity<List<Customer>> listAll() {
        List<Customer> customers = customerService.listAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @ApiOperation(value = "Find a customer by ID", notes = "Provide an ID to look up a specific customer")
    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        return customerService.findById(id)
                .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Delete a customer", notes = "Provide an ID to delete a specific customer")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
