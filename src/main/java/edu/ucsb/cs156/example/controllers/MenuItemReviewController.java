package edu.ucsb.cs156.example.controllers;

import edu.ucsb.cs156.example.entities.MenuItemReview;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.MenuItemReviewRepository;
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

@Api(description = "MenuItemReview")
@RequestMapping("/api/MenuItemReview")
@RestController
@Slf4j
public class MenuItemReviewController extends ApiController {

    @Autowired
    MenuItemReviewRepository menuItemReviewRepository;

    @ApiOperation(value = "List all ucsb menu item reviews")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<MenuItemReview> allMenuItemReviews() {
        Iterable<MenuItemReview> review = menuItemReviewRepository.findAll();
        return review;
    }

    @ApiOperation(value = "Get a single menu item review")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    public MenuItemReview getById(
            @ApiParam("id") @RequestParam Long id) {
        MenuItemReview menuItemReview = menuItemReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MenuItemReview.class, id));

        return menuItemReview;
    }

    @ApiOperation(value = "Create a new menu item review")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public MenuItemReview postMenuItemReview(
        @ApiParam("itemId") @RequestParam Long itemId,
        @ApiParam("reviewerEmail") @RequestParam String reviewerEmail,
        @ApiParam("stars") @RequestParam int stars,
        @ApiParam("dateReviewed (in iso format, e.g. YYYY-mm-ddTHH:MM:SS") @RequestParam("dateReviewed") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateReviewed,
        @ApiParam("comments") @RequestParam String comments
        )
        throws JsonProcessingException {

        MenuItemReview review = new MenuItemReview();
        review.setItemId(itemId);
        review.setReviewerEmail(reviewerEmail);
        review.setStars(stars);
        review.setDateReviewed(dateReviewed);
        review.setComments(comments);

        MenuItemReview savedReview = menuItemReviewRepository.save(review);

        return savedReview;
    }

}