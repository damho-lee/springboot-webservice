package com.seungjo.book.springboot.web;


import com.seungjo.book.springboot.service.file.FileService;
import com.seungjo.book.springboot.service.posts.PostsService;
import com.seungjo.book.springboot.web.dto.PostsResponseDto;
import com.seungjo.book.springboot.web.dto.PostsSaveRequestDto;
import com.seungjo.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;

@RequiredArgsConstructor
@RestController
@Setter
public class PostsApiController {

    private final PostsService postsService;
    private final FileService fileService;

    @PostMapping("/api/v1/posts")
    public Long save(@ModelAttribute PostsSaveRequestDto requestsDto) throws IOException {
        System.out.println("requestsDto.getTitle() = " + requestsDto.getTitle());
        fileService.storeFiles(requestsDto.getImageFiles());
        System.out.println("postsService = " + postsService.save(requestsDto));

        return postsService.save(requestsDto);
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getFullPath(filename));
    }

    @PutMapping("api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("api/v1/posts/{id}")
    public PostsResponseDto findbyId(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }
}