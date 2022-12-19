package com.example.Task.controller;

import com.example.Task.dto.UserDto;
import com.example.Task.dto.UserEditRequestDTO;
import com.example.Task.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import static com.example.Task.controller.StockController.ROW_PER_PAGE;

@Controller
@RequiredArgsConstructor
@RequestMapping(UserController.USER_URL)
public class UserController {
    public static final String USER_URL = "/user";

    private final UserService userService;

    @GetMapping("/findAll")
    public String findAll(Model model,
                          @RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
                          @RequestParam(value = "keyword", defaultValue = "")
                              String keyword) {
        List<UserDto> userDtoList = userService.findAll(pageNumber, ROW_PER_PAGE, keyword);

        Long count = userService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = ((long) pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("users", userDtoList)
            .addAttribute("hasPrev", hasPrev)
            .addAttribute("prev", pageNumber - 1)
            .addAttribute("hasNext", hasNext)
            .addAttribute("next", pageNumber + 1)
            .addAttribute("keyword", keyword);
        return "user/userList";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto)
            .addAttribute("add", true);
        return "user/userEdit";
    }

    @PostMapping("/registration")
    public String addUser(
        @Valid UserDto user,
        BindingResult bindingResult,
        Model model) {

        if (!bindingResult.hasErrors()) {
            userService.saveUser(user);
            return "redirect:/auth/login";
        }
        Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

        model.addAttribute("user", user)
            .addAttribute("add", true)
            .addAttribute("errorsMap", errorsMap);
        return "user/userEdit";
    }


    @GetMapping(value = "/editForm/{id}")
    public String userEditForm(@PathVariable Integer id, Model model) {
        model
            .addAttribute("user", userService.findById(id))
            .addAttribute("add", false);
        return "user/userEdit";
    }

    @PostMapping(value = "/edit/{id}")
    public String updateStock(
        @PathVariable Integer id,
        @Valid UserEditRequestDTO user,
        BindingResult bindingResult,
        Model model) {

        if (!bindingResult.hasErrors()) {
            userService.updateUser(user, id);
            return "redirect:/user/findAll";
        }
        Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

        model.addAttribute("user", userService.findById(id))
            .addAttribute("add", false)
            .addAttribute("errorsMap", errorsMap);
        return "user/userEdit";
    }
}
