package com.ssk.twitter_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"follower_id", "followed_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follow {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,CascadeType.DETACH,
                CascadeType.MERGE,CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,CascadeType.DETACH,
                    CascadeType.MERGE,CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "followed_id", nullable = false)
    private User followed;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
