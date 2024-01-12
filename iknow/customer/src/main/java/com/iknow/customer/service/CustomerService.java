package com.iknow.customer.service;


import com.iknow.customer.model.Customer;
import com.iknow.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final KafkaTemplate<String,Object> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    public CustomerService(CustomerRepository customerRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.customerRepository = customerRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Customer createCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        kafkaTemplate.send(topic, savedCustomer.getName() + " " + savedCustomer.getSurname());
        return savedCustomer;
    }
    public Customer updateCustomer(Long id, String name, String surname) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setName(name);
            customer.setSurname(surname);
            return customerRepository.save(customer);
        } else {
            return null;
        }
    }

    public List<Customer> listAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public void deleteById(Long customerId) {
        customerRepository.deleteById(customerId);
    }
}
