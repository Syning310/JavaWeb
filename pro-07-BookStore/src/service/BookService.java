package service;

import dao.BookDAO;
import domain.Book;

import java.util.List;

public class BookService {
    
    private BookDAO bookDAO;
    
    // 1、获取所有图书
    public List<Book> getBookList() {
        String sql = "select id, bookImg, bookName, price, author, saleCount, bookCount, status from book";
        return bookDAO.queryMutil(sql, Book.class);
    }
    
    // 2、通过图书的 id，获取图书的完整信息(一条记录)
    public Book getBookByBookId(Integer bookId) {
        String sql = "select id, bookImg, bookName, price, author, saleCount, bookCount, status from book where id=?";
        return bookDAO.querySingle(sql, Book.class, bookId);
    }
    
    
    
    
    
    
}
