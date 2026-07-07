package de.hbrs.se2.womm.dtos;

import com.vaadin.flow.component.html.Image;
import de.hbrs.se2.womm.views.extra.ASSETS;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class UnternehmenDTO implements AbstractDTO {
    private Long unternehmenId;
    private String name;
    private String beschreibung;
    private String gruendung;
    private String website_url;
    private NutzerDTO nutzer;
    public Image PlaceholderOrImage(){
        return ASSETS.buildPlaceholder(50);
    }
}
