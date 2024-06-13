package de.tungbeier.panache

import io.quarkus.hibernate.reactive.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class PostRepository : PanacheRepository<Post>