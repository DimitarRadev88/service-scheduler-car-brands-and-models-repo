package bg.softuni.carBrandsAndModels.carBrands.dao;

import bg.softuni.carBrandsAndModels.carBrands.model.CarBrand;
import bg.softuni.carBrandsAndModels.carBrands.service.dto.CarBrandDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, UUID> {

    boolean existsCarBrandByName(String name);

    Optional<CarBrand> findByName(String name);
}
