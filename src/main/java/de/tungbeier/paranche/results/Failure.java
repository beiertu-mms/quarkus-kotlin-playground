package de.tungbeier.paranche.results;

import de.tungbeier.paranche.Post;

public final class Failure extends RegisterResult {
    private final Throwable error;

    public Failure(Post post, Throwable error) {
        super(post);
        this.error = error;
    }
}
