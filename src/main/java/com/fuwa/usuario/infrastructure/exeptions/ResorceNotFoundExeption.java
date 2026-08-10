package com.fuwa.usuario.infrastructure.exeptions;

public class ResorceNotFoundExeption extends RuntimeException{
    public ResorceNotFoundExeption(String mensagem){
        super(mensagem);
    }
    public ResorceNotFoundExeption(String mensagem, Throwable throwable){
        super(mensagem, throwable);
    }
}
