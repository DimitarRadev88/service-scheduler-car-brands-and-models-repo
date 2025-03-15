package bg.softuni.carBrandsAndModels.web;

import bg.softuni.carBrandsAndModels.carBrands.exceptions.BrandAlreadyExistsException;
import bg.softuni.carBrandsAndModels.carModels.exception.BrandDoesNotExistException;
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
    public ResponseEntity<String> handleExists(BrandAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
