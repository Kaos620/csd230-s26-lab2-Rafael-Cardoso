package csd230.s26.lab1.controllers;

import csd230.s26.lab1.entities.CartEntity;
import csd230.s26.lab1.entities.MagazineEntity;
import csd230.s26.lab1.pojos.Magazine;
import csd230.s26.lab1.repositories.CartRepository;
import csd230.s26.lab1.repositories.MagazineRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/magazines")
public class MagazineController {
    private final MagazineRepository magazineRepository;
    private final CartRepository cartRepository;

    // Standard Constructor Injection
    public MagazineController(MagazineRepository magazineRepository, CartRepository cartRepository) {
        this.magazineRepository = magazineRepository;
        this.cartRepository = cartRepository;
    }

//    @GetMapping
//    public String getAllBooks(Model model) {
//        model.addAttribute("books", bookRepository.findAll());
//        return "bookList"; // Refers to bookList.html
//    }

    @GetMapping
    public String getAllMagazines(Model model) {
        List<MagazineEntity> magazines = magazineRepository.findAll();
        model.addAttribute("magazines", magazines); // Packing the suitcase
        return "magazineList";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("magazine", new MagazineEntity());
        return "addMagazine";
    }

    @PostMapping("/add")
    public String addMagazine(@ModelAttribute MagazineEntity magazine) {
        magazineRepository.save(magazine);
        return "redirect:/magazines";
    }

    @GetMapping("/delete/{id}")
    public String deleteMagazine(@PathVariable Long id) {
        MagazineEntity magazine = magazineRepository.findById(id).orElse(null);
        if (magazine != null) {
            // STEP 1: Remove magazine from all Many-to-Many Cart relationships
            for (CartEntity cart : magazine.getCarts()) {
                cart.getProducts().remove(magazine);
                cartRepository.save(cart);
            }
            // STEP 2: Now it is safe to delete
            magazineRepository.deleteById(id);
        }
        return "redirect:/magazines";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MagazineEntity magazine = magazineRepository.findById(id).orElse(null);
        model.addAttribute("magazine", magazine); // This object NOW HAS AN ID (e.g., 5)
        return "addMagazine";
    }


//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable Long id, Model model) {
//        // Look for the book by ID
//        BookEntity book = bookRepository.findById(id).orElse(null);
//
//        if (book != null) {
//            model.addAttribute("book", book); // Pack the existing book into the suitcase
//            return "book-form"; // Send them to the exact same form used for adding
//        }
//
//        return "redirect:/books"; // If book not found, go back to list
//    }
}