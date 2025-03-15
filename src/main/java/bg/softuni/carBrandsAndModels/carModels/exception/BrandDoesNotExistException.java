package bg.softuni.carBrandsAndModels.carModels.exception;

public class BrandDoesNotExistException extends IllegalArgumentException {
    public BrandDoesNotExistException(String message) {
        super(message);
    }
}
