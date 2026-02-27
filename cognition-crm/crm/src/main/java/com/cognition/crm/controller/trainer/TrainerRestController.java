package com.cognition.crm.controller.trainer;


import com.cognition.crm.dto.trainer.TrainerDto;
import com.cognition.crm.service.trainer.TrainerService;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TrainerRestController {

    private final TrainerService service;

    public TrainerRestController(TrainerService service) {
        this.service = service;
    }

    // ==============================
    // ===== REST API SECTION ======
    // ==============================

    @PostMapping("/api/trainers")
    public TrainerDto createTrainer(@RequestBody TrainerDto dto) {
        return service.createTrainer(dto);
    }

    @GetMapping("/api/trainers")
    public List<TrainerDto> getAllTrainers() {
        return service.getAllTrainers();
    }

    @GetMapping("/api/trainers/{id}")
    public TrainerDto getTrainerById(@PathVariable Long id) {
        return service.getTrainerById(id);
    }

    @PutMapping("/api/trainers/{id}")
    public TrainerDto updateTrainer(@PathVariable Long id,
                                    @RequestBody TrainerDto dto) {
        return service.updateTrainer(id, dto);
    }

    @DeleteMapping("/api/trainers/{id}")
    public String deleteTrainer(@PathVariable Long id) {
        service.deleteTrainer(id);
        return "Trainer deleted successfully";
    }

    // =================================
    // ===== THYMELEAF SECTION =========
    // =================================

    @GetMapping("/trainers")
    public ModelAndView trainerListPage() {

        ModelAndView mv = new ModelAndView("trainers/list");
        mv.addObject("trainers", service.getAllTrainers());
        return mv;
    }

    @GetMapping("/trainers/new")
    public ModelAndView showTrainerForm() {

        ModelAndView mv = new ModelAndView("trainers/form");
        mv.addObject("trainer", new TrainerDto());
        return mv;
    }

    @PostMapping("/trainers/save")
    public ModelAndView saveTrainer(@ModelAttribute TrainerDto dto) {

        service.createTrainer(dto);
        return new ModelAndView("redirect:/trainers");
    }
}