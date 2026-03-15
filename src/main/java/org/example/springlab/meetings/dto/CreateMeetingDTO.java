package org.example.springlab.meetings.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateMeetingDTO(
        @NotBlank String name,
        @Size(min = 3, max = 255) @NotBlank String description,
        @NotNull @FutureOrPresent LocalDate date,
        @NotNull Long roomId,
        @Positive @Max(100) Integer maxParticipants
) {
}
