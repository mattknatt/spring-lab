package org.example.springlab.meetings;

import org.springframework.data.repository.ListCrudRepository;

public interface MeetingRepository extends ListCrudRepository<Meeting, Long> {
}
