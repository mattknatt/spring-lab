package org.example.springlab.meetings;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateMeetingDTO(
        @NotBlank String name,

        @Size(min = 3, max = 100) @NotBlank String description,

        @FutureOrPresent LocalDate date,

        @NotNull Long roomId,

        @Positive @Max(100) Integer maxParticipants
) {
}
