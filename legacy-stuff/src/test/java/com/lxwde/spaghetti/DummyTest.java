package com.lxwde.spaghetti;

import org.junit.Test;
import org.slf4j.impl.StaticLoggerBinder;

/**
 * Created by Administrator on 2017/2/20.
 */

public class DummyTest {

    @Test
    public void testException() {
        System.out.println(testExceptionInternal());
    }

    @Test
    public void testSlf4j() {
        StaticLoggerBinder binder = StaticLoggerBinder.getSingleton();
        System.out.println(binder.getLoggerFactoryClassStr());
    }

    public static String testExceptionInternal() {
        try {
            System.out.println("try");
            throw new Exception();
        } catch(Exception e) {
            System.out.println("catch");
            return "return";
        } finally {
            System.out.println("finally");
            return "return in finally";
        }
    }

}
