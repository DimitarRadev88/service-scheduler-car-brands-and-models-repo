package bg.softuni.carBrandsAndModels.web;

import bg.softuni.carBrandsAndModels.carBrand.exception.BrandAlreadyExistsException;
import bg.softuni.carBrandsAndModels.carBrand.exception.BrandDoesNotExistException;
import bg.softuni.carBrandsAndModels.carModel.exception.ModelAlreadyExistsException;
import bg.softuni.carBrandsAndModels.web.exception.InvalidRequestBodyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BrandDoesNotExistException.class)
    public ResponseEntity<String> handleBrandDoesNotExist(BrandDoesNotExistException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<String> handleBrandExists(BrandAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(ModelAlreadyExistsException.class)
    public ResponseEntity<String> handleModelExists(ModelAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidRequestBodyException.class)
    public ResponseEntity<String> handleInvalidRequestBody(InvalidRequestBodyException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
