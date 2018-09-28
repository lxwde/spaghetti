package com.lxwde.spaghetti.jsr269.factory;

import org.springframework.util.StringUtils;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

public class FactoryAnnotatedClass {

    private TypeElement typeElement;
    private String qualifiedSuperClassName;
    private String simpleTypeName;
    private String id;

    public FactoryAnnotatedClass(TypeElement classElement) throws IllegalArgumentException {
        this.typeElement = classElement;
        Factory annotation = classElement.getAnnotation(Factory.class);
        id = annotation.id();

        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException(
                    String.format("id() in @%s for class %s is null or empty! that's not allowed",
                            Factory.class.getSimpleName(), classElement.getQualifiedName().toString())
            );
        }

        try {
            Class<?> clazz = annotation.type();
            qualifiedSuperClassName = clazz.getCanonicalName();
            simpleTypeName = clazz.getSimpleName();
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            qualifiedSuperClassName = classTypeElement.getQualifiedName().toString();
            simpleTypeName = classTypeElement.getSimpleName().toString();
        }
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    public String getQualifiedSuperClassName() {
        return qualifiedSuperClassName;
    }

    public void setQualifiedSuperClassName(String qualifiedSuperClassName) {
        this.qualifiedSuperClassName = qualifiedSuperClassName;
    }

    public String getSimpleTypeName() {
        return simpleTypeName;
    }

    public void setSimpleTypeName(String simpleTypeName) {
        this.simpleTypeName = simpleTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
