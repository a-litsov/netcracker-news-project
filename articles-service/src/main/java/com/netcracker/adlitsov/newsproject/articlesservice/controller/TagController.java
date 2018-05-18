package com.netcracker.adlitsov.newsproject.articlesservice.controller;

import com.netcracker.adlitsov.newsproject.articlesservice.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.articlesservice.model.Tag;
import com.netcracker.adlitsov.newsproject.articlesservice.model.Tag;
import com.netcracker.adlitsov.newsproject.articlesservice.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    TagRepository tagRepository;

    @GetMapping()
    public List<Tag> getAllCategories() {
        return tagRepository.findAll();
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable("id") Integer id) {
        return tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));
    }

    @PostMapping()
    public Tag createTag(@Valid @RequestBody Tag tag) {
        return tagRepository.save(tag);
    }

    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable(value = "id") Integer tagId,
                         @Valid @RequestBody Tag tagDetails) {

        Tag tag = tagRepository.findById(tagId)
                               .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

        tag.setName(tagDetails.getName());

        Tag updatedTag = tagRepository.save(tag);
        return updatedTag;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable(value = "id") Integer tagId) {
        Tag tag = tagRepository.findById(tagId)
                               .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

        tagRepository.delete(tag);

        return ResponseEntity.ok().build();
    }
}
