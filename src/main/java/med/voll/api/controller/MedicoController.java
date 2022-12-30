package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

  private final MedicoRepository repository;

  public MedicoController(MedicoRepository repository) {
    this.repository = repository;
  }

  @PostMapping
  @Transactional
  public void cadastrar(
      @RequestBody @Valid DadosCadastroMedico dados) {
    repository.save(new Medico(dados));
  }

//  http://localhost:8080/medicos?size=1&page=0 -- Paginacao
  @GetMapping
  public Page<DadosListagemMedico> listar(
          Pageable paginacao
  ) {
    return repository
            .findAll(paginacao)
            .map(DadosListagemMedico::new);
  }
}
