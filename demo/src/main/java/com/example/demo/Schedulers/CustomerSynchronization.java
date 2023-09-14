package com.example.demo.Schedulers;

import com.example.demo.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "salesforce.api")
public class CustomerSynchronization {

    private String endpoint;
    private String token;

    @Autowired
    private DataSource dataSource;

    // Getter and setter for salesforce.api.endpoint and salesforce.api.token

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public void synchronizeCustomers() {
        // Fetch customer data from SQL Server
        List<Customer> customers = fetchCustomerDataFromSQLServer();

        // Synchronize customer data with Salesforce
        for (Customer customer : customers) {
            boolean success = synchronizeCustomerWithSalesforce(customer);
            if (success) {
                System.out.println("Customer synchronized successfully: " + customer.getName());
            } else {
                System.err.println("Failed to synchronize customer: " + customer.getName());
            }
        }
    }

    private List<Customer> fetchCustomerDataFromSQLServer() {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, name, email, phone FROM Customer";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");

                customers.add(new Customer(id, name, email, phone));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;
    }

    private boolean synchronizeCustomerWithSalesforce(Customer customer) {
        try {
            RestTemplate restTemplate = restTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            ObjectMapper objectMapper = new ObjectMapper();
            String customerJson = objectMapper.writeValueAsString(customer);

            HttpEntity<String> requestEntity = new HttpEntity<>(customerJson, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(endpoint, requestEntity, String.class);

            // Check the response for success
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return true;
            } else {
                System.err.println("Salesforce API error: " + responseEntity.getBody());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


