package com.example.api.domain.consulta.validacoes;

import com.example.api.domain.consulta.ConsultaRepository;
import com.example.api.domain.consulta.DadosAgendamentoConsulta;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidadorPacienteSemOutraConsultaNoDia {

    private final ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var primeiroHoario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);

        var pacientePossuiOutraConsultaNoDia = consultaRepository
                .existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHoario, ultimoHorario);

        if (pacientePossuiOutraConsultaNoDia) {
            throw new RuntimeException("Médico possui outra consulta no mesmo horário");
        }
    }
}
