package gr.dcu.europeana.arch.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Vangelis Nomikos
 */
@Entity
@Table (name = "subject_mapping")
@Getter
@Setter
@NoArgsConstructor
public class SubjectMapping implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column (name="label")
    private String label;
    
    @Column (name="description")
    private String description;
    
    @Column (name="language")
    private String language;
    
    @Column (name="provider_name")
    private String providerName;
    
    @Column (name="vocabulary_name")
    private String vocabularyName;
    
}
