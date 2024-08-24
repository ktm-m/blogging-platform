package blogger.platform.model.request;

import jakarta.validation.constraints.NotNull;

public record UpdateBlogPostRequest(
        @NotNull String title,
        @NotNull String content,
        @NotNull String category
) {
}
