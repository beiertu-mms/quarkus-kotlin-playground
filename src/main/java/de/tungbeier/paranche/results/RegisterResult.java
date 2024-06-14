package de.tungbeier.paranche.results;

import de.tungbeier.paranche.Post;

public abstract sealed class RegisterResult permits Conflict, Failure, Success {
    protected final Post post;

    protected RegisterResult(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return this.post;
    }
}
