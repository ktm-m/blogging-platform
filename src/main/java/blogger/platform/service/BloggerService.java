package blogger.platform.service;

import blogger.platform.model.request.CreateBlogPostRequest;
import blogger.platform.model.request.UpdateBlogPostRequest;
import blogger.platform.model.response.GetBlogPostResponse;

import java.util.List;

public interface BloggerService {
    GetBlogPostResponse createBlogPost(CreateBlogPostRequest request);

    GetBlogPostResponse updateBlogPost(Long id, UpdateBlogPostRequest request);

    void deleteBlogPost(Long id);

    GetBlogPostResponse getBlogPost(Long id);

    List<GetBlogPostResponse> getAllBlogPosts(String term);
}
