package com.zpmc.ztos.infra.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude={
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class, //（如果使用Hibernate时，需要加）
        DataSourceTransactionManagerAutoConfiguration.class,
})
public class ZtosInfraBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZtosInfraBaseApplication.class, args);
    }

}
