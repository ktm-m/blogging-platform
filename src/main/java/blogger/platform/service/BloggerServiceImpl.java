package blogger.platform.service;

import blogger.platform.exception.InternalServiceException;
import blogger.platform.exception.NotFoundException;
import blogger.platform.model.entity.Blogger;
import blogger.platform.model.request.CreateBlogPostRequest;
import blogger.platform.model.request.UpdateBlogPostRequest;
import blogger.platform.model.response.GetBlogPostResponse;
import blogger.platform.repository.BloggerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloggerServiceImpl implements BloggerService {
    private static final Logger logger = LoggerFactory.getLogger(BloggerServiceImpl.class);
    private final BloggerRepository bloggerRepository;

    public BloggerServiceImpl(BloggerRepository bloggerRepository) {
        this.bloggerRepository = bloggerRepository;
    }

    @Override
    public GetBlogPostResponse createBlogPost(CreateBlogPostRequest request) throws InternalServiceException {
        try {
            Blogger post = new Blogger();
            post.setTitle(request.title());
            post.setContent(request.content());
            post.setCategory(request.category());

            Blogger savedPost = bloggerRepository.save(post);

            logger.info("Created blog post with ID: {} successfully", savedPost.getId());
            return new GetBlogPostResponse(
                    savedPost.getId(),
                    savedPost.getTitle(),
                    savedPost.getContent(),
                    savedPost.getCategory(),
                    savedPost.getCreatedAt(),
                    savedPost.getUpdatedAt()
            );
        } catch (Exception e) {
            logger.error("Failed to create blog post", e);
            throw new InternalServiceException("Failed to create blog post");
        }
    }

    @Override
    public GetBlogPostResponse updateBlogPost(Long id, UpdateBlogPostRequest request) throws InternalServiceException {
        try {
            Blogger post = bloggerRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog post with ID " + id + " not found"));

            post.setTitle(request.title());
            post.setContent(request.content());
            post.setCategory(request.category());

            Blogger savedPost = bloggerRepository.save(post);

            logger.info("Updated blog post with ID: {} successfully", savedPost.getId());
            return new GetBlogPostResponse(
                    savedPost.getId(),
                    savedPost.getTitle(),
                    savedPost.getContent(),
                    savedPost.getCategory(),
                    savedPost.getCreatedAt(),
                    savedPost.getUpdatedAt()
            );
        } catch (NotFoundException e) {
            logger.error("Failed to update blog post with ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            logger.error("Failed to update blog post with ID: {}", id, e);
            throw new InternalServiceException("Failed to update blog post");
        }
    }

    @Override
    public void deleteBlogPost(Long id) throws InternalServiceException {
        try {
            bloggerRepository.deleteById(id);
            logger.info("Deleted blog post with ID: {} successfully", id);
        } catch (Exception e) {
            logger.error("Failed to delete blog post with ID: {}", id, e);
            throw new InternalServiceException("Failed to delete blog post");
        }
    }

    @Override
    public GetBlogPostResponse getBlogPost(Long id) throws InternalServiceException, NotFoundException {
        try {
            Blogger post = bloggerRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog post with ID " + id + " not found"));

            logger.info("Retrieved blog post with ID: {} successfully", id);
            return new GetBlogPostResponse(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getCategory(),
                    post.getCreatedAt(),
                    post.getUpdatedAt()
            );
        } catch (NotFoundException e) {
            logger.error("Failed to get blog post with ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            logger.error("Failed to get blog post with ID: {}", id, e);
            throw new InternalServiceException("Failed to get blog post");
        }
    }

    @Override
    public List<GetBlogPostResponse> getAllBlogPosts(String term) throws InternalServiceException, NotFoundException {
        try {
            List<GetBlogPostResponse> posts;
            if (term == null || term.isEmpty()) {
                posts = bloggerRepository.findAll().stream().map(post -> new GetBlogPostResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCategory(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                )).collect(Collectors.toList());

            } else {
                posts = bloggerRepository.findAllByTerm(term).stream().map(post -> new GetBlogPostResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCategory(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                )).collect(Collectors.toList());

            }

            if (posts.isEmpty()) {
                throw new NotFoundException("No blog posts found");
            }

            logger.info("Retrieved {} blog posts successfully", posts.size());
            return posts;
        } catch (Exception e) {
            logger.error("Failed to get all blog posts", e);
            if (e instanceof NotFoundException) {
                throw (NotFoundException) e;
            }
            throw new InternalServiceException("Failed to get blog posts");
        }
    }
}
