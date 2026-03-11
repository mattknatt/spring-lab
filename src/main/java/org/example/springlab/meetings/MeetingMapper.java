package org.example.springlab.meetings;

public class MeetingMapper {

    public static MeetingDTO toDTO(Meeting meeting) {
        return new MeetingDTO(
                meeting.getId(),
                meeting.getName(),
                meeting.getDescription(),
                meeting.getDate(),
                meeting.getRoomId(),
                meeting.getMaxParticipants()
        );
    }

    public static Meeting fromDTO(CreateMeetingDTO dto) {
        return new Meeting(dto.name(),
                dto.description(),
                dto.date(),
                dto.roomId(),
                dto.maxParticipants());
    }

    public static void updateMeeting(Meeting meeting, UpdateMeetingDTO dto) {
        meeting.setName(dto.name());
        meeting.setDescription(dto.description());
        meeting.setDate(dto.date());
        meeting.setRoomId(dto.roomId());
        meeting.setMaxParticipants(dto.maxParticipants());
    }

}
