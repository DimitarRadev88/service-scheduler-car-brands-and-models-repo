package bg.softuni.carBrandsAndModels.carBrand.service.impl;

import bg.softuni.carBrandsAndModels.carBrand.dao.CarBrandRepository;
import bg.softuni.carBrandsAndModels.carBrand.exception.BrandAlreadyExistsException;
import bg.softuni.carBrandsAndModels.carBrand.model.CarBrand;
import bg.softuni.carBrandsAndModels.carBrand.service.CarBrandService;
import bg.softuni.carBrandsAndModels.carBrand.service.dto.CarBrandDto;
import bg.softuni.carBrandsAndModels.carBrand.service.dto.SavedCarBrandDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CarBrandServiceImpl implements CarBrandService {

    private final CarBrandRepository carBrandRepository;

    @Autowired
    public CarBrandServiceImpl(CarBrandRepository carBrandRepository) {
        this.carBrandRepository = carBrandRepository;
    }

    @Override
    public List<CarBrandDto> getAll() {
        return carBrandRepository.findAll().stream().map(brand ->
                new CarBrandDto(
                        brand.getName())
        ).toList();
    }

    @Override
    public SavedCarBrandDto doAdd(CarBrandDto carBrandDto) {
        if (carBrandRepository.existsCarBrandByName(carBrandDto.name())) {
            throw new BrandAlreadyExistsException("Car brand already exists in db");
        }

        CarBrand brand = carBrandRepository.save(new CarBrand(null, carBrandDto.name(), new ArrayList<>()));

        log.info("Added car brand: {}", brand);

        return new SavedCarBrandDto(brand.getId(), brand.getName());
    }
}
