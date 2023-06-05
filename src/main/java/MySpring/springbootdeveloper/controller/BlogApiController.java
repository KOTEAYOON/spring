package MySpring.springbootdeveloper.controller;

import MySpring.springbootdeveloper.domain.Article;
import MySpring.springbootdeveloper.dto.AddArticleRequest;
import MySpring.springbootdeveloper.dto.ArticleResponse;
import MySpring.springbootdeveloper.dto.UpdateArticleRequest;
import MySpring.springbootdeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // RESPONSE BODY의 객체 데이터를 JSON 형태로 반환하는 애너테이션

public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) { //AddArticleRequest의 데이터를 받아와서 객체로 변환해줌
        Article saveArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED) // 상태코드를 201 created로 설정하는 코드
                .body(saveArticle);
    }


    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{id}")

    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);
        return ResponseEntity.ok().body(updatedArticle);
    }

}
