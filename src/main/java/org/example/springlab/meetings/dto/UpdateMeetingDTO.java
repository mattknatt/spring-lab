package org.example.springlab.meetings.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UpdateMeetingDTO(
        @NotNull @Positive Long id,
        @NotBlank String name,
        @NotBlank @Size(min = 3, max = 255) String description,
        @NotNull @FutureOrPresent LocalDate date,
        @NotNull Long roomId,
        @Positive @Max(100) Integer maxParticipants
) {
}
