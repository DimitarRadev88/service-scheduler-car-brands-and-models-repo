package bg.softuni.carBrandsAndModels.web;

import bg.softuni.carBrandsAndModels.carBrand.exception.BrandAlreadyExistsException;
import bg.softuni.carBrandsAndModels.carBrand.exception.BrandDoesNotExistException;
import bg.softuni.carBrandsAndModels.carModel.exception.InvalidModelNameException;
import bg.softuni.carBrandsAndModels.carModel.exception.ModelAlreadyExistsException;
import bg.softuni.carBrandsAndModels.carBrand.exception.InvalidBrandNameException;
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

    @ExceptionHandler({BrandAlreadyExistsException.class, ModelAlreadyExistsException.class})
    public ResponseEntity<String> handleExists(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({InvalidBrandNameException.class, InvalidModelNameException.class})
    public ResponseEntity<String> handleInvalidBrandRequestBody(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
