package de.base2code.scripzywaitlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ScripzyWaitlistApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScripzyWaitlistApplication.class, args);
    }

}
