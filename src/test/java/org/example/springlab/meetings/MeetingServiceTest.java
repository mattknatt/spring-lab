package org.example.springlab.meetings;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeetingServiceTest {

    @Mock
    private MeetingRepository meetingRepository;

    @InjectMocks
    private MeetingService meetingService;
    
    @Test
    void getMeetingById_validId_returnMeeting() {
        Meeting meeting = new Meeting(1L,
                "meeting",
                "description",
                LocalDate.of(2026,1,1),
                12L,
                3);

        when(meetingRepository.findById(1L)).thenReturn(Optional.of(meeting
        ));

        Meeting expected = meetingService.getMeetingById(1L);

        assertThat(expected.getId()).isEqualTo(1L);
        assertThat(expected.getName()).isEqualTo("meeting");
        assertThat(expected.getDescription()).isEqualTo("description");
        assertThat(expected.getDate()).isEqualTo(LocalDate.of(2026, 1, 1));
        assertThat(expected.getRoomId()).isEqualTo(12L);
        assertThat(expected.getMaxParticipants()).isEqualTo(3);

    }

    @Test
    void getMeetingById_invalidId_throwsException() {

        when(meetingRepository.findById(1L)).thenReturn(Optional.empty());


        assertThatThrownBy(() ->meetingService.getMeetingById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Meeting with id: 1 not found");

    }

    @Test
    void saveMeeting_shouldSaveAndReturnMeeting() {
        Meeting meeting = new Meeting();

        when(meetingRepository.save(meeting)).thenReturn(meeting);

        Meeting result = meetingService.saveMeeting(meeting);

        assertThat(result).isEqualTo(meeting);
        verify(meetingRepository).save(meeting);
    }

    @Test
    void deleteMeeting_shouldCallRepository() {
        meetingService.deleteMeeting(1L);

        verify(meetingRepository).deleteById(1L);
    }

    @ParameterizedTest
    @MethodSource("nameNormalizationCases")
    void getMeetings_shouldNormalizeName(String input, String expected) {

        Pageable pageable = PageRequest.of(0, 10);

        when(meetingRepository.findAllFiltered(any(), any(), any(), any(), any()))
                .thenReturn(Page.empty());

        meetingService.getMeetings(pageable, input, null, null, null);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(meetingRepository).findAllFiltered(
                captor.capture(), any(), any(), any(), eq(pageable)
        );

        assertThat(captor.getValue()).isEqualTo(expected);
    }

    static Stream<Arguments> nameNormalizationCases() {
        return Stream.of(
                Arguments.of("  Test  ", "Test"),
                Arguments.of("   ", null),
                Arguments.of(null, null)
        );
    }


}