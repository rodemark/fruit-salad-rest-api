package team.rode.fruitsaladrestapi.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.rode.fruitsaladrestapi.DTO.request.SaladRequestDto;
import team.rode.fruitsaladrestapi.DTO.response.SaladNutritionResponseDto;
import team.rode.fruitsaladrestapi.DTO.response.SaladResponseDto;
import team.rode.fruitsaladrestapi.models.Salad;
import team.rode.fruitsaladrestapi.services.SaladService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salads")
public class SaladController {
    private final SaladService saladService;

    @Autowired
    public SaladController(SaladService saladService) {
        this.saladService = saladService;
    }

    @GetMapping
    public List<SaladResponseDto> getSalads() {
        return saladService.getSalads();
    }

    @GetMapping("/{saladName}/nutrition")
    public SaladNutritionResponseDto getSaladNutrition(@PathVariable String saladName) {
        return saladService.calculateNutritionPer100g(saladName);
    }

    @PostMapping
    public SaladResponseDto addSalad(@RequestBody @Valid SaladRequestDto saladRequestDto) {
        return saladService.addSalad(saladRequestDto);
    }
}
