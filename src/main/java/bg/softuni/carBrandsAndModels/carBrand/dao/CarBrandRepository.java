package bg.softuni.carBrandsAndModels.carBrand.dao;

import bg.softuni.carBrandsAndModels.carBrand.model.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, UUID> {

    boolean existsCarBrandByName(String name);

    Optional<CarBrand> findByName(String name);
}
