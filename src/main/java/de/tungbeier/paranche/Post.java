package de.tungbeier.paranche;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Post")
@Table(name = "post")
@NamedQuery(
        name = "Post.findByTitle",
        query = "from Post p left join fetch p.comments c where p.title = :title order by p.id"
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<PostComment> comments = new ArrayList<>();

    //Constructors, getters and setters removed for brevity


    public Post() {
    }

    public Post(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public void addComment(PostComment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(PostComment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
}

