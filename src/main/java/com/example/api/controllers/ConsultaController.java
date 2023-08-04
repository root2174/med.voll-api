package com.example.api.controllers;

import com.example.api.domain.consulta.ConsultaService;
import com.example.api.domain.consulta.DadosAgendamentoConsulta;
import com.example.api.domain.consulta.DadosDetalhamentoConsulta;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(
            @RequestBody @Valid DadosAgendamentoConsulta consulta) {

        return ResponseEntity.ok(consultaService.agendar(consulta));
    }
}
