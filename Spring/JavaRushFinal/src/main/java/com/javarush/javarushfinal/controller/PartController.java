package com.javarush.javarushfinal.controller;

import com.javarush.javarushfinal.entity.Part;
import com.javarush.javarushfinal.entity.User;
import com.javarush.javarushfinal.service.part.PartService;
import com.javarush.javarushfinal.service.user.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class PartController {
    private final PartService partService;
    private final UserService userService;
    private List<String> uniqueAutoList;

    public PartController(PartService partService, UserService userService) {
        this.partService = partService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        uniqueAutoList = partService.getUniqueValues("auto");
    }


    @GetMapping("/")
    public String viewAllParts(@RequestParam(value = "language", required = false) String language, Model model) {
        model.addAttribute("language", language);
        model.addAttribute("allPartsList", partService.getAllParts());
        model.addAttribute("uniqueAuto", uniqueAutoList);
        model.addAttribute("selectedBrand", null);
        return "all-parts";
    }

    @GetMapping("/{brand}")
    public String viewPartsByBrand(@PathVariable String brand, Model model) {
        model.addAttribute("filterParts", partService.getAllPartsByAuto(brand));
        model.addAttribute("uniqueAuto", partService.getUniqueValues("auto"));
        model.addAttribute("selectedBrand", brand);
        return "all-parts";
    }

    @GetMapping("/show-image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Part part = partService.getPartById(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(part.getImage());
    }

    @GetMapping("/detail-part/{id}")
    public String viewPartDetails(@PathVariable Long id, Model model) {
        model.addAttribute("part", partService.getPartById(id));

        return "part-details";
    }

    @GetMapping("/buy/{partId}")
    public String buyPart(@PathVariable Long partId, Model model) {
        model.addAttribute("part", partService.getPartById(partId));

        User user = userService.getUserFromSpringSecurity();

        model.addAttribute("user", user);

        return "buy-page";
    }


}
