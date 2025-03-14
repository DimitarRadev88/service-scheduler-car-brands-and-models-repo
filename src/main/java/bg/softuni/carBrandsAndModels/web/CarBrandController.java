package bg.softuni.carBrandsAndModels.web;

import bg.softuni.carBrandsAndModels.carBrands.service.CarBrandService;
import bg.softuni.carBrandsAndModels.carBrands.service.dto.CarBrandDto;
import bg.softuni.carBrandsAndModels.carBrands.service.dto.CarBrandListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
