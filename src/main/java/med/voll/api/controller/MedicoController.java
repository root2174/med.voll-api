package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

  private final MedicoRepository repository;

  public MedicoController(MedicoRepository repository) {
    this.repository = repository;
  }

  @PostMapping
  @Transactional
  public ResponseEntity<DadosDetalhamentoMedico> cadastrar(
      @RequestBody @Valid DadosCadastroMedico dados,
      UriComponentsBuilder uriBuilder) {
    var medico = new Medico(dados);
    repository.save(medico);

    var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
    return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
  }

//  http://localhost:8080/medicos?size=2&page=0&sort=nome,desc -- Paginacao
  @GetMapping
  public ResponseEntity<Page<DadosListagemMedico>> listar(
          @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao
  ) {
    var page = repository
            .findAllByAtivoTrue(paginacao)
            .map(DadosListagemMedico::new);

    return ResponseEntity.ok(page);
  }

  @PutMapping
  @Transactional
  public ResponseEntity<DadosDetalhamentoMedico> atualizar(
          @RequestBody @Valid DadosAtualizacaoMedico dados
  ) {
    Medico medico = repository.getReferenceById(dados.id());
    medico.atualizarInformacoes(dados);

    return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<HttpStatusCode> excluir (
          @PathVariable Long id
  ) {
    Medico medico = repository.getReferenceById(id);
    medico.excluir();
    return ResponseEntity.noContent().build();
  }

}
