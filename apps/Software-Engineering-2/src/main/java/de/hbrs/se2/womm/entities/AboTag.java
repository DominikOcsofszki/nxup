package de.hbrs.se2.womm.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "abo_tag")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AboTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "abo_id")
    private Integer aboId;

    @Column(name = "abo_benachrichtigung")
    private Boolean aboBenachrichtigung;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}

