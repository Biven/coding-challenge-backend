package com.bp.accelerator.test;

import com.bp.accelerator.test.controllers.ApplicantController;
import com.bp.accelerator.test.data.repository.ApplicationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ApplicantController.class)
public class ApplicantControllerMockTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private KafkaTemplate kafkaTemplate;
    @MockBean
    private ApplicationRepository applicationRepository;

    @Test
    public void postApplicationTest() throws Exception {
        String body = "{" +
                "    \"email\": \"biven.mail@gmail.com\"," +
                "    \"name\": \"Yauheni\"," +
                "    \"githubUser\": \"biven\"," +
                "    \"pastProjects\": [" +
                "        {\n" +
                "            \"projectName\": \"project1\"," +
                "            \"employment\": \"freelance\"," +
                "            \"capacity\": \"fullTime\"," +
                "            \"startYear\": 2010," +
                "            \"role\": \"developer\"," +
                "            \"teamSize\": 10," +
                "            \"duration\": \"P2M3D\"" +
                "        }" +
                "    ]" +
                "}";


        mvc.perform(MockMvcRequestBuilders.post("/applicant")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
