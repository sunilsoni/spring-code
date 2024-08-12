package com.interview.notes.y2024.aug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.notes.y2024.aug.test1.Event;
import com.interview.notes.y2024.aug.test1.EventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
class EventControllerTest {

    ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository repository;

    @Test
    public void testCreation() throws Exception {
        Event expectedRecord = Event.builder().name("Test Country").build();
        Event actualRecord = om.readValue(mockMvc.perform(post("/event")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(), Event.class);
        Assertions.assertEquals(expectedRecord.getName(), actualRecord.getName());
    }

    @Test
    public void testGetEventById() throws Exception {
        // Assuming an event with ID 1 exists in the database
        mockMvc.perform(get("/event/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.location").exists())
                .andExpect(jsonPath("$.duration").exists())
                .andExpect(jsonPath("$.cost").exists());
    }

    @Test
    public void testGetEventByIdNotFound() throws Exception {
        mockMvc.perform(get("/event/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetTop3ByCost() throws Exception {
        MvcResult result = mockMvc.perform(get("/event/top3?by=cost"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Event[] events = om.readValue(content, Event[].class);
        
        Assertions.assertTrue(events[0].getCost() >= events[1].getCost());
        Assertions.assertTrue(events[1].getCost() >= events[2].getCost());
    }

    @Test
    public void testGetTop3ByDuration() throws Exception {
        MvcResult result = mockMvc.perform(get("/event/top3?by=duration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Event[] events = om.readValue(content, Event[].class);
        
        Assertions.assertTrue(events[0].getDuration() >= events[1].getDuration());
        Assertions.assertTrue(events[1].getDuration() >= events[2].getDuration());
    }

    @Test
    public void testGetTop3ByInvalidAttribute() throws Exception {
        mockMvc.perform(get("/event/top3?by=invalid"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetTotalByCost() throws Exception {
        mockMvc.perform(get("/event/total?by=cost"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void testGetTotalByDuration() throws Exception {
        mockMvc.perform(get("/event/total?by=duration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void testGetTotalByInvalidAttribute() throws Exception {
        mockMvc.perform(get("/event/total?by=invalid"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
