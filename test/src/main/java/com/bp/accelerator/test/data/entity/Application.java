package com.bp.accelerator.test.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "application")
@Getter
@Setter
public class Application {

    @Id
    @Column
    private String email;

    @Column
    private String name;

    @Column(name = "github_user")
    private String githubUser;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PastProject> pastProjects = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        if (!Objects.equals(email, that.email)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(githubUser, that.githubUser)) return false;
        return Objects.equals(pastProjects, that.pastProjects);
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (githubUser != null ? githubUser.hashCode() : 0);
        result = 31 * result + (pastProjects != null ? pastProjects.hashCode() : 0);
        return result;
    }
}
