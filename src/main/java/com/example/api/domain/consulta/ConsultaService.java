package com.example.api.domain.consulta;

import com.example.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import com.example.api.domain.medicos.models.Medico;
import com.example.api.domain.medicos.repositories.MedicosRepository;
import com.example.api.domain.pacientes.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicosRepository medicosRepository;
    private final PacienteRepository pacienteRepository;
    private final Set<ValidadorAgendamentoDeConsulta> validadores;

    public void agendar(DadosAgendamentoConsulta dados) {

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.findById(dados.idPaciente())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        var medico = medicosRepository.findById(dados.idMedico())
                .orElseGet(() -> escolherMedico(dados));

        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.especialidade() == null) {
            throw new RuntimeException("Especialidade é obrigatória quando o médico não é informado");
        }

        return medicosRepository.escolherMedicoAleatorioLivreNaData(
                dados.especialidade(),
                dados.data(),
                dados.data().plusHours(1));
    }
}
