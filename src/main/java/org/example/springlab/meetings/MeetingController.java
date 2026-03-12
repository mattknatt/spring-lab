package org.example.springlab.meetings;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class MeetingController {

    private final MeetingService meetingService;
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }
    @GetMapping("/meetings")
    public String viewMeetings(Model model) {
        List<Meeting> meetings = meetingService.getAllMeetings();
        model.addAttribute("meetings", meetings);
        model.addAttribute("title", "Meetings");
        model.addAttribute("message", "All meetings");
        return "meetings";
    }


}
