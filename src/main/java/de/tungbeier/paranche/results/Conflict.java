package de.tungbeier.paranche.results;

import de.tungbeier.paranche.Post;

public final class Conflict extends RegisterResult {
    private final Throwable error;


    public Conflict(Post post, Throwable error) {
        super(post);
        this.error = error;
    }
}
