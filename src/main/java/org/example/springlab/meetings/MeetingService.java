package org.example.springlab.meetings;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    public Meeting saveMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    public void deleteMeeting(Long id) {
        meetingRepository.deleteById(id);
    }

    public List<Meeting> getMeetingsByDate(LocalDate date) {
       return meetingRepository.findByDate(date);
    }

    public List<Meeting> getMeetingsByRoomId(Long id) {
        return meetingRepository.findByRoomId(id);
    }

    public List<Meeting> getMeetingsByDateAndRoomId(LocalDate date, Long id) {
        return meetingRepository.findByDateAndRoomId(date, id);
    }
}
