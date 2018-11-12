package com.zx.shark.service.impl;

import com.zx.shark.mapper.BookMapper;
import com.zx.shark.model.Book;
import com.zx.shark.model.User;
import com.zx.shark.model.UserBook;
import com.zx.shark.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookMapper bookMapper;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 获取所以书籍和对应用户借阅信息
     * @return
     */
    @Transactional
    @Override
    public List<Book> findAllBook() {
        //从SecurityContextHolder中获取用户名
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String username = String.valueOf(authentication.getPrincipal());

        List<Book> books = bookMapper.selectAllBooks();
        List<Integer> listid =bookMapper.selectUserBooks(username);
        for(Book book:books){
            if(listid.contains(book.getId())){
                book.setFlag(true);
            }
        }
        return books;
    }

    @Override
    public void removeBook(List<Integer> ids) {

        bookMapper.deleteBook(ids);
    }

    /**
     * 借书的操作
     * @param book_id
     */
    @Transactional
    @Override
    public void borrowBook(Long book_id){
        Long user_id;
        //从SecurityContextHolder中获取用户名
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String username = String.valueOf(authentication.getPrincipal());
        //从redis缓存中查找用户信息
        ValueOperations<String,User> operations=redisTemplate.opsForValue();
        boolean haskey= redisTemplate.hasKey(username);
        //若缓存中存在
        if(haskey){
            User user=operations.get(username);
            user_id = user.getId();
        }else {
            //根据用户名从数据库中获取用户的id
            User user = userService.findUserByUsername(username);
            user_id = user.getId();
        }
        //将book_id和user_id添加如数据库表book_user中，并在book中将remain减少1
        UserBook userBook = new UserBook(user_id,book_id);

        bookMapper.insertUserBook(userBook);
    }

    /**
     * 取消借阅书籍
     * @param book_id
     */
    @Transactional
    @Override
    public void cancelBorrow(Long book_id) {
        Long user_id;
        //从SecurityContextHolder中获取用户名
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String username = String.valueOf(authentication.getPrincipal());
        //从redis缓存中查找用户信息
        ValueOperations<String,User> operations=redisTemplate.opsForValue();
        boolean haskey= redisTemplate.hasKey(username);
        //若缓存中存在
        if(haskey){
            User user=operations.get(username);
            user_id = user.getId();
        }else {
            //根据用户名从数据库中获取用户的id
            User user = userService.findUserByUsername(username);
            user_id = user.getId();
        }
        UserBook userBook = new UserBook(user_id,book_id);
        bookMapper.deleteUserBook(userBook);
    }

    /**
     * 条件查询书籍
     * @param book
     * @return
     */
    @Transactional
    @Override
    public List<Book> selectNeedBook(Book book) {
        //从SecurityContextHolder中获取用户名
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String username = String.valueOf(authentication.getPrincipal());
        List<Book> books = bookMapper.selectNeedBook(book);
        List<Integer> listid = bookMapper.selectUserBooks(username);
        for(Book book1:books){
            if(listid.contains(book1.getId())){
                book1.setFlag(true);
            }
        }
        return books;
    }

    @Override
    public int countBooks() {
        return bookMapper.countBooks();
    }

}
