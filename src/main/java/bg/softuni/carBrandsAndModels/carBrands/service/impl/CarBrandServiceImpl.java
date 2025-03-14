package bg.softuni.carBrandsAndModels.carBrands.service.impl;

import bg.softuni.carBrandsAndModels.carBrands.dao.CarBrandRepository;
import bg.softuni.carBrandsAndModels.carBrands.service.CarBrandService;
import bg.softuni.carBrandsAndModels.carBrands.service.dto.CarBrandDto;
import bg.softuni.carBrandsAndModels.carBrands.service.dto.CarBrandListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
}
