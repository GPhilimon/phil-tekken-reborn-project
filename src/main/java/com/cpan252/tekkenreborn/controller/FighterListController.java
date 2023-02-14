package com.cpan252.tekkenreborn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cpan252.tekkenreborn.repository.FighterRepository;

@Controller
@RequestMapping("/fighterList")
public class FighterListController {

    @Autowired
    private FighterRepository fighterRepository;

    @GetMapping
    public String getfighterList() {
        return "fighterList";
    }

    @ModelAttribute
    public void getAllFighters(Model model) {
        model.addAttribute("fighters", fighterRepository.findAll());
    }
}
