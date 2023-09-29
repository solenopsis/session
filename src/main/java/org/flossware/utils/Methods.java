package org.flossware.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Same vein as <code>java.util.Collections</code>
 *
 * This utility class will be moved to a FlossWare project.
 *
 * @author sfloess
 */
public class Methods {
    public static List<Method> findMethodsForAnnotation(final Class klass, final Annotation annotation) {
        final List<Method> retVal = new ArrayList<>(klass.getMethods().length);     // Assuming all methods have annotation.

        for (final Method method : klass.getMethods()) {
            if (null != method.getAnnotation(annotation.getClass())) {
                retVal.add(method);
            }
        }

        return retVal;
    }

    private Methods() {
    }
}
