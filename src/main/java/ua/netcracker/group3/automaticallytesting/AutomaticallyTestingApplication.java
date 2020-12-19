package ua.netcracker.group3.automaticallytesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutomaticallyTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomaticallyTestingApplication.class, args);
    }

}
