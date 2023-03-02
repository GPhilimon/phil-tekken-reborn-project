package com.cpan252.tekkenreborn.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cpan252.tekkenreborn.model.dto.FighterSearchByDateDto;
import com.cpan252.tekkenreborn.repository.FighterRepository;
import com.cpan252.tekkenreborn.repository.FighterRepositoryPaginated;

@Controller
@RequestMapping("/fighterList")
public class FighterListController {
    private static final int PAGE_SIZE = 2;

    @Autowired
    private FighterRepository fighterRepository;

    @Autowired
    private FighterRepositoryPaginated fighterRepositoryPaginated;

    @ModelAttribute
    public void fighterSearchByDateDto(Model model) {
        model.addAttribute("fighterSearchByDateDto", new FighterSearchByDateDto());
    }

    @GetMapping
    public String getfighterList() {
        return "fighterList";
    }

    @ModelAttribute
    public void fighters(Model model) {
        var fightersPage = fighterRepositoryPaginated.findAll(PageRequest.of(0, PAGE_SIZE));
        model.addAttribute("totalPages", fightersPage.getTotalPages());
        model.addAttribute("currentPage", fightersPage.getNumber());
        model.addAttribute("fighters", fightersPage);
    }

    @PostMapping
    public String searchFightersByDate(@ModelAttribute FighterSearchByDateDto fightersByDateDto,
            Model model) {
        var dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("fighters", fighterRepository.findByNameStartsWithAndCreatedAtBetween(
                fightersByDateDto.getName(), LocalDate.parse(fightersByDateDto.getStartDate(), dateFormatter),
                LocalDate.parse(fightersByDateDto.getEndDate(), dateFormatter)));
        return "fighterList";
    }

    @GetMapping("/switchPage")
    public String switchPage(Model model,
            @RequestParam("pageToSwitch") Optional<Integer> pageToSwitch) {
        var page = pageToSwitch.orElse(0);
        var totalPages = (int) model.getAttribute("totalPages");
        if (page < 0 || page >= totalPages) {
            return "fighterList";
        }
        var fighterPage = fighterRepositoryPaginated.findAll(PageRequest.of(pageToSwitch.orElse(0),
                PAGE_SIZE));
        model.addAttribute("fighters", fighterPage.getContent());
        model.addAttribute("currentPage", fighterPage.getNumber());
        return "fighterList";
    }
}
