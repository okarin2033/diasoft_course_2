package ru.diasoft.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.diasoft.course.dto.BookDto;
import ru.diasoft.course.dto.BookRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "springdoc.api-docs.enabled=false",
        "springdoc.swagger-ui.enabled=false",
        "spring.autoconfigure.exclude=org.springdoc.core.configuration.SpringDocConfiguration,org.springdoc.webmvc.core.configuration.SpringDocWebMvcConfiguration"
})
@AutoConfigureMockMvc(addFilters = false)
class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listAndCrudBook() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        BookRequest createReq = new BookRequest("Интеграционная книга", 1L, 1L);
        MvcResult createRes = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createReq)))
                .andExpect(status().isCreated())
                .andReturn();
        BookDto created = objectMapper.readValue(createRes.getResponse().getContentAsByteArray(), BookDto.class);
        assertThat(created.id()).isNotNull();

        mockMvc.perform(get("/api/books/{id}", created.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Интеграционная книга"));

        BookRequest updateReq = new BookRequest("Обновленная книга", 2L, 2L);
        mockMvc.perform(put("/api/books/{id}", created.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Обновленная книга"))
                .andExpect(jsonPath("$.authorId").value(2))
                .andExpect(jsonPath("$.genreId").value(2));

        mockMvc.perform(delete("/api/books/{id}", created.id()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/books/{id}", created.id()))
                .andExpect(status().isNotFound());
    }
}
