package de.tungbeier.paranche;

import de.tungbeier.paranche.results.Found;
import de.tungbeier.paranche.results.Success;
import de.tungbeier.panache.PostgresResource;
import io.quarkus.logging.Log;
import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
class JavaPostServiceTest {
    @Inject
    PostService service;

    @Test
    @TestReactiveTransaction
    public void testPostServiceWithoutRead(UniAsserter asserter) {
        var title = "a java post";
        var post = new Post(title);
        post.addComment(new PostComment("first comment"));

        asserter
                .assertThat(
                        () -> {
                            Log.info("verify that post can be registered");
                            return service.register(post);
                        },
                        result -> Assertions.assertInstanceOf(Success.class, result))
                .assertThat(
                        () -> {
                            Log.info("verify that a new comment can be added to the post");
                            post.addComment(new PostComment("second comment"));
                            return service.register(post);
                        },
                        result -> Assertions.assertInstanceOf(Success.class, result)
                )
        ;
    }

    @Test
    @TestReactiveTransaction
    public void testPostService(UniAsserter asserter) {
        var title = "a java post";
        var post = new Post(title);
        post.addComment(new PostComment("first comment"));

        asserter
                .assertThat(
                        () -> {
                            Log.info("verify that post can be registered");
                            return service.register(post);
                        },
                        result -> Assertions.assertInstanceOf(Success.class, result))
                .assertThat(
                        () -> {
                            Log.info("verify that post can be found");
                            return service.findFirstByTitle(title);
                        },
                        result -> Assertions.assertInstanceOf(Found.class, result))
                .assertThat(
                        () -> {
                            Log.info("verify that a new comment can be added to the post");
                            post.addComment(new PostComment("second comment"));
                            return service.register(post);
                        },
                        result -> Assertions.assertInstanceOf(Success.class, result)
                )
        ;
    }
}