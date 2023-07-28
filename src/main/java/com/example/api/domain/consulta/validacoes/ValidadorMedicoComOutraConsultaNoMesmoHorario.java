package com.example.api.domain.consulta.validacoes;

import com.example.api.domain.consulta.ConsultaRepository;
import com.example.api.domain.consulta.DadosAgendamentoConsulta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    private final ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var medicoPossuoOutraConsultaNoMesmoHorario = consultaRepository
                .existsByMedicoIdAndData(dados.idMedico(), dados.data());

        if (medicoPossuoOutraConsultaNoMesmoHorario) {
            throw new RuntimeException("Médico possui outra consulta no mesmo horário");
        }
    }
}
