package com.airtribe.news_aggregator.service;

import com.airtribe.news_aggregator.constants.Constants;
import com.airtribe.news_aggregator.dto.GNewsArticleBean;
import com.airtribe.news_aggregator.dto.GNewsResponseBean;
import com.airtribe.news_aggregator.dto.UserCreateBean;
import com.airtribe.news_aggregator.entity.Article;
import com.airtribe.news_aggregator.entity.NewsCategory;
import com.airtribe.news_aggregator.entity.User;
import com.airtribe.news_aggregator.entity.UserPreferences;
import com.airtribe.news_aggregator.exception_handler.ResourceNotFoundException;
import com.airtribe.news_aggregator.repository.ArticleRepository;
import com.airtribe.news_aggregator.repository.UserRepository;
import com.airtribe.news_aggregator.utility.TokenUtil;
import com.airtribe.news_aggregator.utility.WebClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private WebClientUtil webClientUtil;

    @Value("${spring.gnews.api.url}")
    private String gNewsURL;

    @Value("${spring.gnews.api-key}")
    private String gNewsAPIKey;

    /**
     * This function is used to register a user into our project so that user can read articles
     * If the user already exist and active in database then throw error to avoid duplicate users.
     *
     * @return User: will return the saved user.
     * @param: UserCreateBean with necessary details like username, email id and password.
     */
    public User registerUser(UserCreateBean userCreateBean) {
        Optional<User> user = userRepository.findByEmailIdAndActiveTrue(userCreateBean.getEmailId());
        if (user.isPresent()) {
            LOGGER.error("User {} already registered", userCreateBean.getUserName());
            return user.get();
        }

        User newUser = new User();
        newUser.setUserName(userCreateBean.getUserName());
        newUser.setEmailId(userCreateBean.getEmailId());
        newUser.setRole("USER");
        newUser.setPassword(passwordEncoder.encode(userCreateBean.getPassword()));
        newUser.setActive(Constants.TRUE);
        return userRepository.save(newUser);
    }

    /**
     * This function is used to check the login,
     * If user was existed and active then return JWT token.
     *
     * @param: Takes email id and password to validate the user.
     * @return: JWT token.
     */
    public String login(String emailId, String password) {
        Optional<User> user = userRepository.findByEmailIdAndActiveTrue(emailId);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return TokenUtil.generateToken(user.get());
        }

        LOGGER.error("User {} not found", emailId);
        throw new ResourceNotFoundException("user not found");
    }

    /**
     * This function is used to get news preferences for the logging user.
     *
     * @return user preferences list
     * @param: fetched email id from the JWT token.
     */
    public List<UserPreferences> getUserPreferences() {
        String emailId = TokenUtil.getEmailIdFromToken();
        System.out.println(emailId);
        Optional<User> user = userRepository.findByEmailIdAndActiveTrue(emailId);
        if (user.isPresent()) {
            if (Objects.nonNull(user.get().getUserPreferences()) && !user.get().getUserPreferences().isEmpty()) {
                System.out.println(user.get().getUserPreferences());
                return user.get().getUserPreferences();
            }
            LOGGER.error("No user preferences found for the user {}", emailId);
            throw new ResourceNotFoundException("No preferences found user the user " + emailId);
        }

        LOGGER.error("User {} not found", emailId);
        throw new ResourceNotFoundException("user not found");
    }

    /**
     * This function is used to update the preferences for the logging user.
     *
     * @return user complete profile with updated preferences.
     * @param: category, source and language as input.
     */
    public User updateUserPreferences(NewsCategory category, String source, String language) {
        String emailId = TokenUtil.getEmailIdFromToken();
        Optional<User> user = userRepository.findByEmailIdAndActiveTrue(emailId);
        if (user.isEmpty()) {
            LOGGER.error("User {} not found", emailId);
            throw new ResourceNotFoundException("user not found");
        }

        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setCategory(category);
        userPreferences.setSource(source);
        userPreferences.setLanguage(language);
        userPreferences.setActive(Constants.TRUE);
        userPreferences.setUser(user.get());

        List<UserPreferences> updatedUserPreferences = user.get().getUserPreferences();
        updatedUserPreferences.add(userPreferences);
        user.get().setUserPreferences(updatedUserPreferences);
        return userRepository.save(user.get());
    }

    /**
     * This function is used to fetch all articles of logging user preferences.
     *
     * @return list of articles which are of logging user preferences.
     */
    public List<Article> fetchNews() {
        String emailId = TokenUtil.getEmailIdFromToken();
        Optional<User> user = userRepository.findByEmailIdAndActiveTrue(emailId);
        if (user.isEmpty()) {
            LOGGER.error("User {} not found", emailId);
            throw new ResourceNotFoundException("user not found");
        }

        if (user.get().getUserPreferences().isEmpty()) {
            throw new ResourceNotFoundException("No resources found for the user");
        }
        List<NewsCategory> newsCategories = user.get().getUserPreferences().stream()
                .filter(UserPreferences::isActive)
                .map(UserPreferences::getCategory).toList();

        List<Article> articles = new ArrayList<>();
        for (NewsCategory category : newsCategories) {
            String url = UriComponentsBuilder.fromUriString(gNewsURL)
                    .queryParam("q", category)
                    .queryParam("apikey", gNewsAPIKey)
                    .toUriString();
            GNewsResponseBean response = webClientUtil.get(url, GNewsResponseBean.class).block();
            if (Objects.nonNull(response) && Objects.nonNull(response.getArticles())) {
                articles.addAll(this.mapGNewsResponseToArticle(response.getArticles()));
            }
        }
        return articles;
    }

    private List<Article> mapGNewsResponseToArticle(List<GNewsArticleBean> responseBean) {
        List<Article> articles = new ArrayList<>();
        for (GNewsArticleBean responseArticle: responseBean) {
            Article article = new Article();
            article.setTitle(responseArticle.getTitle());
            article.setDescription(responseArticle.getDescription());
            article.setContent(responseArticle.getContent());
            article.setCategory(responseArticle.getSource().getName());
            article.setLanguage(responseArticle.getLang());
            article.setUrl(responseArticle.getUrl());
            article.setPublishedDate(responseArticle.getPublishedAt());
            articles.add(article);
        }
        return articles;
    }
}
