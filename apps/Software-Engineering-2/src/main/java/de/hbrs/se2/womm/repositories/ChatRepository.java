package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findChatByChatId(long ChatID);

    Chat findChatByStudent_StudentIdAndUnternehmen_UnternehmenId(long studentID, long unternehmenID);

    List<Chat> findChatByStudent_StudentId(long studentID);

    List<Chat> findChatByUnternehmen_UnternehmenId(long unternehmenID);

}
