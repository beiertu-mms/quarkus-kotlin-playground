package de.tungbeier.panache

/*
import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.quarkus.logging.Log
import io.quarkus.panache.common.Parameters
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.hibernate.exception.ConstraintViolationException

@ApplicationScoped
class PostRepository : PanacheRepository<Post>

@ApplicationScoped
class PostService(private val repo: PostRepository) {

    fun register(post: Post): Uni<RegisterResult> = repo.persistAndFlush(post)
        .map {
            @Suppress("useless_cast")
            RegisterResult.Success(post) as RegisterResult
        }
        .onFailure().recoverWithItem { error ->
            Log.error("Failed to register a post", error)
            when (error) {
                is ConstraintViolationException -> RegisterResult.Conflict(post, error)
                else -> RegisterResult.Failure(post, error)
            }
        }

    fun findFirstPostByTitle(title: String): Uni<SearchResult> = repo
        .find("#Post.findByTitle", Parameters.with(Post::title.name, title))
        .firstResult<Post>()
        .map {
            @Suppress("useless_cast")
            SearchResult.Found(it) as SearchResult
        }
        .onFailure().recoverWithItem { error ->
            Log.error("Failed to search for post with title=$title", error)
            SearchResult.NotFound(error)
        }
}

sealed class RegisterResult(val post: Post) {
    class Success(post: Post) : RegisterResult(post)
    class Conflict(post: Post, val cause: Throwable) : RegisterResult(post)
    class Failure(post: Post, val cause: Throwable) : RegisterResult(post)
}

sealed class SearchResult {
    class Found(val post: Post) : SearchResult()
    class NotFound(val cause: Throwable) : SearchResult()
}
*/