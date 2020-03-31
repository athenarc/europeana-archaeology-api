package gr.dcu.europeana.arch.model;

import java.util.List;
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
public class MappingCollection {
    
    private List<SubjectTermEntity> mappings;
    
}
