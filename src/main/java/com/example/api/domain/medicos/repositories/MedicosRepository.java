package com.example.api.domain.medicos.repositories;

import com.example.api.domain.medicos.enums.Especialidade;
import com.example.api.domain.medicos.models.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MedicosRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            SELECT m FROM Medico m
            WHERE m.especialidade = :especialidade
            AND m.ativo = true
            AND m.id NOT IN (
                SELECT c.medico.id FROM Consulta c
                WHERE c.data BETWEEN :data AND :dataFim
            )
            ORDER BY RAND()
            LIMIT 1
    """)
    Medico escolherMedicoAleatorioLivreNaData(
            @Param("especialidade") Especialidade especialidade,
            @Param("data") LocalDateTime data,
            @Param("dataFim") LocalDateTime dataFim
    );

    @Query("""
            SELECT m.ativo FROM Medico m
            WHERE m.id = :id
    """)
    Boolean findAtivoById(Long id);
}
