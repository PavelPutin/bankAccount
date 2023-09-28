package edu.vsu.putinpa.application.service.operation.mapping.processor;

import edu.vsu.putinpa.application.model.Account;
import edu.vsu.putinpa.application.model.JournalOperation;
import edu.vsu.putinpa.application.service.Operation;
import edu.vsu.putinpa.application.service.operation.mapping.OperationMappingAnnotationProcessor;
import edu.vsu.putinpa.application.service.operation.mapping.annotation.Sender;
import edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static edu.vsu.putinpa.infrastructure.util.reflection.ReflectionUtil.getValueWithGetter;

public class SenderOperationMappingAnnotationProcessor implements OperationMappingAnnotationProcessor {
    @Override
    public void insertValueIntoJournalOperation(Operation<?> operation, JournalOperation journalOperation) {
        Class<?> infoClass = operation.getInfo().getClass();

        int found = 0;
        String previousFoundName = "";
        for (Field f : infoClass.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(Sender.class)) {
                if (++found > 1) {
                    throw new DuplicateOperationInfoMappingAnnotationException(
                            "Field %s can't be annotated as @Sender because %s is already annotated.".formatted(f.getName(), previousFoundName)
                    );
                }

                previousFoundName = f.getName();
                journalOperation.setSender((Account) getValueWithGetter(f, operation.getInfo()));
            }
        }
    }
}
