package bg.softuni.carBrandsAndModels.web;

import bg.softuni.carBrandsAndModels.carBrand.service.CarBrandService;
import bg.softuni.carBrandsAndModels.carBrand.service.dto.CarBrandDto;
import bg.softuni.carBrandsAndModels.carBrand.service.dto.SavedCarBrandDto;
import bg.softuni.carBrandsAndModels.web.exception.InvalidRequestBodyException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class CarBrandController {

    private final CarBrandService carBrandService;

    public CarBrandController(CarBrandService carBrandService) {
        this.carBrandService = carBrandService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarBrandDto>> getAll() {
        return ResponseEntity.ok(carBrandService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<SavedCarBrandDto> add(
            @RequestBody @Valid CarBrandDto carBrandDto,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestBodyException("Missing information in request body");
        }

        SavedCarBrandDto savedCarBrandDto = carBrandService.doAdd(carBrandDto);

        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(savedCarBrandDto.id())
                                .toUri()
                ).body(savedCarBrandDto);
    }

}
