package bg.softuni.carBrandsAndModels.carModel.service.impl;

import bg.softuni.carBrandsAndModels.carBrand.dao.CarBrandRepository;
import bg.softuni.carBrandsAndModels.carBrand.model.CarBrand;
import bg.softuni.carBrandsAndModels.carModel.dao.CarModelRepository;
import bg.softuni.carBrandsAndModels.carModel.dto.CarModelAddDto;
import bg.softuni.carBrandsAndModels.carModel.dto.CarModelDto;
import bg.softuni.carBrandsAndModels.carBrand.exception.BrandDoesNotExistException;
import bg.softuni.carBrandsAndModels.carModel.exception.ModelAlreadyExistsException;
import bg.softuni.carBrandsAndModels.carModel.model.CarModel;
import bg.softuni.carBrandsAndModels.carModel.service.CarModelService;
import bg.softuni.carBrandsAndModels.carModel.service.dto.SavedCarModelDto;
import bg.softuni.carBrandsAndModels.carBrand.exception.InvalidBrandNameException;
import bg.softuni.carBrandsAndModels.carModel.exception.InvalidModelNameException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CarModelServiceImpl implements CarModelService {

    private final CarModelRepository carModelRepository;
    private final CarBrandRepository carBrandRepository;

    @Autowired
    public CarModelServiceImpl(CarModelRepository carModelRepository, CarBrandRepository carBrandRepository) {
        this.carModelRepository = carModelRepository;
        this.carBrandRepository = carBrandRepository;
    }

    @Override
    public List<CarModelDto> getAllByBrand(String brand) {
        validateBrand(brand);

        return carModelRepository
                .findAllByBrand_Name(brand)
                .stream()
                .map(model -> new CarModelDto(model.getId(), model.getName()))
                .toList();
    }

    @Override
    @Transactional
    public SavedCarModelDto doAdd(CarModelAddDto carModelDto) {
        validateBrand(carModelDto.brand());
        validateModel(carModelDto);

        CarBrand carBrand = carBrandRepository.findByName(carModelDto.brand()).get();

        CarModel carModel = new CarModel();

        carModel.setBrand(carBrand);
        carModel.setName(carModelDto.model());

        carBrand.getModels().add(carModel);
        CarModel save = carModelRepository.save(carModel);

        log.info("Car model added successfully");

        return new SavedCarModelDto(save.getId(), save.getBrand().getName(), save.getName());
    }

    private void validateModel(CarModelAddDto carModelDto) {
        if (carModelDto.model() == null || carModelDto.model().isBlank()) {
            throw new InvalidModelNameException("Invalid model name");
        }

        if (carModelRepository.existsCarModelByBrandNameAndName(carModelDto.brand(), carModelDto.model())) {
            throw new ModelAlreadyExistsException("Car model already exists");
        }
    }

    private void validateBrand(String brand) {
        if (brand == null || brand.isBlank()) {
            throw new InvalidBrandNameException("Brand cannot be blank");
        }

        if (!carBrandRepository.existsCarBrandByName(brand)) {
            throw new BrandDoesNotExistException("Car brand does not exist");
        }
    }

}
