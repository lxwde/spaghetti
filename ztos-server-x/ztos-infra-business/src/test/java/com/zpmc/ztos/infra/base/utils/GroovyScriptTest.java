package com.zpmc.ztos.infra.base.utils;


import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class GroovyScriptTest {
    private static final Logger logger = LoggerFactory.getLogger(GroovyScriptTest.class);

    @Test
    public void test() throws MalformedURLException, ScriptException, ResourceException, InstantiationException, IllegalAccessException {

        final GroovyScriptEngine engine = new GroovyScriptEngine(new URL[] {
                new File("c:\\tmp\\").toURI().toURL()},
                this.getClass().getClassLoader());

        Class<GroovyObject> calcClass = engine.loadScriptByName("UserDefined.groovy");
        GroovyObject calc = calcClass.newInstance();

        int from = 1, to = 100;
        Object result = calc.invokeMethod("calcSum", new Object[] {from, to});

        logger.info("result: {}", result);
    }
}