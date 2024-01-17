package cz.eg.hr.repository;

import cz.eg.hr.data.FrameworkVersion;
import org.springframework.data.repository.CrudRepository;

public interface FrameworkVersionRepository extends CrudRepository<FrameworkVersion, Long> {
    FrameworkVersion findByFrameworkIdAndVersion(Long id, Integer newVersion);

    void deleteById(Long id);
}
