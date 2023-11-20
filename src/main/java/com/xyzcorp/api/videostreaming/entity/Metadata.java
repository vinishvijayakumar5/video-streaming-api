package com.xyzcorp.api.videostreaming.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "video_metadata")
public class Metadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String synopsis;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String caste;

    @Column(name = "year_of_release")
    private int yearOfRelease;

    @Column(nullable = false)
    private String genre;

    @Column(name = "running_time", nullable = false)
    private String runningTime;

    @Column(name="created_on", nullable = false)
    private LocalDateTime createdOn;

    private int deleted;
}
