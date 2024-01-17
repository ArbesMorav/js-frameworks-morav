package cz.eg.hr.service;

import cz.eg.hr.data.FrameworkVersion;
import cz.eg.hr.data.JavascriptFramework;
import cz.eg.hr.repository.FrameworkVersionRepository;
import cz.eg.hr.repository.JavascriptFrameworkRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class JavascriptFrameworkServiceImpl implements JavascriptFrameworkService {

    private final JavascriptFrameworkRepository frameworkRepository;
    private final FrameworkVersionRepository versionRepository;
    private final TransactionalService transactionalService;

    @Autowired
    public JavascriptFrameworkServiceImpl(JavascriptFrameworkRepository frameworkRepository,
                                          FrameworkVersionRepository versionRepository,
                                          TransactionalService transactionalService) {
        this.frameworkRepository = frameworkRepository;
        this.versionRepository = versionRepository;
        this.transactionalService = transactionalService;
    }

    @Override
    public ResponseEntity<String> addFrameworks(List<@Valid JavascriptFramework> frameworks) {
        List<String> duplicateNames = new ArrayList<>();
        for (JavascriptFramework requestBodyItem : frameworks) {
            JavascriptFramework framework = new JavascriptFramework();
            if (frameworkRepository.existsByName(requestBodyItem.getName())) {
                duplicateNames.add(requestBodyItem.getName());
            } else {
                framework.setName(requestBodyItem.getName());
                framework.setRating(requestBodyItem.getRating());

                List<String> endOfSupport = requestBodyItem.getVersions().stream()
                    .map(version -> version.getEndOfSupport() != null ? version.getEndOfSupport().toString() : null)
                    .collect(Collectors.toList());

                List<FrameworkVersion> versions = IntStream.range(0, endOfSupport.size())
                    .mapToObj(i -> {
                        FrameworkVersion version = new FrameworkVersion();
                        version.setVersion(requestBodyItem.getVersions().get(i).getVersion());
                        version.setEndOfSupport(
                            endOfSupport.get(i) != null ? LocalDate.parse(endOfSupport.get(i)) : null
                        );
                        version.setFramework(framework);
                        return version;
                    }).collect(Collectors.toList());

                framework.setVersions(versions);
                frameworkRepository.save(framework);
            }
        }
        // Partial success, so response chosen as ACCEPTED
        if (!duplicateNames.isEmpty()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("The following frameworks already exist and were skipped: " + duplicateNames);
        }
        return ResponseEntity.ok("Framework(s) added successfully");
    }

    @Override
    public ResponseEntity<String> updateFramework(String frameworkName,
                                                  String newFrameworkName,
                                                  Integer version,
                                                  Integer newVersion,
                                                  LocalDate newEndOfSupport,
                                                  Float newRating) {
        Optional<JavascriptFramework> existingFramework = frameworkRepository.findByName(frameworkName);

        if (existingFramework.isPresent()) {
            JavascriptFramework framework = existingFramework.get();

            // Update rating if not adjusting framework name
            if (newRating != null && newFrameworkName == null) {
                framework.setRating(newRating);
                frameworkRepository.save(framework);
                return ResponseEntity.ok("Framework rating updated successfully.");
            }

            // Update framework name if not adjusting rating
            if (newFrameworkName != null && !newFrameworkName.equals(frameworkName) && newRating == null) {
                if (frameworkRepository.existsByName(newFrameworkName)) {
                    // Can't change to name that already exists
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Framework with the new name already exists.");
                }
                framework.setName(newFrameworkName);
                frameworkRepository.save(framework);
                return ResponseEntity.ok("Framework name updated successfully.");
            }

            // Update framework version and/or endOfSupport
            if (version != null) {
                List<FrameworkVersion> versions = framework.getVersions();
                for (FrameworkVersion frameworkVersion : versions) {
                    if (frameworkVersion.getVersion().equals(version)) {
                        if (newVersion != null) {
                            FrameworkVersion existingNewVersion = versionRepository.findByFrameworkIdAndVersion(framework.getId(), newVersion);
                            if (existingNewVersion == null) {
                                frameworkVersion.setVersion(newVersion);
                            } else {
                                return ResponseEntity.badRequest().body("New version is already being used.");
                            }
                            frameworkVersion.setEndOfSupport(newEndOfSupport);
                            versionRepository.save(frameworkVersion);
                            return ResponseEntity.ok("Framework version updated successfully.");
                        }
                        // EndOfSupport value update
                        if (newEndOfSupport != null) {
                            frameworkVersion.setEndOfSupport(newEndOfSupport);
                            versionRepository.save(frameworkVersion);
                            return ResponseEntity.ok("Framework version endOfSupport updated successfully.");
                        }
                        // Can't update version information if ( newEndOfSupport || newVersion ) is missing
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Either newVersion or newEndOfSupport should be provided for version update.");
                    }
                }
                // Framework not found in repository
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Specified framework version not found.");
            }
            // Too many / wrong combinations of parameters
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid combination of parameters for framework update. Limit 1 operation per request.");
        } else {
            // Framework not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Framework with the specified name not found. You can create it using POST /add-frameworks.");
        }
    }

    @Override
    public ResponseEntity<String> delete(String frameworkName, String target, Integer version) {
        Optional<JavascriptFramework> existingFramework = frameworkRepository.findByName(frameworkName);
        // Check the framework exists
        if (existingFramework.isPresent()) {
            JavascriptFramework framework = existingFramework.get();
            if ("framework".equalsIgnoreCase(target)) {
                transactionalService.deleteFrameworkByName(framework.getName());
                return ResponseEntity.ok("Framework and associated versions deleted successfully.");
            } else if ("version".equalsIgnoreCase(target) && version != null) {
                Optional<FrameworkVersion> frameworkVersionToDelete = Optional.ofNullable(versionRepository.findByFrameworkIdAndVersion(framework.getId(), version));
                if (frameworkVersionToDelete.isPresent()) {
                    FrameworkVersion frameworkVersion = frameworkVersionToDelete.get();
                    transactionalService.deleteById(frameworkVersion.getId());
                    return ResponseEntity.ok("Version deleted successfully.");
                } else {
                    return ResponseEntity.badRequest().body("Version not found for the specified framework.");
                }
            } else if ("endOfSupport".equalsIgnoreCase(target) && version != null) {
                FrameworkVersion frameworkVersion = versionRepository.findByFrameworkIdAndVersion(framework.getId(), version);
                if (frameworkVersion != null) {
                    frameworkVersion.setEndOfSupport(null);
                    versionRepository.save(frameworkVersion);
                    return ResponseEntity.ok("EndOfSupport has been set to null for the specified version.");
                } else {
                    return ResponseEntity.badRequest().body("Version not found for the specified framework.");
                }
            } else {
                return ResponseEntity.badRequest().body("Invalid target parameter or version not provided.");
            }
        } else {
            return ResponseEntity.badRequest().body("Framework not found.");
        }
    }
}
