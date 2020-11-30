package gr.dcu.europeana.arch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Settings {

    private String mailgunDomainName;
    private String mailgunApiKey;
    private String mailgunSender;
}
