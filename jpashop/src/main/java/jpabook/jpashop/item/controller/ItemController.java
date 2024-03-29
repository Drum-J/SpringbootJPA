package jpabook.jpashop.item.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());

        return "items/createItemForm";
    }

    @PostMapping("/new")
    public String create(BookForm form) {

        // 여기는 Setter 를 사용했으나 생성자 메서드를 가지고 만드는게 더 깔끔하다!
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        service.save(book);

        return "redirect:/items";
    }

    @GetMapping
    public String list(Model model) {
        List<Item> items = service.findAll();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) service.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItems(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm form) {

        /* 컨트롤러에서 어설프게 엔티티를 만드는 것은 좋지 않다.
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());

        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        */

        service.updateItem(itemId,form.getName(),form.getPrice(),form.getStockQuantity());

        return "redirect:/items";
    }
}
