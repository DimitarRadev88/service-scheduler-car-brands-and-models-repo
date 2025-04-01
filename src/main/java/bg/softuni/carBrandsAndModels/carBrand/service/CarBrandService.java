package bg.softuni.carBrandsAndModels.carBrand.service;

import bg.softuni.carBrandsAndModels.carBrand.service.dto.CarBrandDto;
import bg.softuni.carBrandsAndModels.carBrand.service.dto.SavedCarBrandDto;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CarBrandService {
    List<CarBrandDto> getAll();

    SavedCarBrandDto doAdd(CarBrandDto carBrandDto);

}
