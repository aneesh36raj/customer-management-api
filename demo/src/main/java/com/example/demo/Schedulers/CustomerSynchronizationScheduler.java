package com.example.demo.Schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CustomerSynchronizationScheduler {

    private final CustomerSynchronization customerSynchronization;

    public CustomerSynchronizationScheduler(CustomerSynchronization customerSynchronization) {
        this.customerSynchronization = customerSynchronization;
    }

    // Define the schedule using the @Scheduled annotation
    @Scheduled(cron = "0 0 1 * * ?") // Run every day at 1 AM
    public void synchronizeCustomersJob() {
        customerSynchronization.synchronizeCustomers();
    }
}
