package com.github.oxaoo.mp4ru.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Paths;

@SpringBootApplication(scanBasePackages = "com.github.oxaoo.mp4ru")
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class ServiceApplication {
    public static void main(String[] args) {
        final String curDir = Paths.get(".").toAbsolutePath().normalize().toString();
        System.out.println("Current directory: " + curDir);
        SpringApplication.run(ServiceApplication.class, args);
        final String curDir2 = Paths.get(".").toAbsolutePath().normalize().toString();
        System.out.println("Current directory after run: " + curDir2);
    }
}
