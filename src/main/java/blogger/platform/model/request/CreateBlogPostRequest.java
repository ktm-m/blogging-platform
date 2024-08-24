package blogger.platform.model.request;

import jakarta.validation.constraints.NotNull;

public record CreateBlogPostRequest(
        @NotNull String title,
        @NotNull String content,
        @NotNull String category
) {
}
