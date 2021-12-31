package com.zpmc.ztos.infra.base.utils;


import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.jetbrains.kotlin.cli.common.environment.UtilKt.setIdeaIoUseFallback;

public class KotlinScriptTest {

    @Test
    public void test() throws ScriptException, FileNotFoundException, InstantiationException, IllegalAccessException {
        setIdeaIoUseFallback();
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByExtension("kts");

       Object kts = scriptEngine.eval(new FileReader("c:\\tmp\\UserDefined.kts"));

       System.out.println(kts);
    }

    @Test
    public void testEngine() {
        ScriptEngineManager mgr = new ScriptEngineManager();

        List<ScriptEngineFactory> factories = mgr.getEngineFactories();

        for (ScriptEngineFactory factory : factories) {

            System.out.println("ScriptEngineFactory Info");

            String engName = factory.getEngineName();
            String engVersion = factory.getEngineVersion();
            String langName = factory.getLanguageName();
            String langVersion = factory.getLanguageVersion();

            System.out.printf("\tScript Engine: %s (%s)%n", engName, engVersion);

            List<String> engNames = factory.getNames();
            for(String name : engNames) {
                System.out.printf("\tEngine Alias: %s%n", name);
            }

            System.out.printf("\tLanguage: %s (%s)%n", langName, langVersion);

        }
    }
}