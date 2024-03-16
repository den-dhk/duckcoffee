package duckshop.duckcoffee.api;

import duckshop.duckcoffee.controller.BookForm;
import duckshop.duckcoffee.domain.item.Book;
import duckshop.duckcoffee.domain.item.Item;
import duckshop.duckcoffee.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public BookForm createForm(Model model) {
        return new BookForm();
    }

    @PostMapping("/items/new")
    public void create(BookForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
    }

    @GetMapping("/items")
    public Result list(Model model) {
        List<Item> items = itemService.findItems();
        return new Result(items);
    }

    @GetMapping("items/{itemId}/edit")
    public BookForm updateItemForm(@PathVariable("itemId") Long itemId) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        return form;
    }

    @PostMapping("items/{itemId}/edit")
    public void updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
