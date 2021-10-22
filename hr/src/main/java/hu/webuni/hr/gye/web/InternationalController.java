package hu.webuni.hr.gye.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InternationalController {
	
    @GetMapping("/international")
    public String getInternationalPage() {
        return "redirect:employees";
    }
}
