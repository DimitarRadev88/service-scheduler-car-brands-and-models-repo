package bg.softuni.carBrandsAndModels.carBrand.service.dto;

import jakarta.validation.constraints.NotBlank;

public record CarBrandDto(
        @NotBlank(message = "Brand name cannot be blank")
        String name
) {
}