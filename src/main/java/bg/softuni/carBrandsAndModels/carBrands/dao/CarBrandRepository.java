package bg.softuni.carBrandsAndModels.carBrands.dao;

import bg.softuni.carBrandsAndModels.carBrands.model.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, UUID> {

}
