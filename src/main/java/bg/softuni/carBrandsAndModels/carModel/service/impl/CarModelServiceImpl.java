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
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if (!carBrandRepository.existsCarBrandByName(brand)) {
            throw new BrandDoesNotExistException("Car brand does not exist");
        }


        return carModelRepository
                .findAllByBrand_Name(brand)
                .stream()
                .map(model -> new CarModelDto(model.getId(), model.getName()))
                .toList();
    }

    @Override
    @Transactional
    public SavedCarModelDto doAdd(CarModelAddDto carModelDto) {
        Optional<CarBrand> optionalBrand = carBrandRepository.findByName(carModelDto.brand());
        if (optionalBrand.isEmpty()) {
            throw new BrandDoesNotExistException("Car brand does not exist");
        }

        if (carModelRepository.existsCarModelByBrandNameAndName(carModelDto.brand(), carModelDto.model())) {
            throw new ModelAlreadyExistsException("Car model already exists");
        }

        CarBrand carBrand = optionalBrand.get();

        CarModel carModel = new CarModel();

        carModel.setBrand(carBrand);
        carModel.setName(carModelDto.model());

        carBrand.getModels().add(carModel);
        CarModel save = carModelRepository.save(carModel);

        log.info("Car model added successfully");

        return new SavedCarModelDto(save.getId(), save.getBrand().getName(), save.getName());
    }
}
