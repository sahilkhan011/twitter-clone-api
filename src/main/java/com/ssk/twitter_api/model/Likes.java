package com.ssk.twitter_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "post_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Likes {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,CascadeType.DETACH,
                CascadeType.MERGE,CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,CascadeType.DETACH,
                    CascadeType.MERGE,CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "like_at", updatable = false)
    private LocalDateTime likedAt = LocalDateTime.now();
}
