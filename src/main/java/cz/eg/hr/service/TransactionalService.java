package cz.eg.hr.service;

import cz.eg.hr.repository.FrameworkVersionRepository;
import cz.eg.hr.repository.JavascriptFrameworkRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionalService {

    private final JavascriptFrameworkRepository frameworkRepository;
    private final FrameworkVersionRepository versionRepository;

    @Autowired
    public TransactionalService(JavascriptFrameworkRepository frameworkRepository, FrameworkVersionRepository versionRepository) {
        this.frameworkRepository = frameworkRepository;
        this.versionRepository = versionRepository;
    }
    @Transactional
    public void deleteFrameworkByName(String name) {
        frameworkRepository.deleteByName(name);
    }
    @Transactional
    public void deleteById(Long id) {
        versionRepository.deleteById(id);
    }
}
