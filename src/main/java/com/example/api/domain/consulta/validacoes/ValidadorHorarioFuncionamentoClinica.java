package com.example.api.domain.consulta.validacoes;

import com.example.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var isDomingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisDoFechamentoDaClinica = dataConsulta.getHour() > 18;

        if (isDomingo || antesDaAberturaDaClinica || depoisDoFechamentoDaClinica) {
            throw new RuntimeException("Horário de funcionamento da clínica: Segunda a Sábado das 07:00 às 18:00");
        }
    }
}
