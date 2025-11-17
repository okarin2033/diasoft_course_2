package ru.diasoft.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.diasoft.course.dto.BookCommentDto;
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
class CommentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crudComments() throws Exception {
        var createBookReq = new BookRequest("Книга для комментариев", 1L, 1L);
        MvcResult bookRes = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookReq)))
                .andExpect(status().isCreated())
                .andReturn();
        long bookId = objectMapper.readTree(bookRes.getResponse().getContentAsByteArray()).get("id").asLong();

        String payload = "{\"text\":\"Первый комментарий\"}";
        MvcResult commentRes = mockMvc.perform(post("/api/books/{bookId}/comments", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andReturn();
        BookCommentDto created = objectMapper.readValue(commentRes.getResponse().getContentAsByteArray(), BookCommentDto.class);
        assertThat(created.id()).isNotNull();

        mockMvc.perform(get("/api/books/{bookId}/comments", bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].text").value("Первый комментарий"));

        String updatePayload = "{\"text\":\"Обновленный комментарий\"}";
        mockMvc.perform(put("/api/comments/{id}", created.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Обновленный комментарий"));

        mockMvc.perform(delete("/api/comments/{id}", created.id()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/comments/{id}", created.id()))
                .andExpect(status().isNotFound());
    }
}
