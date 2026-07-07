package de.hbrs.se2.womm.dtos;

import com.vaadin.flow.component.html.Image;
import de.hbrs.se2.womm.views.extra.ASSETS;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDTO implements AbstractDTO {
    private NutzerDTO nutzer;
    private Long studentId;
    private String studentVorname;
    private String studentName;
    private String studentGeburtstag;
    private boolean studentBenachrichtigung;
    private String studentBio;
    private String studentSpezialisierung;
    private Integer studentSemester;

    public Image PlaceholderOrImage(){
        if(nutzer == null || nutzer.getNutzerProfilbild() == null) {
            return ASSETS.buildPlaceholder(50,50);
        }
        return new Image(
                new String(nutzer.getNutzerProfilbild()),
                "getImage"
        );
    }
}
