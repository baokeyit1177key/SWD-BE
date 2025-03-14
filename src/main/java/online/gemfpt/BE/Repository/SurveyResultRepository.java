package online.gemfpt.BE.Repository;

import online.gemfpt.BE.entity.SurveySubmitResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResultRepository extends JpaRepository<SurveySubmitResult, Long> {

    List<SurveySubmitResult> findByEmail(String email);
    List<SurveySubmitResult> findBySurveyId(Long surveyId);
}
