package bg.softuni.carBrandsAndModels.carModel.dto;

import jakarta.validation.constraints.NotBlank;

public record CarModelAddDto(
        @NotBlank(message = "New model brand cannot be empty")
        String brand,
        @NotBlank(message = "New model name cannot be empty")
        String model
) {

}
