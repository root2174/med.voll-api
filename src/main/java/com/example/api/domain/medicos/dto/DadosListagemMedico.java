package com.example.api.domain.medicos.dto;

import com.example.api.domain.medicos.enums.Especialidade;
import com.example.api.domain.medicos.models.Medico;

public record DadosListagemMedico(
        Long id,
        String nome,
        String email,
        String crm,
        Especialidade especialidade
) {
    public DadosListagemMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
