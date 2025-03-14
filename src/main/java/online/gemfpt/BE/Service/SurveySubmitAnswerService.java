package online.gemfpt.BE.Service;


import online.gemfpt.BE.Repository.SurveySubmitAnswerRepository;
import online.gemfpt.BE.entity.SurveySubmitAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveySubmitAnswerService {
    @Autowired
    private SurveySubmitAnswerRepository surveySubmitAnswerRepository;

    public List<SurveySubmitAnswer> saveAllAnswers(List<SurveySubmitAnswer> answers) {
        return surveySubmitAnswerRepository.saveAll(answers);
    }
}
