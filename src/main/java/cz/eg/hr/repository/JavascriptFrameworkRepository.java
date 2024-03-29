package cz.eg.hr.repository;

import cz.eg.hr.data.JavascriptFramework;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JavascriptFrameworkRepository extends CrudRepository<JavascriptFramework, Long> {

    boolean existsByName(String name);

    Optional<JavascriptFramework> findByName(String frameworkName);

    void deleteByName(String framework);
}
