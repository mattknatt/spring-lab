package org.example.springlab.meetings;

import java.time.LocalDate;

public record MeetingDTO(Long id,
                         String name,
                         String description,
                         LocalDate date,
                         Long roomId,
                         Integer maxParticipants) {
}
