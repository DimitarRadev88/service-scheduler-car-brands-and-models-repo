package bg.softuni.carBrandsAndModels.carModels.service.impl;

import bg.softuni.carBrandsAndModels.carModels.dao.CarModelRepository;
import bg.softuni.carBrandsAndModels.carModels.dto.CarModelDto;
import bg.softuni.carBrandsAndModels.carModels.service.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarModelServiceImpl implements CarModelService {

    private final CarModelRepository carModelRepository;

    @Autowired
    public CarModelServiceImpl(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    @Override
    public List<CarModelDto> getAllByBrand(String brand) {
        return carModelRepository
                .findAllByBrand_Name(brand)
                .stream()
                .map(model -> new CarModelDto(model.getName()))
                .toList();
    }
}
