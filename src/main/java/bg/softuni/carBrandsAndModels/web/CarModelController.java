package bg.softuni.carBrandsAndModels.web;

import bg.softuni.carBrandsAndModels.carModels.dto.CarModelAddDto;
import bg.softuni.carBrandsAndModels.carModels.dto.CarModelDto;
import bg.softuni.carBrandsAndModels.carModels.service.CarModelService;
import bg.softuni.carBrandsAndModels.carModels.service.dto.SavedCarModelDto;
import bg.softuni.carBrandsAndModels.web.exception.InvalidRequestBodyException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/models")
public class CarModelController {

    private final CarModelService carModelService;

    @Autowired
    public CarModelController(CarModelService carModelService) {
        this.carModelService = carModelService;
    }

    @GetMapping("/{brand}")
    public ResponseEntity<List<CarModelDto>> getAllByBrand(@PathVariable String brand) {
        if (brand.isEmpty()) {
            throw new InvalidRequestBodyException("Brand cannot be blank");
        }

        return ResponseEntity.ok(carModelService.getAllByBrand(brand));
    }

    @PostMapping("/add")
    public ResponseEntity<SavedCarModelDto> addModel(
            @RequestBody @Valid CarModelAddDto carModelDto,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestBodyException("Missing information in request body");
        }

        SavedCarModelDto savedCarModelDto = carModelService.doAdd(carModelDto);

        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(savedCarModelDto.id())
                                .toUri()
                ).body(savedCarModelDto);

    }

}
