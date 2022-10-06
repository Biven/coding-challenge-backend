package com.bp.accelerator.test;

import com.bp.accelerator.test.controllers.ReviewerController;
import com.bp.accelerator.test.data.entity.Application;
import com.bp.accelerator.test.data.entity.PastProject;
import com.bp.accelerator.test.data.repository.ApplicationRepository;
import com.bp.accelerator.test.service.PdfGenerationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewerController.class)
public class ReviewerControllerMockTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ApplicationRepository applicationRepository;
    @MockBean
    private PdfGenerationService pdfGenerationService;

    @Test
    public void fetchApplicationsTest() throws Exception {
        Application application = new Application();
        application.setName("Name");
        application.setEmail("email@email.com");
        application.setGithubUser("biven");
        application.setPastProjects(List.of(new PastProject()));

        List<Application> applications = List.of(application);

        given(applicationRepository.findAll()).willReturn(applications);

        String expectedJson = "[{\"email\":\"email@email.com\",\"name\":\"Name\",\"githubUser\":\"biven\"," +
                "\"pastProjects\":[{\"id\":0,\"projectName\":null,\"employment\":null,\"capacity\":null," +
                "\"duration\":null,\"startYear\":0,\"role\":null,\"teamSize\":0,\"repoLink\":null,\"liveUrl\":null}]}]";

        mvc.perform(MockMvcRequestBuilders.get("/reviewer/applications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
