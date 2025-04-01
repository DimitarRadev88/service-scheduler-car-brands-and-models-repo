package bg.softuni.carBrandsAndModels.web;

import bg.softuni.carBrandsAndModels.carBrand.service.CarBrandService;
import bg.softuni.carBrandsAndModels.carBrand.service.dto.CarBrandDto;
import bg.softuni.carBrandsAndModels.carBrand.service.dto.SavedCarBrandDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CarBrandControllerTest {

    @MockitoBean
    private CarBrandService carBrandService;

    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllShouldReturnAllCarBrands() throws Exception {
        List<CarBrandDto> brands = List.of(
                new CarBrandDto("Audi"),
                new CarBrandDto("BMW"),
                new CarBrandDto("Mercedes"));
        when(carBrandService.getAll())
                .thenReturn(brands);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/brands/all")
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(brands)));

    }

    @Test
    public void testAddShouldReturnSavedCarBrandDtoWhenAdded() throws Exception {
        CarBrandDto carBrandDto = new CarBrandDto("Audi");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/brands/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(carBrandDto));

        SavedCarBrandDto savedCarBrandDto = new SavedCarBrandDto(UUID.randomUUID(), carBrandDto.name());

        when(carBrandService.doAdd(carBrandDto))
                .thenReturn(savedCarBrandDto);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(savedCarBrandDto)));
    }

}
