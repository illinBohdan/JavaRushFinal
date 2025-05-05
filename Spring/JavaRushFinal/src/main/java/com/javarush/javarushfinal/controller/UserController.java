package com.javarush.javarushfinal.controller;

import com.javarush.javarushfinal.entity.Part;
import com.javarush.javarushfinal.entity.PurchaseHistory;
import com.javarush.javarushfinal.entity.User;
import com.javarush.javarushfinal.service.part.PartService;
import com.javarush.javarushfinal.service.purchase_history.PurchaseHistoryService;
import com.javarush.javarushfinal.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final PartService partService;
    private final PurchaseHistoryService purchaseHistoryService;

    public UserController(UserService userService, PartService partService, PurchaseHistoryService purchaseHistoryService) {
        this.userService = userService;
        this.partService = partService;
        this.purchaseHistoryService = purchaseHistoryService;
    }

    @GetMapping("/user/{userName}")
    public String getUserProfile(@PathVariable String userName, Model model) {
        User user = userService.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Користувача не знайдено"));

        model.addAttribute("user", user);

        List<PurchaseHistory> purchaseHistory = purchaseHistoryService.findByUser(user);

       model.addAttribute("purchaseHistory", purchaseHistory);

        return "user-profile";
    }

    @PostMapping("/confirm-order")
    public String confirmOrder(@RequestParam Long partId,
                               @RequestParam int quantity,
                               @RequestParam String name,
                               @RequestParam String phone,
                               @RequestParam String email, Model model) {

        Part part = partService.getPartById(partId);
        User user = userService.getUserFromSpringSecurity();

        partService.reducingQuantityPart(partId, quantity);

        purchaseHistoryService.savePurchase(user, part, quantity);

        model.addAttribute("part", part);
        model.addAttribute("quantity", quantity);
        model.addAttribute("name", name);
        model.addAttribute("phone", phone);
        model.addAttribute("email", email);

        model.addAttribute("user", user);

        return "order-confirmation";
    }

}
