package com.ecust.touhouairline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class SampleController {
    @RequestMapping("/test")
    public ModelMap AddCharacter(HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        printWriter.print("<script language=\"javascript\">alert('success');</script>");
        return new ModelMap("id","1");
    }
}
