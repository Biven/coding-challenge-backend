package com.bp.accelerator.test.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Period;
import java.util.Objects;

@Entity
@Table(name = "past_project")
@Getter
@Setter
public class PastProject {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "project_name")
    private String projectName;

    @Enumerated(EnumType.STRING)
    private Employment employment;

    @Enumerated(EnumType.STRING)
    private Capacity capacity;

    @Column
    private Period duration;

    @Column(name = "start_year")
    private int startYear;

    @Column
    private String role;

    @Column(name = "team_size")
    private int teamSize;

    @Column(name = "repo_link")
    private String repoLink;

    @Column(name = "live_url")
    private String liveUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PastProject that = (PastProject) o;

        if (startYear != that.startYear) return false;
        if (teamSize != that.teamSize) return false;
        if (!Objects.equals(projectName, that.projectName)) return false;
        if (employment != that.employment) return false;
        if (capacity != that.capacity) return false;
        if (!Objects.equals(duration, that.duration)) return false;
        if (!Objects.equals(role, that.role)) return false;
        if (!Objects.equals(repoLink, that.repoLink)) return false;
        return Objects.equals(liveUrl, that.liveUrl);
    }

    @Override
    public int hashCode() {
        int result = projectName != null ? projectName.hashCode() : 0;
        result = 31 * result + (employment != null ? employment.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + startYear;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + teamSize;
        result = 31 * result + (repoLink != null ? repoLink.hashCode() : 0);
        result = 31 * result + (liveUrl != null ? liveUrl.hashCode() : 0);
        return result;
    }
}
