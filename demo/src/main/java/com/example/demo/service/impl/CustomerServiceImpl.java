package com.example.demo.service.impl;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(Long.valueOf(id));
        return optionalCustomer.orElse(null);
    }

    @Override
    public Flux<Customer> getAllCustomers() {
        return (Flux<Customer>) customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(String id, Customer updatedCustomer) {
        if (customerRepository.existsById(Long.valueOf(id))) {
            updatedCustomer.setId(Long.valueOf(id));
            return customerRepository.save(updatedCustomer);
        }
        return null;
    }

    @Override
    public boolean deleteCustomer(String id) {
        if (customerRepository.existsById(Long.valueOf(id))) {
            customerRepository.deleteById(Long.valueOf(id));
            return true;
        }
        return false;
    }
}
