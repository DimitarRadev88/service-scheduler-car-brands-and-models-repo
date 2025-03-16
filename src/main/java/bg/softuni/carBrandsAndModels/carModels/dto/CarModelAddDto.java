package bg.softuni.carBrandsAndModels.carModels.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CarModelAddDto(
        @NotBlank(message = "New model brand cannot be empty")
        String brand,
        @NotBlank(message = "New model name cannot be empty")
        String model
) {

}
