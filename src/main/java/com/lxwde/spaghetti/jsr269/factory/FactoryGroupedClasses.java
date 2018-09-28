package com.lxwde.spaghetti.jsr269.factory;

import javax.annotation.processing.Filer;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class FactoryGroupedClasses {

    private String qualifiedClassName;

    private Map<String, FactoryAnnotatedClass> itemsMap = new LinkedHashMap<>();

    public FactoryGroupedClasses(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }

    public void add(FactoryAnnotatedClass toInsert) {
        itemsMap.put(toInsert.getId(), toInsert);
    }

    public void generateCode(Elements elementUtils, Filer filer) throws IOException {

    }
}
