package bg.softuni.carBrandsAndModels.carModels.dao;

import bg.softuni.carBrandsAndModels.carModels.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CarModelRepository extends JpaRepository<CarModel, UUID> {
    List<CarModel> findAllByBrand_Name(String brandName);
}
