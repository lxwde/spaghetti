package com.zpmc.ztos.infra.business;

import com.zpmc.ztos.infra.business.config.Constants;
import com.zpmc.ztos.infra.business.util.ProfileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication(scanBasePackages = {"com.zpmc.ztos.infra.base", "com.zpmc.ztos.infra.business"})
@Configuration
@EnableJpaRepositories("com.zpmc.ztos.infra.business")
@EnableMongoRepositories(basePackages="com.zpmc.ztos.infra.business")
public class DummyApp {

    private static final Logger log = LoggerFactory.getLogger(DummyApp.class);

//    @Bean
//    public boolean initGeoDB(final DataSource dataSource) throws SQLException {
//        final Connection cx = dataSource.getConnection();
//        GeoDB.InitGeoDB(cx);
//        return true;
//    }

    @Autowired
    private Environment env;

    @PostConstruct
    public void initApplication() {
        log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                    "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not" +
                    "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(DummyApp.class);
        ProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://127.0.0.1:{}\n\t" +
                        "External: \thttp://{}:{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));

    }

//    @Bean(name = "helloServiceFactory")
//    public HelloServiceFactory helloFactory() {
//        return new HelloServiceFactory();
//    }
//
//    @Bean(name = "helloServicePython")
//    public HelloService helloServicePython() throws Exception {
//        return helloFactory().getObject();
//    }
}