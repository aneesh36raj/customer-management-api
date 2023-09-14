package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

public class CustomerControllerTest {

    private MockMvc mockMvc;

    private CustomerController customerController;

    // Mock the CustomerService interface
    @MockBean
    private CustomerService customerService;

    //private CustomerService customerService;

    @BeforeEach
    public void setup() {
        customerController = new CustomerController(customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        // Arrange
        List<Customer> customers = Arrays.asList(
                new Customer("1", "John Doe", "john@example.com", "123-456-7890"),
                new Customer("2", "Jane Smith", "jane@example.com", "987-654-3210")
        );
        when(customerService.getAllCustomers()).thenReturn(customers);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}]")); // Use JSON content assertions
    }

    @Test
    public void testGetCustomerById() throws Exception {
        // Arrange
        Customer customer = new Customer("1", "John Doe", "john@example.com", "123-456-7890");
        when(customerService.getCustomerById("1")).thenReturn(customer);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
    }


}
