package org.example.springlab.meetings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }


    public Meeting getMeetingById(Long id) {
        return meetingRepository.findById(id).orElse(null);
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
        // Normalise blank strings to null so JPQL :param IS NULL checks work
        String normName = (name == null) ? null : name.trim();
        if(normName != null && normName.isBlank())
            normName = null;
        return meetingRepository.findAllFiltered(normName, dateFrom, dateTo, room, pageable);
    }

    public List<Long> getAllRoomIds() {
        return meetingRepository.findAllDistinctRoomIds();
    }
}