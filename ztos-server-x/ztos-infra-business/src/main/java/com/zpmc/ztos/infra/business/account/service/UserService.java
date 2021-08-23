package com.zpmc.ztos.infra.business.account.service;

import com.zpmc.ztos.infra.base.event.*;
import com.zpmc.ztos.infra.business.account.User;
import com.zpmc.ztos.infra.business.account.UserDO;
import com.zpmc.ztos.infra.business.account.repository.UserRepository;
import com.zpmc.ztos.infra.business.base.BaseBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@EventSubscriber
//@EventSubscriber(value = {"ArgoCalendarEvent", "XXXEvent"})
public class UserService  implements EventListener {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserRepository getRepository() {
//        return this.userRepository;
//    }
//
//    public Boolean isAdmin(UserDO user) {
//        return user.getFirstName().equals("admin");
//    }
//
//    public UserDO create(String firstName, String lastName) {
//        User user = new User(firstName, lastName);
//        userRepository.save(user);
//        return user;
//    }
//
//    public UserDO update(Integer id, String firstName, String lastName) {
//        Optional<User> user = userRepository.findById(id);
//        if (user.isPresent()) {
//            user.get().setFirstName(firstName);
//            user.get().setLastName(lastName);
//            return user.get();
//        }
//        return null;
//    }
////    @Subscribe
////    public void handleEvent(ArgoCalendarEvent event) {
////        logger.info("Event received: {}", event);
////    }
////
////    @Subscribe
////    public void handleEvent(SimpleDiagnosticEvent event) {
////        logger.info("Event received: {}", event);
////    }
//
    @Override
    //@Subscribe
    public void handleEvent(EventBase event) {
        logger.info("Event received: {}", event);
//        if (event instanceof ArgoCalendarEvent) {
//
//        } else if (event instanceof XXXEvent) {
//
//        }
    }
}
