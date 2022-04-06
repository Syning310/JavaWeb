package controller;

import domain.Book;
import service.BookService;

import javax.servlet.http.HttpSession;
import java.util.List;

public class BookController {
    
    BookService bookService;
    
    
    // 取出图书列表，然后转到 index.html 页面    
    public String index(HttpSession session) {

        List<Book> bookList = bookService.getBookList();

        session.setAttribute("bookList", bookList);

        return "index";

    }
    
    
    
    
}
