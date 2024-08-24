package blogger.platform.controller;

import blogger.platform.exception.InternalServiceException;
import blogger.platform.exception.NotFoundException;
import blogger.platform.model.request.CreateBlogPostRequest;
import blogger.platform.model.request.UpdateBlogPostRequest;
import blogger.platform.model.response.GetBlogPostResponse;
import blogger.platform.service.BloggerService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class BloggerController {
    private final BloggerService productService;

    public BloggerController(BloggerService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public GetBlogPostResponse createBlogPost(@Validated @RequestBody CreateBlogPostRequest request) throws InternalServiceException {
        return productService.createBlogPost(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetBlogPostResponse updateBlogPost(@PathVariable Long id, @Validated @RequestBody UpdateBlogPostRequest request) throws InternalServiceException {
        return productService.updateBlogPost(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlogPost(@PathVariable Long id) throws InternalServiceException {
        productService.deleteBlogPost(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetBlogPostResponse getBlogPost(@PathVariable Long id) throws InternalServiceException, NotFoundException {
        return productService.getBlogPost(id);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<GetBlogPostResponse> getAllBlogPosts(@RequestParam(required = false) String term) throws InternalServiceException, NotFoundException {
        return productService.getAllBlogPosts(term);
    }
}
