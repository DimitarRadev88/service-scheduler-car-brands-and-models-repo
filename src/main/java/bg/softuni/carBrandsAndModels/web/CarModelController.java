package bg.softuni.carBrandsAndModels.web;

import bg.softuni.carBrandsAndModels.carModels.dto.CarModelDto;
import bg.softuni.carBrandsAndModels.carModels.service.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(carModelService.getAllByBrand(brand));
    }

}
