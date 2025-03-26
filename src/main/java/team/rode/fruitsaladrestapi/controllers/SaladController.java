package team.rode.fruitsaladrestapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Salads", description = "Operations related to salads management")
public class SaladController {
    private final SaladService saladService;
    private final NutritionCalculatorService nutritionCalculatorService;

    @Autowired
    public SaladController(SaladService saladService, NutritionCalculatorService nutritionCalculatorService) {
        this.saladService = saladService;
        this.nutritionCalculatorService = nutritionCalculatorService;
    }

    @GetMapping
    @Operation(summary = "Get all salads", description = "Returns a list of all salads")
    public List<SaladResponseDto> getSalads() {
        return saladService.getSalads();
    }

    @GetMapping("/{saladId}/nutrition")
    @Operation(summary = "Get nutrition per 100g", description = "Calculates and returns the nutrition per 100g for a given salad")
    public SaladNutritionResponseDto getSaladNutritionPer100gByName(@PathVariable Long saladId) {
        return nutritionCalculatorService.calculateNutritionPer100g(saladId);
    }

    @GetMapping("/duplicates")
    @Operation(summary = "Find duplicate salads", description = "Returns groups of salads that have identical recipes")
    public List<List<SaladResponseDto>> getDuplicateSalads() {
        return saladService.findDuplicateSalads();
    }

    @PostMapping
    @Operation(summary = "Add a new salad", description = "Creates a new salad with the provided details")
    public SaladResponseDto addSalad(@RequestBody @Valid SaladRequestDto saladRequestDto) {
        return saladService.addSalad(saladRequestDto);
    }

    @PatchMapping("/{saladId}")
    @Operation(summary = "Edit a salad", description = "Updates the details of an existing salad")
    public SaladResponseDto editSalad(@RequestBody @Valid SaladRequestDto saladRequestDto,
                                      @PathVariable Long saladId) {
        return saladService.editSalad(saladRequestDto, saladId);
    }

    @DeleteMapping("/{saladId}")
    @Operation(summary = "Delete a salad", description = "Removes a salad by its ID")
    private void deleteSalad(@PathVariable Long saladId) {
        saladService.deleteSalad(saladId);
    }
}
