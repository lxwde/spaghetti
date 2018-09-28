package com.lxwde.spaghetti.jsr269.factory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class FactoryProcessor extends AbstractProcessor {

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;
    private Map<String, FactoryGroupedClasses> factoryClasses = new LinkedHashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for(Element annotatedElement : roundEnv.getElementsAnnotatedWith(Factory.class)) {
            TypeElement typeElement = (TypeElement)annotatedElement;
            try {
                FactoryAnnotatedClass annotatedClass = new FactoryAnnotatedClass(typeElement);

            }catch(IllegalArgumentException e) {
                if (annotatedElement.getKind() != ElementKind.CLASS) {
                    error(annotatedElement, "Only classes can be annotated with @%s", Factory.class.getSimpleName());
                }
                return true;
            }
        }

        return false;
    }

    private void error(Element element, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                element);
    }

    private boolean isValidClass(FactoryAnnotatedClass item) {
        TypeElement classElement = item.getTypeElement();
        if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
            error(classElement, "The class $s is not public.", classElement.getQualifiedName().toString());
            return false;
        }

        if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
            error(classElement, "The class %s is abstract. You can't annotate abstract classes with @%",
                    classElement.getQualifiedName().toString(), Factory.class.getSimpleName());
            return false;
        }

        TypeElement superClassElement = elementUtils.getTypeElement(item.getQualifiedSuperClassName());
        if (superClassElement.getKind() == ElementKind.INTERFACE) {
            if (!classElement.getInterfaces().contains(superClassElement.asType())) {
                error(classElement, "The class %s annotated with @%s must implement the interface %s",
                        classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                        item.getQualifiedSuperClassName());
                return false;
            }
        }

        return true;
    }

}
