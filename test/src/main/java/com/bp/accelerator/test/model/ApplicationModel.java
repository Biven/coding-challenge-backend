package com.bp.accelerator.test.model;

import lombok.Data;

import java.util.List;

@Data
public class ApplicationModel {

    private String email;
    private String name;
    private String githubUser;
    private List<PastProjectModel> pastProjects;
}
