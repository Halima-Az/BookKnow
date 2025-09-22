package com.example.test;

import com.example.test.model.Book;
import com.example.test.model.User;
import com.example.test.services.FavoriteService;
import com.example.test.services.UserService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private UserService userService;

    // 🔹 Ajouter un livre aux favoris
    @PostMapping("/add/{type}/{bookId}")
    public String addToFavorites(@PathVariable Long bookId,
            @PathVariable String type
    ) {
        User user = userService.getcurrentuser();
        if (user != null) {
            favoriteService.addBookToFavorites(user.getId(), bookId);
        }
        return "redirect:/books/types/" + type; // retour à la liste
    }

    // 🔹 Lister les favoris
    @GetMapping
    public String showFavorites(Model model) {
        User user = userService.getcurrentuser();
        if (user == null) {
            return "redirect:/login"; // sécurité si pas connecté
        }

        Set<Book> favorites = favoriteService.getFavorites(user.getId());
        model.addAttribute("favorites", favorites);
        model.addAttribute("breadcrumbCurrent", "Favorites");
        return "favorites"; // ton HTML thymeleaf
    }

    @PostMapping("/remove/{id}")
    public String removeFromFavorite(@PathVariable long id) {
        User user = userService.getcurrentuser();
        if (user != null) {
            favoriteService.RemoveBookToFavorites(user.getId(), id);
        }
        return "redirect:/favorites";
    }

    @PostMapping("/toggle/{type}/{bookId}")
    public String toggleFavorite(@PathVariable long bookId,
            @PathVariable String type,
            RedirectAttributes redirectAttributes) {
        User user = userService.getcurrentuser();
        if (user != null) {
           boolean added=  favoriteService.toggleFavorite(user.getId(), bookId);
           if(added){
               redirectAttributes.addFlashAttribute("success","Book added to favorites!");
           }
           else{
               redirectAttributes.addFlashAttribute("remove","Book removed from favorites!");
           }
        }
        return "redirect:/books/types/" + type; // retour à la liste
    }
}

