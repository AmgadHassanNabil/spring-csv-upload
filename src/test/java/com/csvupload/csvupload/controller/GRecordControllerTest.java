package com.csvupload.csvupload.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class GRecordControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGRecords() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/com/csvupload/csvupload/resources/exercise.csv");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String testFileText = reader.lines().collect(Collectors.joining(System.getProperty("line.separator")));

            MockMultipartFile file = new MockMultipartFile("file", "exercise.csv", "text/csv", testFileText.getBytes());
            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/grecords").file(file))
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location"));

            mockMvc.perform(get("/api/v1/grecords"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.records[0].code").value("271636001"))
                    .andExpect(jsonPath("$.records[0].source").value("ZIB"))
                    .andExpect(jsonPath("$.records[0].codeListCode").value("ZIB001"))
                    .andExpect(jsonPath("$.records", hasSize(18)));

            mockMvc.perform(get("/api/v1/grecords/61086009"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.records[0].code").value("61086009"))
                    .andExpect(jsonPath("$.records[0].source").value("ZIB"))
                    .andExpect(jsonPath("$.records[0].codeListCode").value("ZIB001"));

            mockMvc.perform(delete("/api/v1/grecords"))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/api/v1/grecords"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.records", hasSize(0)));
        }

    }
}
