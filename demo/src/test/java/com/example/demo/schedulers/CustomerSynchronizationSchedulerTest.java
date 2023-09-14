package com.example.demo.schedulers;

import com.example.demo.Schedulers.CustomerSynchronization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.*;

@SpringBootTest
@EnableScheduling // Enable scheduling for tests
@TestPropertySource(properties = {"spring.scheduling.enabled=true"}) // Enable scheduling for tests
public class CustomerSynchronizationSchedulerTest {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler; // Inject the task scheduler

    @MockBean
    private CustomerSynchronization customerSynchronization;

    @Test
    public void testSynchronizeCustomersJob() throws InterruptedException {
        // Schedule the job
        taskScheduler.scheduleAtFixedRate(() -> {
            // Mock behavior of the synchronizeCustomers method
            doNothing().when(customerSynchronization).synchronizeCustomers();
        }, 1000); // Delay for 1 second to allow the task to execute

        // Sleep for some time to allow the task to execute (adjust as needed)
        Thread.sleep(2000);

        // Verify that synchronizeCustomers was called
        verify(customerSynchronization, times(1)).synchronizeCustomers();
    }
}
