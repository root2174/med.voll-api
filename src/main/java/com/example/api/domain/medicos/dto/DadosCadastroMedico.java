package com.example.api.domain.medicos.dto;

import com.example.api.domain.endereco.dto.DadosEndereco;
import com.example.api.domain.medicos.enums.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record DadosCadastroMedico(
        @NotBlank
        String nome,

        @NotNull
        @Email
        String email,

        @NotNull
        @Pattern(regexp = "\\d{4,6}")
        String crm,

        @NotNull
        Especialidade especialidade,

        @Valid
        DadosEndereco endereco
) {}
