package org.example.springlab.meetings;

import jakarta.validation.Valid;
import org.example.springlab.meetings.dto.CreateMeetingDTO;
import org.example.springlab.meetings.dto.UpdateMeetingDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    // --- LIST ---
    @GetMapping("/meetings")
    public String viewMeetings(Model model) {
        List<Meeting> meetings = meetingService.getAllMeetings();
        model.addAttribute("meetings", meetings);
        model.addAttribute("title", "Meetings");
        model.addAttribute("message", "All meetings");
        return "meetings";
    }

    // --- CREATE FORM ---
    @GetMapping("/meetings/new")
    public String showCreateMeetingForm(Model model) {
        model.addAttribute("meeting", new CreateMeetingDTO("", "", null, null, null));
        model.addAttribute("formAction", "/meetings");
        model.addAttribute("title", "Create meeting");
        return "meeting-form";
    }

    // --- EDIT FORM ---
    @GetMapping("/meetings/update/{id}")
    public String showUpdateMeetingForm(@PathVariable Long id, Model model) {
        Meeting meeting = meetingService.getMeetingById(id);

        UpdateMeetingDTO dto = new UpdateMeetingDTO(
                meeting.getId(),
                meeting.getName(),
                meeting.getDescription(),
                meeting.getDate(),
                meeting.getRoomId(),
                meeting.getMaxParticipants()
        );

        model.addAttribute("meeting", dto);
        model.addAttribute("formAction", "/meetings/update");
        model.addAttribute("title", "Update meeting");
        model.addAttribute("message", "Update meeting");
        return "meeting-form";
    }

    // --- SAVE CREATE ---
    @PostMapping("/meetings")
    public String createMeeting(@Valid @ModelAttribute("meeting") CreateMeetingDTO dto,
                                BindingResult bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Create meeting");
            model.addAttribute("formAction", "/meetings");
            return "meeting-form";
        }

        Meeting meeting = MeetingMapper.fromDTO(dto);
        meetingService.saveMeeting(meeting);
        return "redirect:/meetings";
    }

    // --- SAVE UPDATE ---
    @PostMapping("/meetings/update")
    public String updateMeeting(@Valid @ModelAttribute("meeting") UpdateMeetingDTO dto,
                                BindingResult bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Update meeting");
            model.addAttribute("formAction", "/meetings/update");
            return "meeting-form";
        }

        Meeting meeting = meetingService.getMeetingById(dto.id());
        MeetingMapper.updateMeeting(meeting, dto);
        meetingService.saveMeeting(meeting);
        return "redirect:/meetings";
    }

    // --- DELETE ---
    @PostMapping("/meetings/delete/{id}")
    public String deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return "redirect:/meetings";
    }
}