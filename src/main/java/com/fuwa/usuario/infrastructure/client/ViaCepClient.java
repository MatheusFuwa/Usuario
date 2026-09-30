package com.fuwa.usuario.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "via-cep", url = "${viacep.url}")
public interface ViaCepClient {

    @GetMapping("/ws/{cep}/json/")
    ViaCepDTO Buscadadosviacep(@PathVariable("cep") String cep);
}

