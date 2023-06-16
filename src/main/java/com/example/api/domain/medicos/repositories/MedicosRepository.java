package com.example.api.domain.medicos.repositories;

import com.example.api.domain.medicos.models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicosRepository extends JpaRepository<Medico, Long> {
}
