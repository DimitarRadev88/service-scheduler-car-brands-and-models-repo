package bg.softuni.carBrandsAndModels.carModels.service.dto;

import java.util.UUID;

public record SavedCarModelDto(
        UUID id,
        String brandName,
        String modelName
) {
}
