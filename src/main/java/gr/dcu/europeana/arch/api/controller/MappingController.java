package gr.dcu.europeana.arch.api.controller;

import gr.dcu.europeana.arch.model.SubjectTerm;
import gr.dcu.europeana.arch.model.SpatialTerm;
import gr.dcu.europeana.arch.model.Mapping;
import gr.dcu.europeana.arch.service.AuthService;
import gr.dcu.europeana.arch.service.MappingService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@CrossOrigin
public class MappingController {
    
    private final AuthService authService;
    private final MappingService mappingService;

    public MappingController(AuthService authService, MappingService mappingService) {
        this.authService = authService;
        this.mappingService = mappingService;
    }

    @Operation(summary = "Get all mappings")
    @GetMapping("/mappings/all")
    public List<Mapping> getAllMappings(HttpServletRequest requestContext) {
        
        // int userId = authService.authorize(requestContext);
         
        return mappingService.findAll();
    }

    @Operation(summary = "Get user mappings")
    @GetMapping("/mappings")
    public List<Mapping> getUserMappings(HttpServletRequest requestContext) {
        
        int userId = authService.authorize(requestContext);
         
        return mappingService.findAllByUserId(userId);
    }

    @Operation(summary = "Get an existing mapping")
    @GetMapping("/mappings/{id}")
    public Mapping getMapping(HttpServletRequest requestContext, @PathVariable Long id) { 
        
        // int userId = authService.authorize(requestContext);
                
        return mappingService.findById(id);
    }

    @Operation(summary = "Create a new mapping")
    @PostMapping("/mappings")
    public Mapping saveMapping(HttpServletRequest requestContext, @RequestBody Mapping mapping) { 
        
        int userId = authService.authorize(requestContext);
        
        return mappingService.save(userId, mapping);
    }

    @Operation(summary = "Update an existing mapping")
    @PutMapping("/mappings/{id}")
    public Mapping updateMapping(HttpServletRequest requestContext, 
            @PathVariable Long id, @RequestBody Mapping mapping) {

        return mappingService.updateMapping(id, mapping);
    }

    @Operation(summary = "Delete a mapping")
    @DeleteMapping("/mappings/{id}")
    public void deleteMapping(HttpServletRequest requestContext, @PathVariable Long id) { 
        
        int userId = authService.authorize(requestContext);
        
        mappingService.delete(userId, id);
    }

    @Operation(summary = "Export mapping terms to excel")
    @PostMapping("/mappings/{id}/export")
    public ResponseEntity<Resource> exportMapping(HttpServletRequest requestContext, @PathVariable Long id) throws IOException { 

        int userId = authService.authorize(requestContext);
        
        Resource resource = mappingService.exportTerms(id, userId);
        
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = requestContext.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                // .contentLength(file.length())
                .body(resource);

    }

    @Operation(summary = "Upload subject terms to mapping")
    @PostMapping("/mappings/{id}/upload")
    public List<SubjectTerm> uploadTerms(HttpServletRequest requestContext, @PathVariable Long id, 
            @RequestParam("file") MultipartFile file) throws IOException {
        
        int userId = authService.authorize(requestContext);
        
        return mappingService.uploadSubjectTerms(id, file, userId);
    }

    @Operation(summary = "Upload spatial terms to mapping")
    @PostMapping("/mappings/{id}/upload_spatial")
    public List<SpatialTerm> uploadSpatialTerms(HttpServletRequest requestContext, @PathVariable Long id, 
            @RequestParam("file") MultipartFile file) throws IOException {
        
        int userId = authService.authorize(requestContext);
        
        return mappingService.uploadSpatialTerms(id, file, userId);
    }

    @Operation(summary = "Upload temporal terms to mapping")
    @PostMapping("/mappings/{id}/upload_temporal")
    public List<SpatialTerm> uploadTemporalTerms(HttpServletRequest requestContext, @PathVariable Long id, 
            @RequestParam("file") MultipartFile file) throws IOException {
        
        int userId = authService.authorize(requestContext);
        
        return mappingService.uploadSpatialTerms(id, file, userId);
    }
}
