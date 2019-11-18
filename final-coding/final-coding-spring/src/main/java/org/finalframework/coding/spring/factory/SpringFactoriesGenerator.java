package org.finalframework.coding.spring.factory;


import org.finalframework.coding.Coder;
import org.finalframework.core.generator.Generator;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-06 09:47:00
 * @since 1.0
 */
public class SpringFactoriesGenerator implements Generator<SpringFactories, Void> {

    private static final String RESOURCE_FILE = "META-INF/spring.factories";
    private final Coder coder = Coder.getDefaultCoder();
    private final ProcessingEnvironment processingEnv;
    private final Filer filer;


    public SpringFactoriesGenerator(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        this.filer = processingEnv.getFiler();
    }


    @Override
    public Void generate(SpringFactories springFactories) {

        if (springFactories.getSpringFactories().isEmpty()) return null;

        try {
            // would like to be able to print the full path
            // before we attempt to get the resource in case the behavior
            // of filer.getResource does change to match the spec, but there's
            // no good way to resolve CLASS_OUTPUT without first getting a resource.
            FileObject existingFile = filer.getResource(StandardLocation.CLASS_OUTPUT, "",
                    RESOURCE_FILE);
            info("Looking for existing resource file at " + existingFile.toUri());
        } catch (IOException e) {
            // According to the javadoc, Filer.getResource throws an exception
            // if the file doesn't already exist.  In practice this doesn't
            // appear to be the case.  Filer.getResource will happily return a
            // FileObject that refers to a non-existent file but will throw
            // IOException if you try to open an input stream for it.
            info("Resource file did not already exist.");
        }

        try {
            FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", RESOURCE_FILE);
            coder.coding(springFactories, fileObject.openWriter());
            info("Create spring.factories :" + springFactories);
        } catch (Exception e) {
//            logger.error("Create spring.factories error :", e);
            error("Create spring.factories error :" + springFactories + ",\n" + e.getMessage());
        }

        return null;
    }


    private void info(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }

    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }
}
