package com.fuwa.usuario.infrastructure.exeptions;

public class ResorceNotFoundException extends RuntimeException{
    public ResorceNotFoundException(String mensagem){
        super(mensagem);
    }
    public ResorceNotFoundException(String mensagem, Throwable throwable){
        super(mensagem, throwable);
    }
}
