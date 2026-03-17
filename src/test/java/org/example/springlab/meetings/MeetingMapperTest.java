package org.example.springlab.meetings;

import org.example.springlab.meetings.dto.CreateMeetingDTO;
import org.example.springlab.meetings.dto.MeetingDTO;
import org.example.springlab.meetings.dto.UpdateMeetingDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MeetingMapperTest {

    
    @Test
    void shouldMapMeetingToMeetingDto() {

        Meeting meeting = new Meeting(
                1L,
                "name",
                "description",
                LocalDate.of(2026,1,1),
                1L,
                1);

        MeetingDTO meetingDTO = MeetingMapper.toDTO(meeting);

        assertThat(meetingDTO.id()).isEqualTo(meeting.getId());
        assertThat(meetingDTO.name()).isEqualTo(meeting.getName());
        assertThat(meetingDTO.description()).isEqualTo(meeting.getDescription());
        assertThat(meetingDTO.date()).isEqualTo(meeting.getDate());
        assertThat(meetingDTO.roomId()).isEqualTo(meeting.getRoomId());
        assertThat(meetingDTO.maxParticipants()).isEqualTo(meeting.getMaxParticipants());
    }
    
    @Test
    void shouldMapCreateMeetingDTOtoMeeting() {

        CreateMeetingDTO dto = new CreateMeetingDTO(
                "name",
                "description",
                LocalDate.of(2026,1,1),
                1L,
                1);

        Meeting meeting = MeetingMapper.fromDTO(dto);

        assertThat(meeting.getName()).isEqualTo(dto.name());
        assertThat(meeting.getDescription()).isEqualTo(dto.description());
        assertThat(meeting.getDate()).isEqualTo(dto.date());
        assertThat(meeting.getRoomId()).isEqualTo(dto.roomId());
        assertThat(meeting.getMaxParticipants()).isEqualTo(dto.maxParticipants());
        
    }
    
    @Test
    void shouldUpdateMeeting_fromUpdateDTO() {
        Meeting meeting = new Meeting(
                1L,
                "name",
                "description",
                LocalDate.of(2026, 1,1),
                1L,
                1
        );

        Long originalId = meeting.getId();

        UpdateMeetingDTO dto = new UpdateMeetingDTO(
                123L,
                "new name",
                "new description",
                LocalDate.of(2026, 1,2),
                2L,
                2);
        MeetingMapper.updateMeeting(meeting, dto);

        assertThat(meeting.getName()).isEqualTo(dto.name());
        assertThat(meeting.getDescription()).isEqualTo(dto.description());
        assertThat(meeting.getDate()).isEqualTo(dto.date());
        assertThat(meeting.getRoomId()).isEqualTo(dto.roomId());
        assertThat(meeting.getMaxParticipants()).isEqualTo(dto.maxParticipants());
        assertThat(meeting.getId()).isEqualTo(originalId);
        assertThat(meeting.getId()).isNotEqualTo(dto.id());
        
    }
}