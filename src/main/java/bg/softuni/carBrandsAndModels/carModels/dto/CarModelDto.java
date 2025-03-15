package bg.softuni.carBrandsAndModels.carModels.dto;

import java.util.UUID;

public record CarModelDto(
        UUID id,
        String name
) {
}
