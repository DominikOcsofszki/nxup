package de.hbrs.se2.womm.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDto {
    private Integer tagId;
    private String tagText;
}
