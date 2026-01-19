package com.nb.portfolio.cms.controllers;

import com.nb.portfolio.cms.dtos.ContentDTO;
import com.nb.portfolio.cms.exceptions.BadRequestException;
import com.nb.portfolio.cms.services.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContentController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ContentController.class);
    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @PostMapping("content")
    @ResponseStatus(HttpStatus.CREATED)
    public ContentDTO postContent(@RequestBody ContentDTO contentDTO) {
        if (contentDTO.title() == null || contentDTO.title().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (contentDTO.slug() == null || contentDTO.slug().isBlank()) {
            throw new IllegalArgumentException("Slug is required");
        }
        if (!contentDTO.slug().matches("^[a-z0-9-]+$")) {
            throw new IllegalArgumentException("Slug must contain only lowercase letters, numbers, and hyphens");
        }

        return contentService.save(contentDTO);
    }

    @PutMapping("content/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContentDTO putContent(@RequestBody ContentDTO contentDTO, @PathVariable String id) {
        if (contentDTO.id() != null && !contentDTO.id().equals(id)) {
            throw new BadRequestException("Path id and body id must match");
        }

        contentDTO.validate();

        return contentService.update(id, contentDTO);
    }

    @GetMapping("content")
    public List<ContentDTO> getContent() {
        return contentService.findAll();
    }

    @GetMapping("content/{id}")
    public ResponseEntity<ContentDTO> getContentById(@PathVariable String id) {
        ContentDTO ContentDTO = contentService.findOne(id);
        if (ContentDTO == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(ContentDTO);
    }
    //
    // @GetMapping("persons/{ids}")
    // public List<PersonDTO> getPersons(@PathVariable String ids) {
    // List<String> listIds = List.of(ids.split(","));
    // return personService.findAll(listIds);
    // }
    //
    // @GetMapping("persons/count")
    // public Long getCount() {
    // return personService.count();
    // }
    //
    // @DeleteMapping("person/{id}")
    // public Long deletePerson(@PathVariable String id) {
    // return personService.delete(id);
    // }
    //
    // @DeleteMapping("persons/{ids}")
    // public Long deletePersons(@PathVariable String ids) {
    // List<String> listIds = List.of(ids.split(","));
    // return personService.delete(listIds);
    // }
    //
    // @DeleteMapping("persons")
    // public Long deletePersons() {
    // return personService.deleteAll();
    // }
    //

    //
    // @PutMapping("persons")
    // public Long putPerson(@RequestBody List<PersonDTO> personEntities) {
    // return personService.update(personEntities);
    // }
    //
    // @GetMapping("persons/averageAge")
    // public Double averageAge() {
    // return personService.getAverageAge();
    // }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final Exception handleValidationException(IllegalArgumentException e) {
        LOGGER.warn("Validation error: {}", e.getMessage());
        return e;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
