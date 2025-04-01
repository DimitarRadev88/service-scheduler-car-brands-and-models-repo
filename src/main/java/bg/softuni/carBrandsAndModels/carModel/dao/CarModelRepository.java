package bg.softuni.carBrandsAndModels.carModel.dao;

import bg.softuni.carBrandsAndModels.carModel.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CarModelRepository extends JpaRepository<CarModel, UUID> {
    List<CarModel> findAllByBrand_Name(String brandName);

    boolean existsCarModelByBrandNameAndName(String brandName, String name);
}
