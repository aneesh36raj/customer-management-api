package com.example.demo.service;

import com.example.demo.entity.Customer;
import reactor.core.publisher.Flux;

public interface CustomerService {
    Customer getCustomerById(String id);
    Flux<Customer> getAllCustomers();
    Customer createCustomer(Customer customer);
    Customer updateCustomer(String id, Customer customer);
    boolean deleteCustomer(String id);
}
