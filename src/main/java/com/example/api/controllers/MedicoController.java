package com.example.api.controllers;

import com.example.api.domain.medicos.dto.DadosAtualizarMedico;
import com.example.api.domain.medicos.dto.DadosCadastroMedico;
import com.example.api.domain.medicos.dto.DadosDetalhamentoMedico;
import com.example.api.domain.medicos.dto.DadosListagemMedico;
import com.example.api.domain.medicos.models.Medico;
import com.example.api.domain.medicos.repositories.MedicosRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/medicos")
public class MedicoController {
    private final MedicosRepository repository;

   public MedicoController(MedicosRepository repository) {
       this.repository = repository;
   }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(
            @RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder) {

       var medico = new Medico(dados);
       repository.save(medico);

        var uri = uriBuilder
                .path("/medicos/{id}")
                .buildAndExpand(medico.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(
            @PageableDefault(size = 8, sort = {"nome"}) Pageable paginacao){
       var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
       return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid DadosAtualizarMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseStatus> deletar(
            @PathVariable Long id
    ) {
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }
}
