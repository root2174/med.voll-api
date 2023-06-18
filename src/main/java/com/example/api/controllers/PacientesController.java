package com.example.api.controllers;

import com.example.api.domain.pacientes.dto.DadosAtualizacaoPaciente;
import com.example.api.domain.pacientes.dto.DadosCadastroPaciente;
import com.example.api.domain.pacientes.dto.DadosListagemPaciente;
import com.example.api.domain.pacientes.models.Paciente;
import com.example.api.domain.pacientes.repositories.PacienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacientesController {
    private final PacienteRepository repository;

    public PacientesController(PacienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public void cadastrar(
            @RequestBody @Valid DadosCadastroPaciente dados
    ) {
        repository.save(new Paciente(dados));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(
            @PageableDefault(size = 8, sort = {"nome"})Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();
    }
}
