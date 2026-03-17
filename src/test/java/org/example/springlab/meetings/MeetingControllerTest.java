package org.example.springlab.meetings;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


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

}