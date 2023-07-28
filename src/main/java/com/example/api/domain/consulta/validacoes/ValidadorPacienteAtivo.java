package com.example.api.domain.consulta.validacoes;

import com.example.api.domain.consulta.DadosAgendamentoConsulta;
import com.example.api.domain.pacientes.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidadorPacienteAtivo {

    private final PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null) {
            return;
        }

        var pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.idMedico());

        if (!pacienteEstaAtivo) {
            throw new RuntimeException("Médico não está ativo");
        }
    }
}
