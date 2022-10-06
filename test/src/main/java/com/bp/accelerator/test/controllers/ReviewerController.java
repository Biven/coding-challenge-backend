package com.bp.accelerator.test.controllers;

import com.bp.accelerator.test.data.entity.Application;
import com.bp.accelerator.test.data.repository.ApplicationRepository;
import com.bp.accelerator.test.service.PdfGenerationService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/reviewer")
@RestController
public class ReviewerController {

    private final ApplicationRepository applicationRepository;
    private final PdfGenerationService pdfGenerationService;

    public ReviewerController(ApplicationRepository applicationRepository,
                              PdfGenerationService pdfGenerationService) {
        this.applicationRepository = applicationRepository;
        this.pdfGenerationService = pdfGenerationService;
    }

    @GetMapping("/applications")
    public List<Application> fetchApplications() {
        return applicationRepository.findAll();
    }

    @GetMapping("/applications/{email}")
    public CompletableFuture<ResponseEntity<ByteArrayResource>> downloadApplicationPdf(@PathVariable String email) {
        Application application = applicationRepository.getReferenceById(email);

        return CompletableFuture.supplyAsync(() -> {
            try {
                ByteArrayResource resource = pdfGenerationService.generatePdf(application);

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + application.getName() + ".pdf");
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");


                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(resource.contentLength())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        });
    }
}
