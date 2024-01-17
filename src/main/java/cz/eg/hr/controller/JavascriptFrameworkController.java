package cz.eg.hr.controller;

import cz.eg.hr.data.JavascriptFramework;
import cz.eg.hr.repository.FrameworkVersionRepository;
import cz.eg.hr.repository.JavascriptFrameworkRepository;
import cz.eg.hr.rest.Errors;
import cz.eg.hr.service.JavascriptFrameworkService;
import cz.eg.hr.service.TransactionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
public class JavascriptFrameworkController {

    private final JavascriptFrameworkRepository frameworkRepository;
    private final JavascriptFrameworkService frameworkService;

    @Autowired
    public JavascriptFrameworkController(JavascriptFrameworkRepository frameworkRepository,
                                         FrameworkVersionRepository versionRepository,
                                         TransactionalService transactionalService,
                                         JavascriptFrameworkService frameworkService) {
        this.frameworkRepository = frameworkRepository;
        this.frameworkService = frameworkService;
    }

    @Operation(summary = "Print all frameworks",
        description = "Prints a JSON array containing all the information (except for individual version IDs)")
    @ApiResponse(responseCode = "200", description = "Success", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = JavascriptFramework.class))})
    @GetMapping("/frameworks")
    public Iterable<JavascriptFramework> frameworks() {
        return frameworkRepository.findAll();
    }

    @Operation(summary = "Add Frameworks", description = "Endpoint to add multiple JavaScript frameworks.")
    @ApiResponse(responseCode = "200", description = "Success", content = {
        @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "202", description = "Partial Success", content = {
        @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = Errors.class))})
    @PostMapping("/add-frameworks")
    public ResponseEntity<String> addFrameworks(
        @Parameter(description = "List of JavaScript frameworks to be added.",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = JavascriptFramework.class))))
        @RequestBody List<@Valid JavascriptFramework> requestBody) {
        return frameworkService.addFrameworks(requestBody);
    }

    @Operation(summary = "Update Framework Information",
        description = "Endpoint to update details of a Javascript framework such as name, version, rating, EOS.")
    @ApiResponse(responseCode = "200", description = "Success", content = {
        @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
        @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "404", description = "Not Found", content = {
        @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))})
    @PutMapping("/update-framework/{frameworkName}")
    public ResponseEntity<String> updateFramework(
        @Parameter(description = "Name of the Javascript framework to be updated.")
        @PathVariable String frameworkName,
        @Parameter(description = "New name for the Javascript framework.")
        @RequestParam(required = false) String newFrameworkName,
        @Parameter(description = "Version of the Javascript framework to eb updated.")
        @RequestParam(required = false) Integer version,
        @Parameter(description = "New version for the Javascript framework.")
        @RequestParam(required = false) Integer newVersion,
        @Parameter(description = "New endOfSupport date for the Javascript framework.")
        @RequestParam(required = false) LocalDate newEndOfSupport,
        @Parameter(description = "New rating for the Javascript framework.")
        @RequestParam(required = false) Float newRating) {
        return frameworkService.updateFramework(
            frameworkName, newFrameworkName, version, newVersion, newEndOfSupport, newRating);
    }

    @Operation(summary = "Delete Information",
        description = "Endpoint to delete a Javascript framework, its version, or to turn EOS to null.")
    @ApiResponse(responseCode = "200", description = "Success", content = {
        @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))})
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
        @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))})
    @DeleteMapping("/delete/{frameworkName}")
    public ResponseEntity<String> delete(
        @Parameter(description = "Name of the Javascript framework to be deleted, or just part of it.")
        @PathVariable String frameworkName,
        @Parameter(description = "Type of deletion operation: 'framework', 'version', or 'endOfSupport'.")
        @RequestParam String target,
        @Parameter(description = "Version of the Javascript framework to be deleted.")
        @RequestParam(required = false) Integer version) {
        return frameworkService.delete(frameworkName,target,version);
    }
}
