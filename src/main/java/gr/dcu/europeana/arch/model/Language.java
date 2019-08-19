package gr.dcu.share3d.entity;

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
@Table(name = "languages")
@Getter
@Setter
@NoArgsConstructor
public class Language implements Serializable {
    
    @Column (name="iso639_1")
    private String iso639_1;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="iso639_2")
    private String iso639_2;
    
    private String name;
    
}
