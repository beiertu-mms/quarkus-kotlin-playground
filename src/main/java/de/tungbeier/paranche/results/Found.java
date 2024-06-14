package de.tungbeier.paranche.results;

import de.tungbeier.paranche.Post;

public final class Found extends SearchResult {

    private final Post post;

    public Found(Post post) {
        this.post = post;
    }
}
