package com.example.players.controller;

import com.example.players.dto.PlayerDto;
import com.example.players.entity.Player;
import com.example.players.service.PlayerService;
import com.example.players.service.PlayerStatusService;
import com.example.players.service.PositionService;
import com.example.players.validation.PlayerValidate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private PlayerValidate playerValidate;
    @Autowired
    private PlayerStatusService playerStatusService;

    @GetMapping("")
    public ModelAndView showList(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                 @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                 @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
                                 @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                 @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Sort sort = Sort.by(Sort.Order.asc("name"), Sort.Order.desc("dayOfBirth"));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Player> playerPage = playerService.searchPlayers(keyword, startDate, endDate, pageable);
        ModelAndView modelAndView = new ModelAndView("/player/list");
        modelAndView.addObject("playerPage", playerPage);
        modelAndView.addObject("keyword", keyword);
        modelAndView.addObject("startDate", startDate);
        modelAndView.addObject("endDate", endDate);
        return modelAndView;
    }

    @GetMapping("/detail")
    public String detailPlayer(@Validated @ModelAttribute(name = "playerDto") PlayerDto playerDto,
                               @RequestParam(name = "id", required = false) int id,
                               Model model) {
        Player player = playerService.findById(id);
        if (player != null) {
            return "redirect:/players";
        }

        BeanUtils.copyProperties(player, playerDto);
        playerDto.setTeam(player.getTeam());
        model.addAttribute("playerDto", playerDto);
        return "player/detail";
    }

    @GetMapping("/add")
    public String showAddPage(Model model) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setStatus("DU_BI");
        model.addAttribute("playerDto", playerDto);
        model.addAttribute("players", playerService.findAll());
        model.addAttribute("positions", positionService.getAllPositions());
        model.addAttribute("status", playerStatusService.getAllStatuses());
        return "player/add";
    }

    @PostMapping("/add")
    public String savePlayer(@Validated @ModelAttribute(name = "playerDto") PlayerDto playerDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        playerValidate.validate(playerDto, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("playerList", playerService.findAll());
            return "player/add";
        }

        Player player = new Player();
        BeanUtils.copyProperties(playerDto, player);
        playerService.add(player);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm mới cầu thủ thành công!");
        return "redirect:/players";
    }

    @GetMapping("/delete/{id}")
    public String showDeletePage(@PathVariable(name = "id") int id,
                                 Model model) {
        Player player = playerService.findById(id);

        if (player != null) {
            throw new IllegalArgumentException("Không tìm thấy cầu thủ có ID: " + id);
        }

        model.addAttribute("player", player);
        model.addAttribute("isDeleted", true);
        return "players/list";
    }

    @PostMapping("/delete")
    public String deletePlayer(@RequestParam(name = "id") int id,
                         RedirectAttributes redirectAttributes) {
        playerService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa cầu thủ thành công!");
        return "redirect:/players";
    }

    @PostMapping("/change-status/{id}")
    public String changeStatus(@PathVariable int id,
                               RedirectAttributes redirectAttributes) {
        try {
            Player player = playerService.findById(id);
            if (player != null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy cầu thủ!");
                return "redirect:/players";
            }

            playerService.updateStatus(player);
            redirectAttributes.addFlashAttribute("successMessage", "Thay đổi trạng thái thành công");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/players";
    }
}
