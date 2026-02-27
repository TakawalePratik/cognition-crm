package com.cognition.crm.service.impl.trainer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cognition.crm.dto.trainer.TrainerDto;
import com.cognition.crm.entity.Trainer;
import com.cognition.crm.repository.TrainerRepository;
import com.cognition.crm.service.trainer.TrainerService;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository repository;

    public TrainerServiceImpl(TrainerRepository repository) {
        this.repository = repository;
    }

    @Override
    public TrainerDto createTrainer(TrainerDto dto) {
        Trainer trainer = mapToEntity(dto);
        return mapToDTO(repository.save(trainer));
    }

    @Override
    public List<TrainerDto> getAllTrainers() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TrainerDto getTrainerById(Long id) {
        Trainer trainer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
        return mapToDTO(trainer);
    }

    @Override
    public TrainerDto updateTrainer(Long id, TrainerDto dto) {
        Trainer trainer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        trainer.setFullName(dto.getFullName());
        trainer.setEmail(dto.getEmail());
        trainer.setPhone(dto.getPhone());
        trainer.setSpecialization(dto.getSpecialization());
        trainer.setExperienceYears(dto.getExperienceYears());
        trainer.setIsActive(dto.getIsActive());

        return mapToDTO(repository.save(trainer));
    }

    @Override
    public void deleteTrainer(Long id) {
        repository.deleteById(id);
    }

    private TrainerDto mapToDTO(Trainer trainer) {
        TrainerDto dto = new TrainerDto();
        dto.setTrainerId(trainer.getTrainerId());
        dto.setFullName(trainer.getFullName());
        dto.setEmail(trainer.getEmail());
        dto.setPhone(trainer.getPhone());
        dto.setSpecialization(trainer.getSpecialization());
        dto.setExperienceYears(trainer.getExperienceYears());
        dto.setIsActive(trainer.getIsActive());
        return dto;
    }

    private Trainer mapToEntity(TrainerDto dto) {
        Trainer trainer = new Trainer();
        trainer.setFullName(dto.getFullName());
        trainer.setEmail(dto.getEmail());
        trainer.setPhone(dto.getPhone());
        trainer.setSpecialization(dto.getSpecialization());
        trainer.setExperienceYears(dto.getExperienceYears());
        trainer.setIsActive(dto.getIsActive());
        return trainer;
    }
}
