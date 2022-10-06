package com.bp.accelerator.test.data.repository;

import com.bp.accelerator.test.data.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, String> {
}
