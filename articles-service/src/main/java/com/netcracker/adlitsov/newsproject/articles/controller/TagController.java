package com.netcracker.adlitsov.newsproject.articles.controller;

import com.netcracker.adlitsov.newsproject.articles.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.articles.model.Tag;
import com.netcracker.adlitsov.newsproject.articles.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    TagsRepository tagsRepository;

    @GetMapping()
    public List<Tag> getAllCategories() {
        return tagsRepository.findAll();
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable("id") Integer id) {
        return tagsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));
    }

    @PostMapping()
    public Tag createTag(@Valid @RequestBody Tag tag) {
        return tagsRepository.save(tag);
    }

    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable(value = "id") Integer tagId,
                         @Valid @RequestBody Tag tagDetails) {

        Tag tag = tagsRepository.findById(tagId)
                                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

        tag.setName(tagDetails.getName());

        Tag updatedTag = tagsRepository.save(tag);
        return updatedTag;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable(value = "id") Integer tagId) {
        Tag tag = tagsRepository.findById(tagId)
                                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

        tagsRepository.delete(tag);

        return ResponseEntity.ok().build();
    }
}
