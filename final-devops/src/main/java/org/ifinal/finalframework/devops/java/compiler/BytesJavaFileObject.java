package org.ifinal.finalframework.devops.java.compiler;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import javax.tools.SimpleJavaFileObject;

/**
 * BytesJavaFileObject.
 */
public class BytesJavaFileObject extends SimpleJavaFileObject {

    private static final char PKG_SEPARATOR = '.';

    private static final char DIR_SEPARATOR = '/';

    private static final String CLASS_FILE_SUFFIX = ".class";

    private ByteArrayOutputStream byteArrayOutputStream;

    public BytesJavaFileObject(final String className) {

        super(URI.create("byte:///" + className.replace(PKG_SEPARATOR, DIR_SEPARATOR)
            + Kind.CLASS.extension), Kind.CLASS);
    }

    public BytesJavaFileObject(final String className, final ByteArrayOutputStream byteArrayOutputStream) {

        this(className);
        this.byteArrayOutputStream = byteArrayOutputStream;
    }

    @Override
    public OutputStream openOutputStream() {
        if (byteArrayOutputStream == null) {
            byteArrayOutputStream = new ByteArrayOutputStream();
        }
        return byteArrayOutputStream;
    }

    public byte[] getByteCode() {
        return byteArrayOutputStream.toByteArray();
    }

    public String getClassName() {
        String className = getName();
        className = className.replace(DIR_SEPARATOR, PKG_SEPARATOR);
        className = className.substring(1, className.indexOf(CLASS_FILE_SUFFIX));
        return className;
    }

}