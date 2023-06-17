package com.example.api.controllers;

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
       return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }
}
