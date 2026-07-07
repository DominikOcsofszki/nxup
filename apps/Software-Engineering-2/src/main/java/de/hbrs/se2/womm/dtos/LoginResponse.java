package de.hbrs.se2.womm.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    @JsonProperty("authorities")
    private List<AuthorityDTO> authorities;
    @JsonProperty("authenticated")
    private boolean authenticated;
    @JsonProperty("principal")
    private NutzerDTO principal;
}
