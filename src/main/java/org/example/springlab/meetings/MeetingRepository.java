package org.example.springlab.meetings;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeetingRepository extends ListCrudRepository<Meeting, Long> {
    List<Meeting> findByDate(LocalDate date);
    List<Meeting> findByRoomId(Long roomId);
    List<Meeting> findByDateAndRoomId(LocalDate date, Long roomId);
}
