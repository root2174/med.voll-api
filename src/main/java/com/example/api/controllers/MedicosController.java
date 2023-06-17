package com.example.api.controllers;

import com.example.api.domain.medicos.dto.DadosCadastroMedico;
import com.example.api.domain.medicos.dto.DadosListagemMedico;
import com.example.api.domain.medicos.models.Medico;
import com.example.api.domain.medicos.repositories.MedicosRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicosController {

   @Autowired
    private MedicosRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
    }

    @GetMapping
    public List<DadosListagemMedico> listar(){
       return repository.findAll().stream().map(DadosListagemMedico::new).toList();
    }
}
