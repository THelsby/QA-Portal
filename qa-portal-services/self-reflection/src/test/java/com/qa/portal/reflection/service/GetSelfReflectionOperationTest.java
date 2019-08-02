package com.qa.portal.reflection.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.portal.common.exception.QaResourceNotFoundException;
import com.qa.portal.common.persistence.entity.TrainerEntity;
import com.qa.portal.common.persistence.repository.QaTrainerRepository;
import com.qa.portal.reflection.dto.ReflectionDto;
import com.qa.portal.reflection.persistence.entity.ReflectionEntity;
import com.qa.portal.reflection.persistence.repository.ReflectionRepository;
import com.qa.portal.reflection.service.mapper.ReflectionMapper;

@RunWith(MockitoJUnitRunner.class)
public class GetSelfReflectionOperationTest {

	@Mock
	private ReflectionRepository reflectionRepository;
	
	@Mock
	private ReflectionMapper reflectionMapper;
	
	@Mock
	private ReflectionEntity reflectionEntity;
	
	@Mock
	private ReflectionDto reflectionDto;
	
	@Mock
	private TrainerEntity trainerEntity;
	
	@Mock
	private QaTrainerRepository trainerRepository;
	
	private final String USER_NAME = "TEST_USER", FAKE_NAME = "FAKE_USER";
	
	private final LocalDate DATE = LocalDate.now();
	
	private final Integer REFLECTION_ID = 1, FAKE_ID = 2;
	
	@InjectMocks
	private GetSelfReflectionOperation operation;

	@Test
	public void getSelfReflectionByIdTest() {
		setPreConditions();
		executeActions();
		checkPostConditions();
	}
	
	@Test(expected = QaResourceNotFoundException.class)
	public void getSelfReflectionIdNotFoundTest() {
		operation.getSelfReflectionById(FAKE_ID);
	}
	
	@Test
	public void getSelfReflectionByUserAndDateTest() {
		setPreConditions();
		executeActionsUserAndDate();
		checkPostConditionsUserAndDate();
	}
	
	@Test(expected = QaResourceNotFoundException.class)
	public void getSelfReflectionByUserAndDateTrainerNotFoundTest() {
		operation.getSelfReflectionByUserAndDate(FAKE_NAME, DATE);
	}
	
	private void setPreConditions() {
		when(reflectionRepository.findById(REFLECTION_ID)).thenReturn(Optional.of(reflectionEntity));
		when(trainerRepository.findByUserName(USER_NAME)).thenReturn(Optional.of(trainerEntity));
		when(reflectionRepository.findByReviewerAndFormDate(trainerEntity, DATE)).thenReturn(Optional.of(reflectionEntity));
		when(reflectionMapper.mapToReflectionDto(reflectionEntity)).thenReturn(reflectionDto);
	}
	
	private void executeActions() {
		operation.getSelfReflectionById(REFLECTION_ID);
	}

	private void checkPostConditions() {
		verify(reflectionRepository).findById(REFLECTION_ID);
	}
	
	private void executeActionsUserAndDate() {
		operation.getSelfReflectionByUserAndDate(USER_NAME, DATE);
	}

	private void checkPostConditionsUserAndDate() {
		verify(trainerRepository).findByUserName(USER_NAME);
		verify(reflectionRepository).findByReviewerAndFormDate(trainerEntity, DATE);
		verify(reflectionRepository).findByReviewerAndFormDate(trainerEntity, DATE);
		verify(reflectionMapper).mapToReflectionDto(reflectionEntity);
	}
}
