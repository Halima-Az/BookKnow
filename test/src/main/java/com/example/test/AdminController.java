package com.example.test;

import com.example.test.dto.BookDTO;
import com.example.test.model.Book;
import com.example.test.services.BookService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;

    @GetMapping("/addbook")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new BookDTO());
        return "addbook";
    }

    @PostMapping("/books")
    public String ajouterLivre(BookDTO bookDTO) throws IOException {

        Book book = new Book();
        book.setNom(bookDTO.getNom());
        book.setWriter(bookDTO.getWriter());
        book.setDate(bookDTO.getDate());
        book.setType(bookDTO.getType());
        book.setLanguage(bookDTO.getLanguage());

        if (bookDTO.getBookPdf() != null && !bookDTO.getBookPdf().isEmpty()) {
            // Chemin absolu sûr pour les fichiers
            String uploadDir = "C:/Users/ADmiN/Documents/books/uploads"; // créer ce dossier sur ton PC
            Files.createDirectories(Paths.get(uploadDir));

            // Nom unique pour éviter les collisions
            String filename = UUID.randomUUID() + "_" + bookDTO.getBookPdf().getOriginalFilename();
            Path filePath = Paths.get(uploadDir, filename);

            // Transfert du fichier
            bookDTO.getBookPdf().transferTo(filePath.toFile());

            // Stocke seulement le nom du fichier en base
            book.setBookPdf(filename);
        }
        if (bookDTO.getImage() != null && !bookDTO.getImage().isEmpty()) {
            String uploadDirImage = "C:/Users/ADmiN/Documents/books/images";
            Files.createDirectories(Paths.get(uploadDirImage));

            String imageName = UUID.randomUUID() + "_" + bookDTO.getImage().getOriginalFilename();
            Path imagePath = Paths.get(uploadDirImage, imageName);
            bookDTO.getImage().transferTo(imagePath.toFile());

            book.setImage(imageName);
        }
        bookService.saveBook(book);
        return "redirect:/home";
    }
}
