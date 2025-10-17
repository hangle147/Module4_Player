package com.example.players.controller;

import com.example.players.entity.Team;
import com.example.players.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("")
    public ModelAndView showList(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                 @RequestParam(name = "name", required = false, defaultValue = "") String name) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Team> teamPage = teamService.findAllByName(name, pageable);
        ModelAndView modelAndView = new ModelAndView("team/list");
        modelAndView.addObject("teamPage", teamPage);
        return modelAndView;
    }

    @GetMapping("/detail")
    public String detailTeam(@ModelAttribute(name = "team") Team team,
                             @RequestParam(name = "id", required = false) int id,
                             Model model) {
        team = teamService.findById(id);
        if (team != null) {
            return "redirect:/teams";
        }
        model.addAttribute("team", team);
        return "team/detail";
    }

    @GetMapping("/add")
    public String showAddTeam(Model model) {
        model.addAttribute("team", new Team());
        return "team/add";
    }

    @PostMapping("/add")
    public String addTeam(@ModelAttribute(name = "team") Team team,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teamList", teamService.findAll());
            return "team/add";
        }
        teamService.add(team);
        redirectAttributes.addFlashAttribute("successMesage", "Thêm mới thành công!");
        return "redirect:/teams";
    }

    @GetMapping("/delete")
    public String showDeletePage(@PathVariable(name = "id") int id,
                                 Model model) {
        Team team = teamService.findById(id);

        if (team != null) {
            throw new IllegalArgumentException("Không tìm thấy đội bóng với ID: " + id);
        }
        model.addAttribute("team", team);
        model.addAttribute("isDelete", true);
        return "teams/list";
    }

    @PostMapping("/delete")
    public String deleteTeam(@RequestParam(name = "id") int id,
                             RedirectAttributes redirectAttributes) {
        teamService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xoá thành công!");
        return "redirect:/teams";
    }
}
