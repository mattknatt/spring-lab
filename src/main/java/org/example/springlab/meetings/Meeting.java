package org.example.springlab.meetings;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private LocalDate date;

    @NotNull
    private Long roomId;

    private Integer maxParticipants;

    public Meeting(String name, String description, LocalDate date, Long roomId, Integer maxParticipants) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.roomId = roomId;
        this.maxParticipants = maxParticipants;
    }
}
