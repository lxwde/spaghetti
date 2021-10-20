package com.zpmc.ztos.infra.business.account.repository;

import com.zpmc.ztos.infra.base.helper.ZpmcAbstractRunnable;
import com.zpmc.ztos.infra.base.helper.ZpmcRunnable;
import com.zpmc.ztos.infra.business.account.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZpmcUserRunnable extends ZpmcAbstractRunnable {
    private static final Logger logger = LoggerFactory.getLogger(ZpmcUserRunnable.class);

    private String firstName, lastName;
    public ZpmcUserRunnable(String taskId, String firstName, String lastName) {
        super(taskId);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void beforeExecute() {
        super.beforeExecute();
        User.create(firstName, lastName);
        logger.info("beforeExecute, user created. {} {}", firstName, lastName);
    }

    private User user;

    @Override
    public void process() {
        user = User.findOneByFirstNameAndLastName(firstName, lastName);
        logger.info("execute User.findOneByFirstNameAndLastName {} {}", firstName, lastName);
    }

    @Override
    public void processScheduled() {
        logger.info("processScheduled {} {}", firstName, lastName);
    }

    @Override
    public void afterExecute() {
        super.afterExecute();
        logger.info("afterExecute, user found {} {}", user.getFirstName(), user.getLastName());
    }


}
