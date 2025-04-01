package bg.softuni.carBrandsAndModels.carModel.service.impl;

import bg.softuni.carBrandsAndModels.carBrand.dao.CarBrandRepository;
import bg.softuni.carBrandsAndModels.carBrand.exception.BrandDoesNotExistException;
import bg.softuni.carBrandsAndModels.carBrand.exception.InvalidBrandNameException;
import bg.softuni.carBrandsAndModels.carBrand.model.CarBrand;
import bg.softuni.carBrandsAndModels.carModel.dao.CarModelRepository;
import bg.softuni.carBrandsAndModels.carModel.dto.CarModelAddDto;
import bg.softuni.carBrandsAndModels.carModel.dto.CarModelDto;
import bg.softuni.carBrandsAndModels.carModel.exception.InvalidModelNameException;
import bg.softuni.carBrandsAndModels.carModel.exception.ModelAlreadyExistsException;
import bg.softuni.carBrandsAndModels.carModel.model.CarModel;
import bg.softuni.carBrandsAndModels.carModel.service.CarModelService;
import bg.softuni.carBrandsAndModels.carModel.service.dto.SavedCarModelDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarModelServiceImplTest {

    @Mock
    private CarBrandRepository carBrandRepository;
    @Mock
    private CarModelRepository carModelRepository;

    private CarModelService carModelService;
    @Captor
    private ArgumentCaptor<CarModel> captor;
    @BeforeEach
    public void setUp() {
        captor = ArgumentCaptor.forClass(CarModel.class);
        carModelService = new CarModelServiceImpl(carModelRepository, carBrandRepository);
    }

    @Test
    public void testGetAllByBrandShouldThrowWhenBrandNotFound() {
        String brand = "Audi";
        when(carBrandRepository.existsCarBrandByName(brand))
                .thenReturn(false);

        Assertions.assertThrows(BrandDoesNotExistException.class, () -> carModelService.getAllByBrand(brand));
    }

    @Test
    public void testGetAllByBrandShouldReturnAllModelsForBrand() {
        CarBrand carBrand = new CarBrand(UUID.randomUUID(), "Audi", new ArrayList<>());
        List<CarModel> carModels = List.of(
                new CarModel(UUID.randomUUID(), "A1", carBrand),
                new CarModel(UUID.randomUUID(), "A2", carBrand),
                new CarModel(UUID.randomUUID(), "A3", carBrand));

        when(carBrandRepository.existsCarBrandByName(carBrand.getName()))
                .thenReturn(true);

        when(carModelRepository.findAllByBrand_Name(carBrand.getName()))
                .thenReturn(carModels);

        List<CarModelDto> expected = carModels.stream().map(CarModelServiceImplTest::mapToCarModelDto).toList();

        Assertions.assertEquals(expected, carModelService.getAllByBrand(carBrand.getName()));
    }

    @Test
    public void testDoAddThrowsWhenBrandIsEmpty() {
        String brand = " ";

        Assertions.assertThrows(InvalidBrandNameException.class, () -> carModelService.doAdd(new CarModelAddDto(brand, "model")));
    }

    @Test
    public void testDoAddThrowsWhenBrandExistsAndModelIsEmpty() {
        String brand = "Audi";
        String model = " ";

        when(carBrandRepository.existsCarBrandByName(brand))
                .thenReturn(true);

        Assertions.assertThrows(InvalidModelNameException.class, () -> carModelService.doAdd(new CarModelAddDto(brand, model)));
    }

    @Test
    public void testDoAddThrowsWhenBrandDoesNotExist() {
        String brand = "Audi";

        when(carBrandRepository.existsCarBrandByName(brand))
        .thenReturn(false);

        Assertions.assertThrows(BrandDoesNotExistException.class, () -> carModelService.doAdd(new CarModelAddDto(brand, brand)));
    }

    @Test
    public void testDoAddThrowsWhenBrandAndModelExist() {
        CarBrand carBrand = new CarBrand(UUID.randomUUID(), "Audi", new ArrayList<>());
        CarModel carModel = new CarModel(UUID.randomUUID(), "A1", carBrand);
        carBrand.getModels().add(carModel);
        CarModelAddDto carModelDto = new CarModelAddDto(carBrand.getName(), carModel.getName());

        when(carBrandRepository.existsCarBrandByName(carBrand.getName()))
                .thenReturn(true);
        when(carModelRepository.existsCarModelByBrandNameAndName(carBrand.getName(), carModel.getName()))
                .thenReturn(true);

        Assertions.assertThrows(ModelAlreadyExistsException.class, () -> carModelService.doAdd(carModelDto));
    }

    @Test
    public void testDoAddAddsWhenBrandExistsAndModelDoesNotExist() {
        CarBrand carBrand = new CarBrand(UUID.randomUUID(), "Audi", new ArrayList<>());
        CarModel carModel = new CarModel(UUID.randomUUID(), "A1", carBrand);
        carBrand.getModels().add(carModel);
        CarModelAddDto carModelDto = new CarModelAddDto(carBrand.getName(), carModel.getName());

        when(carBrandRepository.existsCarBrandByName(carBrand.getName()))
                .thenReturn(true);
        when(carBrandRepository.findByName(carBrand.getName()))
                .thenReturn(Optional.of(carBrand));
        when(carModelRepository.existsCarModelByBrandNameAndName(carBrand.getName(), carModel.getName()))
                .thenReturn(false);
        when(carModelRepository.save(Mockito.any(CarModel.class)))
                .thenReturn(carModel);

        SavedCarModelDto savedCarModelDto = carModelService.doAdd(carModelDto);

        verify(carModelRepository).save(captor.capture());

        CarModel value = captor.getValue();

        Assertions.assertEquals(carModel.getId(), savedCarModelDto.id());
        Assertions.assertEquals(carModel.getBrand().getName(), savedCarModelDto.brandName());
        Assertions.assertEquals(carModel.getName(), savedCarModelDto.modelName());
        Assertions.assertEquals(carModel.getName(), value.getName());
        Assertions.assertEquals(carModel.getBrand(), value.getBrand());
    }

    private static CarModelDto mapToCarModelDto(CarModel model) {
        return new CarModelDto(model.getId(), model.getName());
    }

}
