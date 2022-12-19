package com.example.Task.controller;

import com.example.Task.dto.StockDTO;
import com.example.Task.service.StockService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping(StockController.STOCK_URL)
public class StockController {
    public static final String STOCK_URL = "/stock";
    public static final Integer ROW_PER_PAGE = 5;

    private final StockService stockService;

    @GetMapping("/findAll")
    public String findAll(Model model,
                          @RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
                          @RequestParam(value = "name", defaultValue = "") String name) {
        List<StockDTO> stockDTOList = stockService.findAll(pageNumber, ROW_PER_PAGE, name);
        Long count = stockService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = ((long) pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("stocks", stockDTOList)
            .addAttribute("hasPrev", hasPrev)
            .addAttribute("prev", pageNumber - 1)
            .addAttribute("hasNext", hasNext)
            .addAttribute("next", pageNumber + 1)
            .addAttribute("name", name);
        return "stock/stockList";
    }

    @GetMapping("/add")
    public String add(Model model) {
        StockDTO stockDTO = new StockDTO();
        model
            .addAttribute("stock", stockDTO)
            .addAttribute("add", true);
        return "stock/stockEdit";
    }

    @PostMapping("/add")
    public String addStock(
        @Valid StockDTO stock,
        BindingResult bindingResult,
        Model model) {
        if (!bindingResult.hasErrors()) {
            stockService.saveStock(stock);
            return "redirect:/stock/findAll";
        }
        Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

        model
            .addAttribute("stock", stock)
            .addAttribute("add", true)
            .addAttribute("errorsMap", errorsMap);
        return "stock/stockEdit";
    }

    @GetMapping(value = "/editForm/{id}")
    public String stockEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("stock", stockService.findById(id))
            .addAttribute("add", false);
        return "stock/stockEdit";
    }

    @PostMapping(value = "/edit/{id}")
    public String updateStock(
        @Valid StockDTO stock,
        BindingResult bindingResult,
        Model model,
        @PathVariable Integer id) {
        StockDTO stockDTO = stockService.findById(id);
        if (!bindingResult.hasErrors()) {
            stockService.updateStock(stock, id);
            return "redirect:/stock/findAll";
        }
        Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
        model.addAttribute("stock", stockDTO)
            .addAttribute("add", false)
            .addAttribute("errorsMap", errorsMap);
        return "stock/stockEdit";
    }

    @PostMapping(value = "/enable/{id}")
    public String enableStockById(@PathVariable Integer id) {
        stockService.enableById(id);
        return "redirect:/stock/findAll";
    }

    @PostMapping(value = "/disable/{id}")
    public String disableStockById(@PathVariable Integer id) {
        stockService.disableById(id);
        return "redirect:/stock/findAll";
    }
}
