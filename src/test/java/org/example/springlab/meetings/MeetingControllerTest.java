package org.example.springlab.meetings;

import org.example.springlab.meetings.dto.UpdateMeetingDTO;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;


@WebMvcTest(MeetingController.class)
class MeetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MeetingService meetingService;

    @Test
    void meetings_displays_allMeetings() throws Exception {

        when(meetingService.getMeetings(any(), any(), any(), any(), any()))
                .thenReturn(Page.empty());

        List<Long> rooms = List.of(1L,2L);
        when(meetingService.getAllRoomIds()).thenReturn(rooms);

        mockMvc.perform(get("/meetings"))
                .andExpect(status().isOk())
                .andExpect(view().name("meetings"))
                .andExpect(model().attributeExists("meetings"))
                .andExpect(model().attribute("allRooms", rooms))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"));
        
    }

    @Test
    void showCreateMeetingForm_shouldReturnForm() throws Exception {

        mockMvc.perform(get("/meetings/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("meeting-form"))
                .andExpect(model().attributeExists("meeting"))
                .andExpect(model().attribute("formAction", "/meetings"));
    }

    @Test
    void showUpdateMeetingForm_shouldMapMeetingToDTO_clean() throws Exception {

        Meeting meeting = new Meeting(
                1L,
                "Test",
                "Test meeting",
                LocalDate.of(2026, 5, 10),
                12L,
                5
        );

        when(meetingService.getMeetingById(1L)).thenReturn(meeting);

        var result = mockMvc.perform(get("/meetings/update/1"))
                .andExpect(status().isOk())
                .andReturn();

        UpdateMeetingDTO dto = (UpdateMeetingDTO)
                Objects.requireNonNull(result.getModelAndView())
                        .getModel().get("meeting");

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.name()).isEqualTo("Test");
        assertThat(dto.description()).isEqualTo("Test meeting");
        assertThat(dto.date()).isEqualTo(LocalDate.of(2026, 5, 10));
        assertThat(dto.roomId()).isEqualTo(12L);
        assertThat(dto.maxParticipants()).isEqualTo(5);
    }

    @Test
    void showUpdateMeetingForm_notFound_shouldReturn404() throws Exception {

        when(meetingService.getMeetingById(1L))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/meetings/update/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
    }

    @Test
    void createMeeting_shouldMapAndSaveCorrectly() throws Exception {

        mockMvc.perform(post("/meetings")
                        .param("name", "Test")
                        .param("description", "Test meeting")
                        .param("date", "2026-05-10")
                        .param("roomId", "12")
                        .param("maxParticipants", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/meetings"));

        ArgumentCaptor<Meeting> captor = ArgumentCaptor.forClass(Meeting.class);

        verify(meetingService).saveMeeting(captor.capture());

        Meeting saved = captor.getValue();

        assertThat(saved.getName()).isEqualTo("Test");
        assertThat(saved.getDescription()).isEqualTo("Test meeting");
        assertThat(saved.getDate()).isEqualTo(LocalDate.of(2026, 5, 10));
        assertThat(saved.getRoomId()).isEqualTo(12L);
        assertThat(saved.getMaxParticipants()).isEqualTo(5);
    }

    
    @Test
    void createMeeting_invalidInput_returnsForm() throws Exception {
        mockMvc.perform(post("/meetings")
                .param("name", "")
                .param("description", "")
                .param("date", "")
                .param("roomId", "")
                .param("maxParticipants", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("meeting-form"))
                .andExpect(model().attributeExists("title"))
                .andExpect(model().attributeExists("formAction"));

                verify(meetingService, never()).saveMeeting(any());
        
    }

    @Test
    void updateMeeting_validInput_shouldUpdateAndRedirect() throws Exception {

        Meeting existingMeeting = new Meeting(
                1L,
                "Old",
                "Old desc",
                LocalDate.of(2026, 5, 1),
                10L,
                2
        );

        when(meetingService.getMeetingById(1L)).thenReturn(existingMeeting);

        mockMvc.perform(post("/meetings/update")
                        .param("id", "1")
                        .param("name", "Updated")
                        .param("description", "Updated desc")
                        .param("date", "2026-05-10")
                        .param("roomId", "12")
                        .param("maxParticipants", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/meetings"));

        verify(meetingService).getMeetingById(1L);
        verify(meetingService).saveMeeting(existingMeeting);
    }

    @Test
    void updateMeeting_invalidInput_returnsForm() throws Exception {
        mockMvc.perform(post("/meetings/update")
                        .param("name", "")
                        .param("description", "")
                        .param("date", "")
                        .param("roomId", "")
                        .param("maxParticipants", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("meeting-form"))
                .andExpect(model().attributeExists("title"))
                .andExpect(model().attributeExists("formAction"));

        verify(meetingService, never()).saveMeeting(any());

    }

    @Test
    void deleteMeeting_deletesMeeting_andRedirectsToMeetings() throws Exception {

        mockMvc.perform(post("/meetings/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/meetings"));

        verify(meetingService).deleteMeeting(1L);
    }
}