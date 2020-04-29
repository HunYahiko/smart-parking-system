package com.utm.stanislav.parkingapp.configuration.processor;

import com.google.auto.service.AutoService;
import com.utm.stanislav.parkingapp.validators.ValidationChain;
import org.springframework.core.annotation.Order;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ValidatorOrderProcessor extends AbstractProcessor {
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        List<TypeElement> validationChains = ElementFilter.typesIn(roundEnv.getRootElements())
                .stream()
                .filter(this::isValidationChain)
                .collect(Collectors.toList());
        
        for (TypeElement validationChainElement : validationChains) {
            processValidationChain(validationChainElement);
        }
        return false;
    }
    
    private boolean isValidationChain(TypeElement typeElement) {
        List<? extends TypeMirror> interfaces = typeElement.getInterfaces();
        return interfaces.stream().anyMatch(mirror ->
            mirror.toString().equals(ValidationChain.class.getName()));
    }
    
    private void processValidationChain(TypeElement validationChainElement) {
        if (validationChainElement.getAnnotation(Order.class) == null) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    "Classes implementing ValidationChain interface" +
                            " must be annotated with @Order. \n Error class: " + validationChainElement);
        }
    }
}
