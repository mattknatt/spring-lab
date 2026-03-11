package org.example.springlab.meetings;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateMeetingDTO(
        String name,
        @Size(min = 3, max = 100) String description,
        @FutureOrPresent LocalDate date,
        Long roomId,
        @Positive Integer maxParticipants
        ) {
}
