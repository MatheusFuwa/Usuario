package com.fuwa.usuario.business;

import com.fuwa.usuario.infrastructure.client.ViaCepClient;
import com.fuwa.usuario.infrastructure.client.ViaCepDTO;
import com.fuwa.usuario.infrastructure.exeptions.IllegalArgumentsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {
    private final ViaCepClient viaCepClient;

    public ViaCepDTO BuscarDadosCep(String cep){
        return viaCepClient.Buscadadosviacep(processarCep(cep));
    }

    private String processarCep(String cep){
        String cepFormatado = cep.replace(" ", "").replace("-", "");

        if(!cepFormatado.matches("\\d{8}") || !Objects.equals(cepFormatado.length(), 8)){
            throw new IllegalArgumentsException("O Cep contém caracteres inválidos, favor verificar");
        }
        return cepFormatado;
    }
}
