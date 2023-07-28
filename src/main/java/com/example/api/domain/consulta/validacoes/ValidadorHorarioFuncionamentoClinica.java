package com.example.api.domain.consulta.validacoes;

import com.example.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.DayOfWeek;

public class ValidadorHorarioFuncionamentoClinica {

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
