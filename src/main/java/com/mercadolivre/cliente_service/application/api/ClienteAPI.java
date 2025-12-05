package com.mercadolivre.cliente_service.application.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.Valid;

@RequestMapping("/v1/cliente")
public interface ClienteAPI {
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	ClienteResponde postcliente(@Valid @RequestBody ClienteRequest request);
	

}
