package com.cognition.crm.service.trainer;

import java.util.List;

import com.cognition.crm.dto.trainer.TrainerDto;

public interface TrainerService {

    TrainerDto createTrainer(TrainerDto dto);
    List<TrainerDto> getAllTrainers();
    TrainerDto getTrainerById(Long id);
    TrainerDto updateTrainer(Long id, TrainerDto dto);
    void deleteTrainer(Long id);
}