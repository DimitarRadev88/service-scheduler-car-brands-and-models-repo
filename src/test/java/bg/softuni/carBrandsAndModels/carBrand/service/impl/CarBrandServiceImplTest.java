package bg.softuni.carBrandsAndModels.carBrand.service.impl;

import bg.softuni.carBrandsAndModels.carBrand.dao.CarBrandRepository;
import bg.softuni.carBrandsAndModels.carBrand.exception.BrandAlreadyExistsException;
import bg.softuni.carBrandsAndModels.carBrand.model.CarBrand;
import bg.softuni.carBrandsAndModels.carBrand.service.CarBrandService;
import bg.softuni.carBrandsAndModels.carBrand.service.dto.CarBrandDto;
import bg.softuni.carBrandsAndModels.carBrand.service.dto.SavedCarBrandDto;
import bg.softuni.carBrandsAndModels.carModel.model.CarModel;
import bg.softuni.carBrandsAndModels.carBrand.exception.InvalidBrandNameException;
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
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarBrandServiceImplTest {

    @Mock
    private CarBrandRepository carBrandRepository;

    private CarBrandService carBrandService;
    @Captor
    private ArgumentCaptor<CarBrand> captor;

    @BeforeEach
    public void setUp() {
        carBrandService = new CarBrandServiceImpl(carBrandRepository);
        captor = ArgumentCaptor.forClass(CarBrand.class);
    }

    @Test
    public void tetGetAllShouldReturnAllCarBrands() {
        List<CarBrand> brands = List.of(
                new CarBrand(UUID.randomUUID(), "Audi", new ArrayList<>()),
                new CarBrand(UUID.randomUUID(), "BMW", List.of(
                        new CarModel(UUID.randomUUID(), "120", null))
                )
        );
        when(carBrandRepository.findAll())
                .thenReturn(brands);

        List<CarBrandDto> all = carBrandService.getAll();

        Assertions.assertEquals(brands.stream().map(this::mapToCarBrandDto).toList(), all);

    }

    @Test
    public void testDoAddShouldThrowWhenCarBrandNameIsNotValid() {
        CarBrandDto dto = new CarBrandDto(" ");

        Assertions.assertThrows(InvalidBrandNameException.class, () -> carBrandService.doAdd(dto));
    }

    @Test
    public void testDoAddShouldThrowWhenCarBrandExists() {
        CarBrandDto dto = new CarBrandDto("Audi");

        when(carBrandRepository.existsCarBrandByName(dto.name()))
                .thenReturn(true);

        Assertions.assertThrows(BrandAlreadyExistsException.class, () -> carBrandService.doAdd(dto));
    }

    @Test
    public void testDoAddShouldAddWhenCarBrandDoesNotExist() {
        CarBrandDto dto = new CarBrandDto("Audi");

        when(carBrandRepository.existsCarBrandByName(dto.name()))
                .thenReturn(false);

        CarBrand expected = new CarBrand(UUID.randomUUID(), "Audi", new ArrayList<>());
        when(carBrandRepository.save(Mockito.any(CarBrand.class)))
                .thenReturn(expected);

        SavedCarBrandDto savedCarBrandDto = carBrandService.doAdd(dto);

        verify(carBrandRepository).save(captor.capture());

        CarBrand value = captor.getValue();

        Assertions.assertEquals(expected.getName(), value.getName());
        Assertions.assertEquals(expected.getId(), savedCarBrandDto.id());
        Assertions.assertEquals(expected.getName(), savedCarBrandDto.name());

    }


    private CarBrandDto mapToCarBrandDto(CarBrand brand) {
        return new CarBrandDto(brand.getName());
    }

}
