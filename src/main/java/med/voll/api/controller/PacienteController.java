package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(
            @RequestBody @Valid DadosCadastroPaciente dados,
            UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(dados);
        pacienteRepository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(
            @PageableDefault(sort = {"nome"}) Pageable paginacao) {
        var page = pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(
            @RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = pacienteRepository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> excluir(
            @PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }
}
