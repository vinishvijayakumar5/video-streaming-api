package com.xyzcorp.api.videostreaming.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "video_impression")
public class Impressions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @JoinColumn(name = "metadata_id")
    @ManyToOne
    private Metadata metadata;

    @Column(name="created_on", nullable = false)
    private LocalDateTime createdOn;

    private int deleted;
}
