package com.qa.portal.reflection.service;

import java.util.Set;
import java.util.stream.Collectors;

import com.qa.portal.common.exception.QaResourceNotFoundException;
import com.qa.portal.common.persistence.entity.QaCohortEntity;
import com.qa.portal.common.persistence.repository.QaCohortRepository;
import com.qa.portal.reflection.dto.QuestionDto;
import com.qa.portal.reflection.persistence.entity.CohortQuestionEntity;
import com.qa.portal.reflection.persistence.repository.CohortQuestionRepository;
import com.qa.portal.reflection.util.mapper.QuestionMapper;

public class QuestionService {

	private CohortQuestionRepository cohortQuestionRepo;

	private QaCohortRepository cohortRepo;

	private QuestionMapper mapper;

	public Set<QuestionDto> getQuestionsForCohort(Integer cohortId) {
		QaCohortEntity cohort = this.cohortRepo.findById(cohortId)
				.orElseThrow(() -> new QaResourceNotFoundException("Cohort doesn't exist"));
		Set<CohortQuestionEntity> cohortQuestions = this.cohortQuestionRepo.findByCohort(cohort);
		return cohortQuestions.stream().map(cq -> cq.getQuestion()).map(this.mapper::mapToQuestionDto)
				.collect(Collectors.toSet());
	}

}
