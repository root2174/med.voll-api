package com.example.api.controllers;

import com.example.api.domain.pacientes.dto.DadosAtualizacaoPaciente;
import com.example.api.domain.pacientes.dto.DadosCadastroPaciente;
import com.example.api.domain.pacientes.dto.DadosDetalhamentoPaciente;
import com.example.api.domain.pacientes.dto.DadosListagemPaciente;
import com.example.api.domain.pacientes.models.Paciente;
import com.example.api.domain.pacientes.repositories.PacienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacientesController {
    private final PacienteRepository repository;

    public PacientesController(PacienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(
            @RequestBody @Valid DadosCadastroPaciente dados,
            UriComponentsBuilder uriBuilder
    ) {
        var paciente = new Paciente(dados);
        repository.save(paciente);

        var uri = uriBuilder
                .path("/medicos/{id}")
                .buildAndExpand(paciente.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(
            @PageableDefault(size = 8, sort = {"nome"})Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(
            @PathVariable Long id
    ) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(medico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ResponseStatus> atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseStatus> excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }
}
