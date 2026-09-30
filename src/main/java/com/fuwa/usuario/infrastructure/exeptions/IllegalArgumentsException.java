package com.fuwa.usuario.infrastructure.exeptions;

public class IllegalArgumentsException extends RuntimeException {
    public IllegalArgumentsException(String mensagem) {
        super(mensagem);
    }
    public IllegalArgumentsException(String mensagem, Throwable throwable){
        super(mensagem, throwable);
    }
}
