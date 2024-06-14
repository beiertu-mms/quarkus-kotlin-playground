package de.tungbeier.paranche.results;

public final class NotFound extends SearchResult {
    private final Throwable error;

    public NotFound(Throwable error) {
        super();
        this.error = error;
    }
}
