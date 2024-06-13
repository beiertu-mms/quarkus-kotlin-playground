package de.tungbeier.panache

import io.quarkus.test.TestReactiveTransaction
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.UniAsserter
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import strikt.api.expectThat
import strikt.assertions.all
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isContainedIn
import strikt.assertions.isEqualTo

@QuarkusTest
@QuarkusTestResource(PostgresResource::class, restrictToAnnotatedClass = true)
class PostServiceTest {

    @Inject
    lateinit var service: PostService

    @Test
    @TestReactiveTransaction
    fun `Service should be able to register a communication`(asserter: UniAsserter) {
        val title = "a simple post"
        val post = Post(title)
        post.addComment(PostComment("very short post"))

        asserter
            .assertThat(
                { service.register(post) },
                { expectThat(it).isA<RegisterResult.Success>().get(RegisterResult.Success::post) isEqualTo post }
            )
            .assertThat(
                { service.findFirstPostByTitle(title) },
                { expectThat(it).isA<SearchResult.Found>() }
            )
            .assertThat(
                { service.register(post.apply { addComment(PostComment("not recommended")) }) },
                { expectThat(it).isA<RegisterResult.Success>() }
            )
            .assertThat(
                { service.findFirstPostByTitle(title) },
                {
                    expectThat(it).isA<SearchResult.Found>().get(SearchResult.Found::post).and {
                        get(Post::title).isEqualTo(title)
                        get(Post::comments).hasSize(2).all {
                            get(PostComment::review) isContainedIn listOf("very short post", "not recommended")
                        }
                    }
                }
            )
    }
}

class PostgresResource : QuarkusTestResourceLifecycleManager {

    private val databaseName = "mydatabase"
    private val databaseUser = "sarah"
    private val databasePassword = "connor"

    private val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
        .withDatabaseName(databaseName)
        .withUsername(databaseUser)
        .withPassword(databasePassword)

    override fun start(): Map<String, String> {
        postgres.start()

        return mapOf(
            "quarkus.datasource.username" to databaseUser,
            "quarkus.datasource.password" to databasePassword,
            "quarkus.datasource.reactive.url" to postgres.jdbcUrl.replace("jdbc", "vertx-reactive"),
        )
    }

    override fun stop() {
        postgres.stop()
    }
}