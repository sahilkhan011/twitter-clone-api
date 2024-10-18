package com.ssk.twitter_api.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_image_url")
    private String profileImageUrl;


    @Column(name = "cover_image_url")
    private String cover_image_url;

    @Column(name = "following_count", nullable = false)
    private int followingCount = 0;

    @Column(name = "followers_count", nullable = false)
    private int followersCount = 0;

    @Column(name = "is_verified")
    private boolean isVerified = false;

    @Column(name = "location")
    private String location;

    @Column(name = "website_url")
    private String websiteUrl;

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Follow> followingList;

    @OneToMany(mappedBy = "followed", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Follow> followersList;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> postList;

}
