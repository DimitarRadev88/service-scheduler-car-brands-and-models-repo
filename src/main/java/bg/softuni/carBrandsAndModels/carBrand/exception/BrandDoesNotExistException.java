package bg.softuni.carBrandsAndModels.carBrand.exception;

public class BrandDoesNotExistException extends RuntimeException {
    public BrandDoesNotExistException(String message) {
        super(message);
    }
}
