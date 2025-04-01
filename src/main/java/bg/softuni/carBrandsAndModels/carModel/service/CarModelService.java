package bg.softuni.carBrandsAndModels.carModel.service;

import bg.softuni.carBrandsAndModels.carModel.dto.CarModelAddDto;
import bg.softuni.carBrandsAndModels.carModel.dto.CarModelDto;
import bg.softuni.carBrandsAndModels.carModel.service.dto.SavedCarModelDto;

import java.util.List;

public interface CarModelService {
    List<CarModelDto> getAllByBrand(String brand);

    SavedCarModelDto doAdd(CarModelAddDto carModelDto);

}
