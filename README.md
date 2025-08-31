# 📰 News Aggregator API

A Spring Boot REST API that aggregates and serves news articles.  
Users can register, log in, set preferences, and fetch articles from multiple categories.  
The app demonstrates **Spring Boot 3**, **JWT-based authentication**, and **Swagger UI** for API documentation.

---

## ✨ Features
- 🔐 **JWT Authentication** (Register & Login APIs)
- 👤 User management (register, login, preferences)
- 📰 News articles & categories
- 📊 Tracks user reads
- 🛡️ Role-based access control
- 📖 Swagger UI for API documentation
- ⚡ Built with Spring Boot & Spring Security

- ## 📂 Project Structure
src/main/java/com/airtribe/news_aggregator
│── NewsAggregatorApplication.java # Main entry point
│
├── configuration/ # Security configuration
│ ├── AuthConfig.java
│ ├── AuthJwtAuthenticationFilter.java
│
├── constants/ # App-wide constants
│ └── Constants.java
│
├── controller/ # REST controllers
│ └── UserController.java
│
├── dto/ # Data transfer objects
│ ├── GNewsArticleBean.java
│ ├── GNewsResponseBean.java
│ ├── GNewsSourceBean.java
│ ├── ResponseBean.java
│ └── UserCreateBean.java
│
├── entity/ # JPA entities
│ ├── Article.java
│ ├── NewsCategory.java
│ ├── User.java
│ ├── UserPreferences.java
│ └── UserReads.java
│
├── exception_handler/ # Custom exceptions
│ ├── GlobalExceptionHandler.java
│ └── ResourceNotFoundException.java
│
├── repository/ # Spring Data repositories
│ ├── ArticleRepository.java
│ ├── UserPreferencesRepository.java
│ ├── UserReadsRepository.java
│ └── UserRepository.java
│
├── service/ # Business logic
│ └── UserService.java
│
└── utility/ # Utility classes
├── TokenUtil.java
└── WebClientUtil.java

# API Documentation

## [new_aggregation.postman_collection.json](https://github.com/user-attachments/files/22064298/new_aggregation.postman_collection.json)
