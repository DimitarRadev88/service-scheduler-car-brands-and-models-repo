package bg.softuni.carBrandsAndModels.carBrands.exceptions;

public class BrandAlreadyExistsException extends IllegalStateException {
    public BrandAlreadyExistsException(String message) {
        super(message);
    }
}
