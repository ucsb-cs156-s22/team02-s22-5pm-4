package edu.ucsb.cs156.example.controllers;

import edu.ucsb.cs156.example.entities.Article;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.ArticleRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.time.LocalDateTime;


@Api(description = "Article")
@RequestMapping("/api/article")
@RestController
@Slf4j
public class ArticleController extends ApiController {

    @Autowired
    ArticleRepository articleRepo;

    @ApiOperation(value = "List all articles")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<Article> allArticles() {
        Iterable<Article> all_articles = articleRepo.findAll();
        return all_articles;
    }

    // @ApiOperation(value = "Get a single article")
    // @PreAuthorize("hasRole('ROLE_USER')")
    // @GetMapping("")
    // public Article getById(
    //         @ApiParam("id") @RequestParam Long id) {
    //     Article single_article = articleRepo.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException(Article.class, id));

    //     return single_article;
    // }

    @ApiOperation(value = "Create a new article")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public Article postArticle(
            @ApiParam("title (title of the article)") @RequestParam String title,
            @ApiParam("url (url of the article)") @RequestParam String url,
            @ApiParam("explanation (a brief explanation of the article)") @RequestParam String explanation,
            @ApiParam("email (of the person who submitted the Article") @RequestParam String email,
            @ApiParam("dateAdded (in iso format, e.g. YYYY-mm-ddTHH:MM:SS; see https://en.wikipedia.org/wiki/ISO_8601)") @RequestParam("dateAdded") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateAdded)
            throws JsonProcessingException {

        // For an explanation of @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        // See: https://www.baeldung.com/spring-date-parameters

        log.info("dateAdded={}", dateAdded);

        Article new_article = new Article();
        new_article.setTitle(title);
        new_article.setUrl(url);
        new_article.setExplanation(explanation);
        new_article.setEmail(email);
        new_article.setDateAdded(dateAdded);

        Article savedArticle = articleRepo.save(new_article);

        return savedArticle;
    }

    // @ApiOperation(value = "Delete an Article")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @DeleteMapping("")
    // public Object deleteArticle(
    //         @ApiParam("id") @RequestParam Long id) {
    //     Article art = articleRepo.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException(Article.class, id));

    //     ArticleRepository.delete(art);
    //     return genericMessage("Article with id %s deleted".formatted(id));
    // }

    // @ApiOperation(value = "Update a single Article")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @PutMapping("")
    // public Article updateArticle(
    //         @ApiParam("id") @RequestParam Long id,
    //         @RequestBody @Valid Article incoming) {

    //     Article art = articleRepo.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException(Article.class, id));

    //     art.setTitle(incoming.getTitle());
    //     art.setURL(incoming.getURL());
    //     art.setExplanation(incoming.getExplanation());
    //     art.setEmail(incoming.getEmail());
    //     art.setLocalDateTime(incoming.getLocalDateTime());

    //     articleRepo.save(art);

    //     return art;
    // }
}
