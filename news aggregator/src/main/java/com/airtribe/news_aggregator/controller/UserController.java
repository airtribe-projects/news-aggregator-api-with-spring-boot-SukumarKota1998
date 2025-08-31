package com.airtribe.news_aggregator.controller;

import com.airtribe.news_aggregator.dto.ResponseBean;
import com.airtribe.news_aggregator.dto.UserCreateBean;
import com.airtribe.news_aggregator.entity.Article;
import com.airtribe.news_aggregator.entity.NewsCategory;
import com.airtribe.news_aggregator.entity.User;
import com.airtribe.news_aggregator.entity.UserPreferences;
import com.airtribe.news_aggregator.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseBean> registerUser(@Valid @RequestBody UserCreateBean userBean) {
        User user = userService.registerUser(userBean);
        return new ResponseEntity<ResponseBean>(
                new ResponseBean("User Created successfully", user), HttpStatus.OK);
    }

    @PostMapping("/logins")
    public ResponseEntity<ResponseBean> login(@RequestParam String emailId,
                                              @RequestParam String password) {
        String token = userService.login(emailId, password);
        return new ResponseEntity<ResponseBean>(
                new ResponseBean("Logged successfully", token), HttpStatus.OK);
    }

    @GetMapping("/preferences")
    public ResponseEntity<ResponseBean> getUserPreferences() {
        List<UserPreferences> userPreferences = userService.getUserPreferences();
        return new ResponseEntity<ResponseBean>(
                new ResponseBean("Got user preferences successfully", userPreferences), HttpStatus.OK);
    }

    @PutMapping("/preferences")
    public ResponseEntity<ResponseBean> updateUserPreferences(@RequestParam NewsCategory newsCategory,
                                                              @RequestParam String source,
                                                              @RequestParam String language) {
        User userPreferences = userService.updateUserPreferences(newsCategory, source, language);
        return new ResponseEntity<ResponseBean>(
                new ResponseBean("posted user preferences successfully", userPreferences), HttpStatus.OK);
    }

    @GetMapping("/news")
    @PreAuthorize("hasRole('ROLE_ROLE_USER')")
    public ResponseEntity<ResponseBean> fetchNews() {
        List<Article> articles = userService.fetchNews();
        return new ResponseEntity<ResponseBean>(
                new ResponseBean("Got news", articles), HttpStatus.OK);
    }
}
