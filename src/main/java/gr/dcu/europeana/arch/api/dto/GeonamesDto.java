package gr.dcu.europeana.arch.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Vangelis Nomikos
 */
@Getter
@Setter
@NoArgsConstructor
public class GeonamesDto {
    
    private long geonameId;
    private String name;
    
    private String countryCode;
    private String countryName;
   
    private String latitude;
    private String longitude;
    
    private String label;
    
}
