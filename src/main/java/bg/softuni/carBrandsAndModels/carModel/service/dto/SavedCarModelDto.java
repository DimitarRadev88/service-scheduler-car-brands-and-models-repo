package bg.softuni.carBrandsAndModels.carModel.service.dto;

import java.util.UUID;

public record SavedCarModelDto(
        UUID id,
        String brandName,
        String modelName
) {
}
