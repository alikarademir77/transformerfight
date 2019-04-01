package com.aequilibrium.transformerscoring.exception;

@SuppressWarnings("serial")
public class TransformerNotFoundException extends RuntimeException {

    public TransformerNotFoundException(String msg) {
        super(msg);
    }

    public TransformerNotFoundException(String msg, Exception e) {
        super(msg + " because of " + e.toString(), e);
    }
}
