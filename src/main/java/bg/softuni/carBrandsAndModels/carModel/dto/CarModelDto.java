package bg.softuni.carBrandsAndModels.carModel.dto;

import java.util.UUID;

public record CarModelDto(
        UUID id,
        String name
) {
}
