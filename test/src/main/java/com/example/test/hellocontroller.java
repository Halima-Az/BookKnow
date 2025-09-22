package com.example.test;

import com.example.test.model.Book;
import com.example.test.model.User;       // importe ta classe User
import com.example.test.services.UserService; // importe ton service
import com.example.test.services.BookService;
import com.example.test.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;

@Controller
public class hellocontroller {

    @Autowired
    private UserService userService;
    private final BookService bookService;
    @Autowired
    private FavoriteService favoriteService;

    public hellocontroller(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "hello"; // correspond à hello.html dans templates
    }

    @GetMapping("/home")
    public String home(Authentication auth, Model model) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }
        User user = userService.getcurrentuser();
        String nom = user.getNom();
        String prenom = user.getPrenom();
        nom = nom.substring(0, 1).toUpperCase() + nom.substring(1).toLowerCase();
        prenom = prenom.substring(0, 1).toUpperCase() + prenom.substring(1).toLowerCase();
        model.addAttribute("nom", nom);
        model.addAttribute("prenom", prenom);
        return "home"; // nom de ta page HTML (home.html)
    }

    @GetMapping("/login")
    public String signin() {
        return "login"; // correspond à login.html
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User()); // ⚡ correction addAttribute + majuscule
        return "register"; // correspond à register.html
    }

    @PostMapping("/users")

    public String saveUser(@Valid @ModelAttribute("user") User user,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "register"; // renvoie vers le formulaire avec erreurs
        }
        if (userService.existsByUsername(user)) {
            bindingResult.rejectValue("username", "error.user", "This email is already registered");
            return "register";
        }
        userService.saveUser(user);
        //model.addAttribute("message", "User registered successfully!");
        redirectAttributes.addFlashAttribute("message", "User registered successfully!");
        return "redirect:/login";
    }

    @GetMapping("/books/types/{type}")
    public String getBooksByType(@PathVariable String type, Model model) {
        List<Book> books = bookService.getBookByType(type);
        model.addAttribute("books", books);
        if ("Non_Fiction".equals(type)) {
            type = "Non Fiction";
        } else if ("Self_help".equals(type)) {
            type = "Self Help";
        } else if ("Art_design".equals(type)) {
            type = "Art & Design";
        }

        model.addAttribute("type", type);
        if (books.isEmpty()) {
            model.addAttribute("message", "There is no book of type : " + type);
        }

        // ✅ Ajouter la liste des favoris si user connecté
        User user = userService.getcurrentuser();
        Set<Book> favorites = (user != null) ? favoriteService.getFavorites(user.getId()) : Set.of();
        model.addAttribute("favorites", favorites);

        //pour les liens au dessous d'entete
        model.addAttribute("breadcrumbCurrent", type);

        return "booksByType"; // Vue Thymeleaf

    }

    @GetMapping("/books/pdf/{id}")
    public ResponseEntity<Resource> getBookPdf(@PathVariable Long id) throws IOException {
        Book book = bookService.getBookById(id);

        // Répertoire absolu où sont stockés les fichiers
        String uploadDir = "C:/Users/ADmiN/Documents/books/uploads";

        Path path = Paths.get(uploadDir).resolve(book.getBookPdf()).normalize();
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + path.getFileName().toString() + "\"")
                .body(resource);
    }

}
