package bg.softuni.carBrandsAndModels.web;

import bg.softuni.carBrandsAndModels.carModel.service.CarModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerIT {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private CarModelService carModelService;
    ;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCatchesInvalidBrandNameAndReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/models/ ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCatchesBrandDoesNotExistAndReturnsNotFound() throws Exception {
        mockMvc.perform(get("/models/missing")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCatchesInvalidModelNameAndReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/models/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("brand", "Audi")
                        .param("model", " "))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCatchesBrandAlreadyExistAndReturnsConflict() throws Exception {
        mockMvc.perform(post("/brands/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Audi\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testCatchesModelAlreadyExistAndReturnsConflict() throws Exception {
        mockMvc.perform(post("/models/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"brand\":\"Audi\", \"model\":\"A4\"}"))
                .andExpect(status().isConflict());
    }

}
