package bg.softuni.carBrandsAndModels.carBrands.service;

import bg.softuni.carBrandsAndModels.carBrands.service.dto.CarBrandDto;
import bg.softuni.carBrandsAndModels.carBrands.service.dto.SavedCarBrandDto;

import java.util.List;

public interface CarBrandService {
    List<CarBrandDto> getAll();

    SavedCarBrandDto doAdd(CarBrandDto carBrandDto);

}
