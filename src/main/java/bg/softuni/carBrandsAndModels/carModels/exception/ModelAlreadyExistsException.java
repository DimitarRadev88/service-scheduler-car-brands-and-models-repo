package bg.softuni.carBrandsAndModels.carModels.exception;

public class ModelAlreadyExistsException extends RuntimeException {
    public ModelAlreadyExistsException(String message) {
        super(message);
    }
}
