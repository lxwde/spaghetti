package com.zpmc.ztos.infra.business.account.repository;

import com.zpmc.ztos.infra.base.event.ArgoCalendarEvent;
import com.zpmc.ztos.infra.base.event.SimpleDiagnosticEvent;
import com.zpmc.ztos.infra.base.event.ZpmcEventBus;
import com.zpmc.ztos.infra.business.DummyApp;
import com.zpmc.ztos.infra.business.account.User;
import com.zpmc.ztos.infra.business.account.service.UserService;
import com.zpmc.ztos.infra.business.config.TestRedisConfiguration;
import org.assertj.core.api.Assertions;
import org.geolatte.geom.*;
import org.geolatte.geom.crs.CoordinateReferenceSystem;
import org.geolatte.geom.crs.CrsRegistry;
import org.hibernate.spatial.SpatialFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.hibernate.annotations.Type;
import org.geolatte.geom.codec.Wkb;
import org.geolatte.geom.codec.WkbDecoder;
import org.geolatte.geom.codec.WkbEncoder;
import org.geolatte.geom.codec.Wkb.Dialect;

import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DummyApp.class})
public class UserRepositoryTest {

    @Autowired
    UserService userService;

    @Autowired
    ZpmcEventBus zpmcEventBus;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void test() {
        long count = User.count();
        System.out.println(count);

       // Assertions.assertThat(count).isEqualTo(0);
        User user = User.create("admin", "AAXXAA");
        org.geolatte.geom.Point<C2D> point1 = Geometries.mkPoint(new C2D(12.34343, 12.232424), CrsRegistry.getProjectedCoordinateReferenceSystemForEPSG(3785));
        user.setLocation(point1);
        user.update();

        int id = user.getId();

//        count = User.count();
//        System.out.println(count);
//        Assertions.assertThat(count).isEqualTo(id);

        User user1 = User.findById(id);
        System.out.println(user1);
        Assertions.assertThat(user1.getFirstName()).isEqualTo("admin");

        List<User> all = User.findAllByFirstName("admin");
        System.out.println(all);

        List<User> cached = User.findAllByFirstName("admin");
        System.out.println(cached);

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

        List<User> users = User.findAll((root, query, cb) -> {
            Predicate predicate = cb.equal(root.get(User.FIRST_NAME), "admin");
            predicate = cb.and(predicate, cb.equal(root.get(User.LAST_NAME), "AAXXAA"));
            // where (firstName = 'admin' and lastName = 'AAXXAA')
            query.where(predicate);
            return predicate;
        });
        System.out.println(users);

        users = User.findAll((root, query, cb) -> {
            Predicate predicate = cb.equal(root.get(User.FIRST_NAME), "admin");
            predicate = cb.and(predicate, cb.like(root.get(User.LAST_NAME), "%XX%"));
            // where firstName = 'admin' and lastName like '%XX%'
            query.where(predicate);
            return predicate;
        });
        System.out.println(users);

        double longitude  = 0d, latitude = 0d, radius = 10d;

//        users = User.findAll((root, query, builder) -> {
//            Expression<Geometry> geography = builder.function("geography", Geometry.class, root.get("location"));
//            Expression<Point> point = builder.function("ST_Point", Point.class, builder.literal(longitude),
//                    builder.literal(latitude));
//            Expression<Point> comparisonPoint = builder.function("ST_SetSRID", Point.class, point,
//                    builder.literal(4326));
//            Expression<Boolean> expression = builder.function("ST_Within", boolean.class,
//                    geography, comparisonPoint, builder.literal(radius));
//            return builder.equal(expression, true);
//        });
//
//        System.out.println(users);
    }
}