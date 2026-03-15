package org.example.springlab.meetings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MeetingRepository extends ListCrudRepository<Meeting, Long> {

    @Query("SELECT DISTINCT m.roomId FROM Meeting m ORDER BY m.roomId")
    List<String> findAllDistinctRoomIds();

    @Query("""
        SELECT m FROM Meeting m
        WHERE (:name IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')))
          AND (:dateFrom IS NULL OR m.date >= :dateFrom)
          AND (:dateTo   IS NULL OR m.date <= :dateTo)
          AND (:room     IS NULL OR m.roomId = :room)
        """)
    Page<Meeting> findAllFiltered(
            @Param("name")     String name,
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo")   LocalDate dateTo,
            @Param("room")     Long room,
            Pageable pageable
    );

}