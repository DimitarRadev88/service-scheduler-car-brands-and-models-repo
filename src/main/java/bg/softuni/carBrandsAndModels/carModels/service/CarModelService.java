package bg.softuni.carBrandsAndModels.carModels.service;

import bg.softuni.carBrandsAndModels.carModels.dto.CarModelDto;

import java.util.List;

public interface CarModelService {
    List<CarModelDto> getAllByBrand(String brand);
}
