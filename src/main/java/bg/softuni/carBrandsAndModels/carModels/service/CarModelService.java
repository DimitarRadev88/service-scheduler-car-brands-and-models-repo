package bg.softuni.carBrandsAndModels.carModels.service;

import bg.softuni.carBrandsAndModels.carModels.dto.CarModelAddDto;
import bg.softuni.carBrandsAndModels.carModels.dto.CarModelDto;
import bg.softuni.carBrandsAndModels.carModels.service.dto.SavedCarModelDto;

import java.util.List;

public interface CarModelService {
    List<CarModelDto> getAllByBrand(String brand);

    SavedCarModelDto doAdd(CarModelAddDto carModelDto);

}
