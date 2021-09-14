package com.zpmc.ztos.infra.business.account;

import com.google.common.base.Objects;
import com.zpmc.ztos.infra.base.config.ApplicationContextProvider;
import com.zpmc.ztos.infra.business.account.repository.UserRepository;
import com.zpmc.ztos.infra.business.base.RepositoryEnabled;
import org.geolatte.geom.Point;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "dummy", schema = "HR")
public class User extends UserDO implements RepositoryEnabled<UserRepository> {
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";

    public User() {};
    public User(String firstName, String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    private static UserRepository userRepository;

    public void init() {
        userRepository = ApplicationContextProvider.APPLICATION_CONTEXT.getBean(UserRepository.class);
    }

    public static UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = ApplicationContextProvider.APPLICATION_CONTEXT.getBean(UserRepository.class);
        }
        return userRepository;
    }

    public static User findById(Integer id) {
        return getUserRepository().findById(id).get();
    }

    private static final AtomicInteger seed = new AtomicInteger(new Random(10000000).nextInt());

    public static User create(String firstName, String lastName) {
        User user = new User(firstName, lastName);
        user.setId(seed.incrementAndGet());
        getUserRepository().save(user);
        return user;
    }

    public static long count() {
        return getUserRepository().count();
    }

    public static List<User> findAllByFirstName(String firstName) {
        return getUserRepository().findAllByFirstName(firstName);
    }

    public static User findOneByFirstNameAndLastName(String firstName, String lastName) {
        return getUserRepository().findOneByFirstNameAndLastName(firstName, lastName);
    }

    public static List<User> findAll(@Nullable Specification<User> spec) {
        return getUserRepository().findAll(spec);
    }

    public static User findOne(@Nullable Specification<User> spec) {
        return getUserRepository().findOne(spec).get();
    }

    public void update() {
        getUserRepository().save(this);
    }

    public void delete() {
        getUserRepository().delete(this);
    }

    public void notifyEvent() {
        // TODO: notify event with EventBus
    }
}
