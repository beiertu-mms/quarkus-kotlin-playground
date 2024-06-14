package de.tungbeier.paranche;

import de.tungbeier.paranche.results.Conflict;
import de.tungbeier.paranche.results.Failure;
import de.tungbeier.paranche.results.Found;
import de.tungbeier.paranche.results.NotFound;
import de.tungbeier.paranche.results.RegisterResult;
import de.tungbeier.paranche.results.SearchResult;
import de.tungbeier.paranche.results.Success;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.exception.ConstraintViolationException;

@ApplicationScoped
public class PostService {
    @Inject
    PostRepository postRepository;

    public Uni<RegisterResult> register(Post post) {
        return postRepository
                .persistAndFlush(post)
                .map(p -> (RegisterResult) new Success(p))
                .onFailure().recoverWithItem(error -> {
                    Log.error("Failed to register a post", error);
                    if (error instanceof ConstraintViolationException) {
                        return new Conflict(post, error);
                    } else {
                        return new Failure(post, error);
                    }
                });
    }

    public Uni<SearchResult> findFirstByTitle(String title) {
        return postRepository
                .find("#Post.findByTitle", Parameters.with("title", title))
                .firstResult()
                .map(post -> (SearchResult) new Found(post))
                .onFailure().recoverWithItem(error -> {
                    Log.error("Failed to find a post with title " + title, error);
                    return new NotFound(error);
                });
    }
}
