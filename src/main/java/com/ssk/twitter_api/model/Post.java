package com.ssk.twitter_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "content")
    private String content = "";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "is_updated")
    private boolean isUpdated = false;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = null;

    @Column(name = "likes_count")
    private Long likes_count = 0L;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,CascadeType.MERGE,
                    CascadeType.PERSIST,CascadeType.REFRESH
            })
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<PostMedia> postMedia;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Likes> likesList;

}
