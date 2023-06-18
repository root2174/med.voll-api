package com.example.api.controllers;

import com.example.api.domain.medicos.dto.DadosAtualizarMedico;
import com.example.api.domain.medicos.dto.DadosCadastroMedico;
import com.example.api.domain.medicos.dto.DadosListagemMedico;
import com.example.api.domain.medicos.models.Medico;
import com.example.api.domain.medicos.repositories.MedicosRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicosController {
    private final MedicosRepository repository;

   public MedicosController(MedicosRepository repository) {
       this.repository = repository;
   }

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(
            @PageableDefault(size = 8, sort = {"nome"}) Pageable paginacao){
       return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizarMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(
            @PathVariable Long id
    ) {
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
}
