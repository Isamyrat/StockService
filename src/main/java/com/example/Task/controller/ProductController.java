package com.example.Task.controller;

import com.example.Task.dto.ProductDTO;
import com.example.Task.entity.ProductPictureEntity;
import com.example.Task.service.ProductPictureService;
import com.example.Task.service.ProductService;
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
@RequestMapping(ProductController.PRODUCT_URL)
@RequiredArgsConstructor
public class ProductController {
    public static final String PRODUCT_URL = "/product";

    private final ProductService productService;
    private final ProductPictureService productPictureService;

    @GetMapping("/findAll/{stockId}")
    public String findAllByStockId(Model model,
                                   @RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
                                   @PathVariable Integer stockId,
                                   @RequestParam(value = "productData", defaultValue = "") String productData) {
        List<ProductDTO> productDTOList =
            productService.findAllByProductData(pageNumber, ROW_PER_PAGE, productData, stockId);

        Long count = productService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = ((long) pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("products", productDTOList)
            .addAttribute("hasPrev", hasPrev)
            .addAttribute("prev", pageNumber - 1)
            .addAttribute("hasNext", hasNext)
            .addAttribute("next", pageNumber + 1)
            .addAttribute("stockId", stockId)
            .addAttribute("productData", productData);
        return "product/productList";
    }

    @GetMapping("/add/{stockId}")
    public String add(Model model, @PathVariable Integer stockId) {
        ProductDTO productDTO = new ProductDTO();
        model.addAttribute("product", productDTO)
            .addAttribute("add", true)
            .addAttribute("stockId", stockId);
        return "product/productEdit";
    }

    @PostMapping("/add/{stockId}")
    public String addProduct(
        @Valid ProductDTO product,
        BindingResult bindingResult,
        Model model,
        @PathVariable Integer stockId) {
        if (!bindingResult.hasErrors()) {
            productService.saveProduct(product, stockId);
            return "redirect:/product/findAll/" + stockId;
        }
        Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

        model.addAttribute("product", product)
            .addAttribute("add", true)
            .addAttribute("stockId", stockId)
            .addAttribute("errorsMap", errorsMap);
        return "product/productEdit";
    }

    @GetMapping(value = "/editForm/{id}")
    public String productEditForm(@PathVariable String id, Model model) {
        ProductDTO productDTO = productService.findById(id);
        model.addAttribute("product", productDTO)
            .addAttribute("add", false)
            .addAttribute("stockId", productDTO.getStockId());
        return "product/productEdit";
    }

    @PostMapping(value = "/edit/{id}")
    public String updateProduct(
        @Valid ProductDTO product,
        BindingResult bindingResult,
        Model model,
        @PathVariable String id) {
        ProductDTO productDTO = productService.findById(id);
        if (!bindingResult.hasErrors()) {
            Integer stockId = productService.updateProduct(product, id);
            return "redirect:/product/findAll/" + stockId;
        }
        Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
        model.addAttribute("product", productDTO)
            .addAttribute("add", false)
            .addAttribute("stockId", productDTO.getStockId())
            .addAttribute("errorsMap", errorsMap);
        return "product/productEdit";
    }

    @PostMapping(value = "/delete/{id}/{stockId}")
    public String deleteById(@PathVariable String id, @PathVariable Integer stockId) {
        productService.deleteById(id);
        return "redirect:/product/findAll/" + stockId;
    }

    @GetMapping(value = "/getById/{id}")
    public String getById(@PathVariable String id, Model model) {
        ProductDTO productDTO = productService.findById(id);
        List<ProductPictureEntity> productPictureEntityList = productDTO.getProductPictureEntities();
        int size = 0;
        if (productDTO.getProductPictureEntities() != null) {
            size = productPictureEntityList.size();
        }
        model.addAttribute("product", productDTO)
            .addAttribute("stockId", productDTO.getStockId())
            .addAttribute("pictures", productPictureEntityList)
            .addAttribute("size", size);
        return "product/product";
    }

    @GetMapping(value = "/addPicture/{id}")
    public String productAddPictureForm(@PathVariable String id, Model model) {
        ProductDTO productDTO = productService.findById(id);
        model
            .addAttribute("stockId", productDTO.getStockId())
            .addAttribute("id", id);
        return "product/productAddPicture";
    }

    @PostMapping(value = "/addPicture/{id}")
    public String addPicture(@PathVariable String id, @RequestParam("pictureLink") String pictureLink) {
        String productId = productPictureService.saveAccountImage(id, pictureLink);
        return "redirect:/product/getById/" + productId;
    }
}
