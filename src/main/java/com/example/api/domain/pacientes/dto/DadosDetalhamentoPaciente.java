package com.example.api.domain.pacientes.dto;

import com.example.api.domain.endereco.models.Endereco;
import com.example.api.domain.pacientes.models.Paciente;

public record DadosDetalhamentoPaciente(
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco) {
    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                paciente.getEndereco()
        );
    }
}