package com.example.api.domain.consulta.validacoes;

import com.example.api.domain.consulta.DadosAgendamentoConsulta;
import com.example.api.domain.medicos.repositories.MedicosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidadorMedicoAtivo {

    private final MedicosRepository medicosRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null) {
            return;
        }

        var medicoEstaAtivo = medicosRepository.findAtivoById(dados.idMedico());

        if (!medicoEstaAtivo) {
            throw new RuntimeException("Médico não está ativo");
        }
    }
}
