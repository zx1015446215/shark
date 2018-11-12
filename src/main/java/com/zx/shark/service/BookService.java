package com.zx.shark.service;

import com.zx.shark.model.Book;
import java.util.List;

public interface BookService {
    List<Book> findAllBook();
    void removeBook(List<Integer> ids);
    void borrowBook(Long book_id);
    void cancelBorrow(Long book_id);
    List<Book> selectNeedBook(Book book);
    int countBooks();

}
