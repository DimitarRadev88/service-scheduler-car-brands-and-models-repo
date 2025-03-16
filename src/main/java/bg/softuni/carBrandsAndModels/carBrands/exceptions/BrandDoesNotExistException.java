package bg.softuni.carBrandsAndModels.carBrands.exceptions;

public class BrandDoesNotExistException extends RuntimeException {
    public BrandDoesNotExistException(String message) {
        super(message);
    }
}
