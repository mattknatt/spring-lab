package org.example.springlab.meetings.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UpdateMeetingDTO(
        @NotNull @Positive Long id,
        String name,
        @NotBlank @Size(min = 3, max = 255) String description,
        @FutureOrPresent LocalDate date,
        Long roomId,
        @Positive Integer maxParticipants
        ) {
}
