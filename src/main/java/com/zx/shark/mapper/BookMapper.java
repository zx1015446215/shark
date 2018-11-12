package com.zx.shark.mapper;

import com.zx.shark.model.Book;
import com.zx.shark.model.MenuDO;
import com.zx.shark.model.UserBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BookMapper{
    List<Book> selectAllBooks();
    void insertBook(Book book);
    void deleteBook(List<Integer> ids);
    void insertUserBook(UserBook userBook);
    void deleteUserBook(UserBook userBook);
    int countBooks();
    List<Integer> selectUserBooks(String username);
    List<Book> selectNeedBook(Book book);
}