package de.hbrs.se2.womm.mapper;

import de.hbrs.se2.womm.dtos.TagDto;
import de.hbrs.se2.womm.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    @Mapping(target = "tagText", source = "tagText")
    @Mapping(target = "tagId", source = "tagId")
    Tag tagdtoToTag(TagDto tagDto);
    TagDto tagToTagDTO(Tag tag);

}
