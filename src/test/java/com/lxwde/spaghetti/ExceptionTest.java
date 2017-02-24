package com.lxwde.spaghetti;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2017/2/20.
 */

public class ExceptionTest {

    @Test
    public void test() {
        System.out.println(testInternal());
    }

    public static String testInternal()
    {
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
