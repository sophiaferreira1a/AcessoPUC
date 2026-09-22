package com.projeto.acessopuc.exception;

public class SendEmailException extends RuntimeException {

    public SendEmailException(String message) {
        super(message);
    }
}