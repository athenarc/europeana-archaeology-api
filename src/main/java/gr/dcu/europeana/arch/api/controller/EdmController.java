package gr.dcu.europeana.arch.api.controller;

import gr.dcu.europeana.arch.api.resource.ExtractTermResult;
import gr.dcu.europeana.arch.model.EdmArchive;
import gr.dcu.europeana.arch.model.mappers.SpatialTermMapper;
import gr.dcu.europeana.arch.model.mappers.SubjectTermMapper;
import gr.dcu.europeana.arch.model.mappers.TemporalTermMapper;
import gr.dcu.europeana.arch.service.AuthService;
import gr.dcu.europeana.arch.service.EDMService;
import gr.dcu.europeana.arch.service.edm.EdmExtractUtils;
import gr.dcu.europeana.arch.service.edm.EdmFileTermExtractionResult;
import gr.dcu.europeana.arch.service.edm.ElementExtractionData;
import gr.dcu.europeana.arch.service.edm.ElementExtractionDataCategories;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Vangelis Nomikos
 */
@Slf4j
@CrossOrigin
@RestController
public class EdmController {
    
    @Autowired
    AuthService authService;
    
    @Autowired
    EDMService edmService;
    
    @Autowired
    SubjectTermMapper subjectTermMapper;
    
    @Autowired
    SpatialTermMapper spatialTermMapper;
    
    @Autowired
    TemporalTermMapper temporalTermMapper;
    
    
    @GetMapping("/edm_archives")
    public List<EdmArchive> getEdmArchives(HttpServletRequest requestContext) {
        
        int userId = authService.authorize(requestContext);
         
        return edmService.getEdmArchives(userId);
    }
    
    @GetMapping("/edm_archives/{id}")
    public EdmArchive getEdmArchive(HttpServletRequest requestContext,  @PathVariable Long id) {
        
        int userId = authService.authorize(requestContext);
         
        return edmService.getEdmArchive(id);
    }
    
    
    @PostMapping("/edm_archives/upload")
    public EdmArchive uploadEdmArchive(HttpServletRequest requestContext,
            @RequestParam("file") MultipartFile file) throws IOException {
        
        int userId = authService.authorize(requestContext);
        
        return edmService.uploadEdmArchive(file, userId);
        
    }
    
    @PostMapping("/edm_archives/{id}/extract_terms")
    public ExtractTermResult extractTermsFromEdmArchive(
            HttpServletRequest requestContext, @PathVariable Long id) {
        
        int userId = authService.authorize(requestContext);
        
        ExtractTermResult extractTermResult = new ExtractTermResult();
        
        List<EdmFileTermExtractionResult> extractionResult = edmService.extractTermsFromEdmArcive(id);
        
        // Create seperate categories(thematic, spatial, temporal)
        ElementExtractionDataCategories extractionCategories = 
                EdmExtractUtils.splitExtractionDataInCategories(extractionResult);
        
        // Get thematic terms
        Set<ElementExtractionData> thematicElementValues = extractionCategories.getThematicElementValues();
        if(!thematicElementValues.isEmpty()) {
            extractTermResult.setSubjectTerms(subjectTermMapper.toSubjectTermList(thematicElementValues));
        } else {
            log.warn("No thematic terms to extract.");
        }
        
        Set<ElementExtractionData> spatialElementValues = extractionCategories.getSpatialElementValues();
        if(!spatialElementValues.isEmpty()) {
            extractTermResult.setSpatialTerms(spatialTermMapper.toSpatialTermList(spatialElementValues));
        } else {
            log.warn("No spatial terms to extract.");
        }

        Set<ElementExtractionData> temporalElementValues = extractionCategories.getTemporalElementValues();
        if(!temporalElementValues.isEmpty()) {
            extractTermResult.setTemporalTerms(temporalTermMapper.toTemporalTermList(temporalElementValues));
        } else {
            log.warn("No temporal terms to extract.");
        }
        
        return extractTermResult;
        // return edmService.extractTermsFromEdmArcive(id);
        
        // return new ResponseEntity<>("", HttpStatus.OK);
    }
    
}
