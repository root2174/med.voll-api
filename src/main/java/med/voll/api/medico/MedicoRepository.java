package med.voll.api.medico;

import org.springframework.data.jpa.repository.JpaRepository;

// First argument is the Model for this repository and the second the primary key type
public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
