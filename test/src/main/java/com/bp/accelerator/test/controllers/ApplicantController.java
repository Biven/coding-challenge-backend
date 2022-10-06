package com.bp.accelerator.test.controllers;

import com.bp.accelerator.test.data.entity.Application;
import com.bp.accelerator.test.data.entity.Capacity;
import com.bp.accelerator.test.data.entity.Employment;
import com.bp.accelerator.test.data.entity.PastProject;
import com.bp.accelerator.test.data.repository.ApplicationRepository;
import com.bp.accelerator.test.model.ApplicationModel;
import com.bp.accelerator.test.model.PastProjectModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Period;

@RequestMapping("/applicant")
@RestController
public class ApplicantController {

    private final ApplicationRepository applicationRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ApplicantController(ApplicationRepository applicationRepository,
                               KafkaTemplate<String, String> kafkaTemplate) {
        this.applicationRepository = applicationRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void postApplication(@RequestBody ApplicationModel applicationModel) {
        applicationRepository.save(validateAndTransformModel(applicationModel));
        kafkaTemplate.send("application.posted", applicationModel.getEmail());
    }

    private Application validateAndTransformModel(ApplicationModel applicationModel) {
        Application application = new Application();
        if (StringUtils.isBlank(applicationModel.getEmail())) {
            throw new IllegalArgumentException("Email is required and can't be empty");
        }
        application.setEmail(applicationModel.getEmail());
        application.setName(applicationModel.getName());
        application.setGithubUser(applicationModel.getGithubUser());
        if (CollectionUtils.isEmpty(applicationModel.getPastProjects())) {
            throw new IllegalArgumentException("Should be presented at least one project");
        }
        for (PastProjectModel pastProjectModel : applicationModel.getPastProjects()) {
            PastProject pastProject = new PastProject();
            if (StringUtils.isBlank(pastProjectModel.getProjectName())) {
                throw new IllegalArgumentException("Project name should not be blank");
            }
            pastProject.setProjectName(pastProjectModel.getProjectName());

            if (!"freelance".equals(pastProjectModel.getEmployment())
                    && !"employed".equals(pastProjectModel.getEmployment())) {
                throw new IllegalArgumentException("Project " + pastProjectModel.getProjectName() + " has incorrect employment type");
            }
            pastProject.setEmployment(Employment.valueOf(pastProjectModel.getEmployment()));

            if (!"partTime".equals(pastProjectModel.getCapacity())
                    && !"fullTime".equals(pastProjectModel.getCapacity())) {
                throw new IllegalArgumentException("Project " + pastProjectModel.getProjectName() + " has incorrect capacity");
            }
            pastProject.setCapacity(Capacity.valueOf((pastProjectModel.getCapacity())));


            if (StringUtils.isBlank(pastProjectModel.getDuration())) {
                throw new IllegalArgumentException("Project " + pastProjectModel.getProjectName() + " should have duration");
            }
            try {
                pastProject.setDuration(Period.parse(pastProjectModel.getDuration()));
            } catch (Exception ex) {
                throw new IllegalArgumentException("Project " + pastProjectModel.getProjectName() + " has incorrect duration");
            }

            if (pastProjectModel.getStartYear() == null) {
                throw new IllegalArgumentException("Project " + pastProjectModel.getProjectName() + " should have defined start year");
            }
            pastProject.setStartYear(pastProjectModel.getStartYear());

            if (pastProjectModel.getRole() == null) {
                throw new IllegalArgumentException("Project " + pastProjectModel.getProjectName() + " should have defined role");
            }
            pastProject.setRole(pastProjectModel.getRole());

            if (pastProjectModel.getTeamSize() == null) {
                throw new IllegalArgumentException("Project " + pastProjectModel.getProjectName() + " should have defined team size");
            }
            pastProject.setTeamSize(pastProjectModel.getTeamSize());

            pastProject.setRepoLink(pastProjectModel.getRepoLink());
            pastProject.setLiveUrl(pastProjectModel.getLiveUrl());

            application.getPastProjects().add(pastProject);
        }
        return application;
    }

}
