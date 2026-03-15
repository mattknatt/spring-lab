package org.example.springlab.meetings;

import jakarta.validation.Valid;
import org.example.springlab.meetings.dto.CreateMeetingDTO;
import org.example.springlab.meetings.dto.UpdateMeetingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    // --- LIST (with filtering + pagination) ---
    @GetMapping("/meetings")
    public String viewMeetings(
            Model model,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false)    String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(required = false)    Long room
    ) {
        int safePage = Math.max(0, page);
        int safeSize = (size <= 0) ? 10 : Math.min(size, 100);

        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by("date").ascending());
        Page<Meeting> meetingPage = meetingService.getMeetings(pageable, name, dateFrom, dateTo, room);

        boolean isFiltered = (name != null && !name.isBlank())
                || dateFrom != null
                || dateTo   != null
                || room != null;

        model.addAttribute("meetings",        meetingPage.getContent());
        model.addAttribute("currentPage",     meetingPage.getNumber());
        model.addAttribute("totalPages",      meetingPage.getTotalPages());
        model.addAttribute("totalElements",   meetingPage.getTotalElements());
        model.addAttribute("size",            safeSize);
        model.addAttribute("isFiltered",      isFiltered);

        // Echo filter values back so inputs stay populated after submit
        model.addAttribute("filterName",     name);
        model.addAttribute("filterDateFrom", dateFrom);
        model.addAttribute("filterDateTo",   dateTo);
        model.addAttribute("filterRoom",     room);

        // Populate room dropdown with all distinct room IDs
        model.addAttribute("allRooms", meetingService.getAllRoomIds());

        model.addAttribute("title",   "Meetings");
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
        if (meeting == null) {
            return "redirect:/meetings";
        }

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
        if (meeting == null) {
            return "redirect:/meetings";
        }
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