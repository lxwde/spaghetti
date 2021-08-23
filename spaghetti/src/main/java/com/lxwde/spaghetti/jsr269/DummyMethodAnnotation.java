package com.lxwde.spaghetti.jsr269;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD})
public @interface DummyMethodAnnotation {
}
