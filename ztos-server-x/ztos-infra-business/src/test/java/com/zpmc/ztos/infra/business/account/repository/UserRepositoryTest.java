package com.zpmc.ztos.infra.business.account.repository;

import com.zpmc.ztos.infra.base.event.*;
import com.zpmc.ztos.infra.business.DummyApp;
import com.zpmc.ztos.infra.business.account.User;
import com.zpmc.ztos.infra.business.account.service.UserService;
import com.zpmc.ztos.infra.business.base.AbstractBaseCalc;
import com.zpmc.ztos.infra.business.base.HelloService;
import com.zpmc.ztos.infra.business.base.UserValidationRule;
import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.assertj.core.api.Assertions;
import org.geolatte.geom.C2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.crs.CrsRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.Predicate;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

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
        // __TypeId__ : com.zpmc.ztos.infra.base.event.SimpleDiagnosticEvent
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

    @Autowired
    @Qualifier("taskExecutor")
    private AsyncTaskExecutor executorService;
    private Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Test
    public void testExecutorService() throws InterruptedException {
        // TODO: add priority to Runnable
        Future<?> future1 = executorService.submit(new ZpmcUserRunnable("userCreationTask1", "user1", "USER001"));
        Future<?> future2 = executorService.submit(new ZpmcUserRunnable("userCreationTask2", "user2", "USER002"));
        Future<?> future3 = executorService.submit(new ZpmcUserRunnable("userCreationTask3", "user3", "USER003"));

        zpmcEventBus.postMQ(
                new TaskTriggerEvent("userCreationTask1"));
        zpmcEventBus.postMQ(
                new TaskTriggerEvent("userCreationTask2"));
        zpmcEventBus.postMQ(
                new TaskTriggerEvent("userCreationTask3"));
        while (!future1.isDone()
//                || !future2.isDone()
//                || !future3.isDone()
        ) {
            logger.info("Not finished, waiting...");
            // TODO: add ThreadStopEvent
            zpmcEventBus.postMQ(
                    new TaskStopEvent("userCreationTask1"));
            Thread.sleep(3000);
//            zpmcEventBus.postMQ(
//                    new TaskStopEvent("userCreationTask2"));
//            zpmcEventBus.postMQ(
//                    new TaskStopEvent("userCreationTask3"));
        }
    }

    @Test
    public void testPlugin() throws MalformedURLException, ScriptException, ResourceException, InstantiationException, IllegalAccessException {

        final GroovyScriptEngine engine = new GroovyScriptEngine(new URL[] {
                new File("c:\\tmp\\").toURI().toURL()},
                this.getClass().getClassLoader());

        Class<GroovyObject> calcClass = engine.loadScriptByName("UserDefined.groovy");
        GroovyObject calc = calcClass.newInstance();

        int from = 1, to = 100;
        Object result = calc.invokeMethod("calcSum", new Object[] {from, to});

        logger.info("result: {}", result);

        Object user = calc.invokeMethod("createMyUser", new Object[] {});

        logger.info("user: {}", user);

        List<User> userGroovy = User.findAllByFirstName("userGroovy");
        logger.info("users found: {}", userGroovy);
    }

    @Test
    public void testPluginEx() throws ScriptException, ResourceException, MalformedURLException, InstantiationException, IllegalAccessException {
        final GroovyScriptEngine engine = new GroovyScriptEngine(new URL[] {
                new File("c:\\tmp\\").toURI().toURL()},
                this.getClass().getClassLoader());

        Class<AbstractBaseCalc> calcClass = engine.loadScriptByName("UserDefinedCalc.groovy");
        AbstractBaseCalc calc = calcClass.newInstance();
        System.out.println(calc.getClass());
//        System.out.println(calc.getMetaClass());
        int from = 1, to = 100;
        int res = calc.calc(from, to);
        System.out.println(res);


        Class<UserValidationRule> groovyClass = engine.loadScriptByName("UserDefinedValidationRule.groovy");
        UserValidationRule rule = groovyClass.newInstance();
        System.out.println(rule.getClass());
        boolean validate = rule.validate(User.create("user1", "aaa"));
        System.out.println(validate);
    }

    @Test
    public void testPluginWithJython() {

//        PySystemState systemState = Py.getSystemState();
//        systemState.path.append(new PyString("C:\\jython2.7.2\\Lib"));

        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("c:\\tmp\\HelloServicePython.py");
        PyObject pyObject = interpreter.get("HelloServicePython").__call__();

        HelloService helloService = (HelloService) pyObject.__tojava__(HelloService.class);
        String hello = helloService.getHello();
        System.out.println(hello);

        List<User> userPython = User.findAllByFirstName("pythonUser");
        logger.info("users found: {}", userPython);
    }

    @Test
    public void testPluginWithJythonEx() throws IOException {
        String line = "python " + " c:\\tmp\\calc.py 1 100";
        CommandLine cmdLine = CommandLine.parse(line);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(streamHandler);

        int exitCode = executor.execute(cmdLine);

        System.out.println(outputStream);
        assertEquals("No errors should be detected", 0, exitCode);
        assertEquals("Should contain script output: ", "5050", outputStream.toString()
                .trim());
    }
}