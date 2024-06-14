package de.tungbeier.panache

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.NamedQuery
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.Objects

@Entity
@Table(name = "post")
@NamedQuery(
    name = "Post.findByTitle",
    query = "from Post p left join fetch p._comments c where p.title = :title order by p.id"
)
class Post() {

    constructor(
        title: String,
    ) : this() {
        this.title = title
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var title: String? = null

    @OneToMany(
        mappedBy = "post",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY,
    )
    private val _comments: MutableList<PostComment> = mutableListOf()

    val comments: List<PostComment> get() = _comments.toList()

    fun addComment(comment: PostComment) {
        _comments.add(comment)
        comment.post = this
    }

    fun removeComment(comment: PostComment) {
        _comments.remove(comment)
        comment.post = null
    }

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is Post -> false
        else -> id != null && id == other.id
    }

    override fun hashCode(): Int = Objects.hash(id)
}

@Entity
@Table(name = "post_comment")
class PostComment() {

    constructor(
        review: String,
    ) : this() {
        this.review = review
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var review: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    var post: Post? = null

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is PostComment -> false
        else -> id != null && id == other.id
    }

    override fun hashCode(): Int = Objects.hash(id)
}