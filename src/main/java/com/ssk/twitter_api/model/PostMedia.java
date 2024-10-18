package com.ssk.twitter_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostMedia {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "content_type", nullable = false)
    private String contentType; // You might want to use an enum here

    @Column(name = "url",nullable = false)
    private String url;
}



