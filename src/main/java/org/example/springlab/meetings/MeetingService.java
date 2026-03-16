package org.example.springlab.meetings;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }


    public Meeting getMeetingById(Long id) {
        return meetingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Meeting with id: " + id + " not found"));
    }

    public Meeting saveMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    public void deleteMeeting(Long id) {
        meetingRepository.deleteById(id);
    }

    public Page<Meeting> getMeetings(Pageable pageable,
                                     String name,
                                     LocalDate dateFrom,
                                     LocalDate dateTo,
                                     Long room) {
        String normName = normalizeName(name);
        return meetingRepository.findAllFiltered(normName, dateFrom, dateTo, room, pageable);
    }


    public List<Long> getAllRoomIds() {
        return meetingRepository.findAllDistinctRoomIds();
    }

    // Normalize strings so that blank strings are treated as null
    private static @Nullable String normalizeName(String name) {
        return Optional.ofNullable(name)
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .orElse(null);
    }
}