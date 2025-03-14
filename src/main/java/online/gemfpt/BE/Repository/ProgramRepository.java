package online.gemfpt.BE.Repository;

import online.gemfpt.BE.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByParticipantsId(Long userId);
}