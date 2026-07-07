package de.hbrs.se2.womm.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class NutzerDTO {
    private long nutzerId;
    private String nutzerMail;
    private Boolean nutzerAktiv;
    private String nutzerOrt;
    private byte[] nutzerProfilbild;
}