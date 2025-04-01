package bg.softuni.carBrandsAndModels.carModel.exception;

public class ModelAlreadyExistsException extends RuntimeException {
    public ModelAlreadyExistsException(String message) {
        super(message);
    }
}
