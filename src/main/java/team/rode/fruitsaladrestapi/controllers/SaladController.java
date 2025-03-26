package team.rode.fruitsaladrestapi.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.rode.fruitsaladrestapi.DTO.request.SaladRequestDto;
import team.rode.fruitsaladrestapi.DTO.response.SaladNutritionResponseDto;
import team.rode.fruitsaladrestapi.DTO.response.SaladResponseDto;
import team.rode.fruitsaladrestapi.services.NutritionCalculatorService;
import team.rode.fruitsaladrestapi.services.SaladService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salads")
public class SaladController {
    private final SaladService saladService;
    private final NutritionCalculatorService nutritionCalculatorService;

    @Autowired
    public SaladController(SaladService saladService, NutritionCalculatorService nutritionCalculatorService) {
        this.saladService = saladService;
        this.nutritionCalculatorService = nutritionCalculatorService;
    }

    @GetMapping
    public List<SaladResponseDto> getSalads() {
        return saladService.getSalads();
    }

    @GetMapping("/{saladId}/nutrition")
    public SaladNutritionResponseDto getSaladNutritionPer100gByName(@PathVariable Long saladId) {
        return nutritionCalculatorService.calculateNutritionPer100g(saladId);
    }

    @PostMapping
    public SaladResponseDto addSalad(@RequestBody @Valid SaladRequestDto saladRequestDto) {
        return saladService.addSalad(saladRequestDto);
    }

    @PatchMapping("/{saladId}")
    public SaladResponseDto editSalad(@RequestBody @Valid SaladRequestDto saladRequestDto,
                                      @PathVariable Long saladId) {
        return saladService.editSalad(saladRequestDto, saladId);
    }

    @DeleteMapping("/{saladId}")
    private void deleteSalad(@PathVariable Long saladId) {
        saladService.deleteSalad(saladId);
    }
}
