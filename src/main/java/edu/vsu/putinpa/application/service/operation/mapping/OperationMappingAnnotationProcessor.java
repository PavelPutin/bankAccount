package edu.vsu.putinpa.application.service.operation.mapping;

import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.operation.mapping.processor.DuplicateOperationInfoMappingAnnotationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.getAllDeclaredFieldsFromClassHierarchy;
import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.getValueWithGetter;

public interface OperationMappingAnnotationProcessor {
    void insertValueIntoJournalOperation(Operation<?> operation, JournalOperation journalOperation);

    static Object traverseFields(
            Object object,
            Class<? extends Annotation> annotationClass) {
        Class<?> infoClass = object.getClass();

        Object result = null;
        int found = 0;
        String previousFoundName = "";
        for (Field f : getAllDeclaredFieldsFromClassHierarchy(infoClass)) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(annotationClass)) {
                if (++found > 1) {
                    throw new DuplicateOperationInfoMappingAnnotationException(
                            "Field %s can't be annotated as @Sender because %s is already annotated.".formatted(f.getName(), previousFoundName)
                    );
                }

                previousFoundName = f.getName();
                result = getValueWithGetter(f, object);
            }
        }
        return result;
    }
}
