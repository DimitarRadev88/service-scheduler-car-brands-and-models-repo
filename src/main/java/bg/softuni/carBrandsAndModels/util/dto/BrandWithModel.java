package bg.softuni.carBrandsAndModels.util.dto;

import lombok.Data;

import java.util.List;

@Data
public class BrandWithModel {
    String brand;
    List<String> models;
}
