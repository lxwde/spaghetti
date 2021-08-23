package com.zpmc.ztos.simpletest.account.repository;

import com.zpmc.ztos.infra.base.event.ArgoCalendarEvent;
import com.zpmc.ztos.infra.base.event.SimpleDiagnosticEvent;
import com.zpmc.ztos.infra.base.event.ZpmcEventBus;
import com.zpmc.ztos.infra.business.account.User;
import com.zpmc.ztos.infra.business.account.repository.UserRepository;
import com.zpmc.ztos.simpletest.DummyApp;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.Predicate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApp.class)
public class UserRepositoryTest {


    @Autowired
    ZpmcEventBus zpmcEventBus;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserRepository userRepository;

    @Test
    public void test() {
        long count = User.count();
        System.out.println(count);

        Assertions.assertThat(count).isEqualTo(0);
        User user = User.create("admin", "AAXXAA");

        count = User.count();
        System.out.println(count);
        Assertions.assertThat(count).isEqualTo(1);

        User user1 = User.findById(1);
        System.out.println(user1);
        Assertions.assertThat(user1.getFirstName()).isEqualTo("admin");

        List<User> all = User.findAllByFirstName("admin");
        System.out.println(all);

        User user2 = User.findOneByFirstNameAndLastName("admin", "AAXXAA");
        System.out.println(user2);

//        Boolean isAdmin = this.userService.isAdmin(user2);
//        System.out.println(isAdmin);
    }

    @Test
    public void testEventPost() throws InterruptedException {
        zpmcEventBus.postInternal(new ArgoCalendarEvent());

        Thread.sleep(3 * 1000);

        zpmcEventBus.postInternal(new SimpleDiagnosticEvent("STS down", "solution1"));

        Thread.sleep(3 * 1000);
    }

    @Test
    public void testEventPostMQ() throws InterruptedException {
        zpmcEventBus.postMQ(
                new SimpleDiagnosticEvent("AGV delay", "solution2"));

        Thread.sleep(300 * 1000);
    }

    @Test
    public void testJpaSpecification() {
        User user = User.create("admin", "AAXXAA");
        List<User> all = User.getUserRepository().findAll();
        System.out.println(all);

        List<User> users = User.findAll((root, query, cb) -> {
            Predicate predicate = cb.equal(root.get(User.FIRST_NAME), "admin");
            predicate = cb.and(predicate, cb.equal(root.get(User.LAST_NAME), "AAXXAA"));
            // where (firstName = 'admin' and lastName = 'AAXXAA')
            query.where(predicate);
            return null;
        });
        System.out.println(users);

        users = User.findAll((Specification<User>) (root, query, cb) -> {
            Predicate predicate = cb.equal(root.get(User.FIRST_NAME), "admin");
            predicate = cb.and(predicate, cb.like(root.get(User.LAST_NAME), "%XX%"));
            // where firstName = 'admin' and lastName like '%XX%'
            query.where(predicate);
            return null;
        });
        System.out.println(users);
    }
}