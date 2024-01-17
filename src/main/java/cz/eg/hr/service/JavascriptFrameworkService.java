package cz.eg.hr.service;

import cz.eg.hr.data.JavascriptFramework;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface JavascriptFrameworkService {

    ResponseEntity<String> addFrameworks(List<@Valid JavascriptFramework> frameworks);
    ResponseEntity<String> updateFramework(String frameworkName,
                                           String newFrameworkName,
                                           Integer version,
                                           Integer newVersion,
                                           LocalDate newEndOfSupport,
                                           Float newRating);
    ResponseEntity<String> delete(String frameworkName,
                                  String target,
                                  Integer version);
}
