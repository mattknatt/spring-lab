package org.example.springlab.meetings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface MeetingRepository extends ListCrudRepository<Meeting, Long>, ListPagingAndSortingRepository<Meeting, Long> {
    List<Meeting> findByDate(LocalDate date);
    List<Meeting> findByRoomId(Long id);
    List<Meeting> findByDateAndRoomId(LocalDate date, Long id);
}
