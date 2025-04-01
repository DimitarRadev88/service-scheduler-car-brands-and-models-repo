package bg.softuni.carBrandsAndModels.web;

import bg.softuni.carBrandsAndModels.carBrand.exception.BrandDoesNotExistException;
import bg.softuni.carBrandsAndModels.carBrand.exception.InvalidBrandNameException;
import bg.softuni.carBrandsAndModels.carModel.dto.CarModelAddDto;
import bg.softuni.carBrandsAndModels.carModel.dto.CarModelDto;
import bg.softuni.carBrandsAndModels.carModel.service.CarModelService;
import bg.softuni.carBrandsAndModels.carModel.service.dto.SavedCarModelDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CarModelControllerTest {

    @MockitoBean
    private CarModelService carModelService;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testGetAllByBrandReturnsBadNotFoundWhenBrandDoesNotExist() throws Exception {
        String brand = "Brand";

        List<CarModelDto> carModelDtoList = List.of(new CarModelDto(UUID.randomUUID(), "Model"));

        when(carModelService.getAllByBrand(brand))
                .thenReturn(carModelDtoList);

        mockMvc.perform(get("/models/" + brand)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(carModelDtoList)));
    }

    @Test
    public void testAddModelReturnsSavedCarModelDtoWhenAdded() throws Exception {
        CarModelAddDto modelAddDto = new CarModelAddDto("Brand", "Model");

        SavedCarModelDto savedCarModelDto = new SavedCarModelDto(UUID.randomUUID(), "Brand", "Model");
        when(carModelService.doAdd(Mockito.any(CarModelAddDto.class)))
                .thenReturn(savedCarModelDto);

        String content = mapper.writeValueAsString(savedCarModelDto);
        mockMvc.perform(post("/models/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(content));
    }

}
