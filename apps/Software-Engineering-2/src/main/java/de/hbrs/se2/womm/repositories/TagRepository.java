package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findTagByTagId(long tagID);

    List<Tag> findTagByTagTextContaining(String tagText);
}
