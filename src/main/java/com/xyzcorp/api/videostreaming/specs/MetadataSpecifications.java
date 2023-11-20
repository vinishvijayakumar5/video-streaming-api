package com.xyzcorp.api.videostreaming.specs;

import com.xyzcorp.api.videostreaming.entity.Metadata;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public interface MetadataSpecifications {

    static Specification<Metadata> isNotDeleted() {
        return (metadata, cq, cb) -> cb.equal(metadata.get("deleted"), 0);
    }

    static Specification<Metadata> titleContains(String title) {
        return (metadata, cq, cb) -> cb.equal(metadata.get("title"), "%" + title + "%");
    }

    static Specification<Metadata> directorContains(String director) {
        return (metadata, cq, cb) -> cb.equal(metadata.get("director"), "%" + director + "%");
    }

    static Specification<Metadata> casteContains(String caste) {
        return (metadata, cq, cb) -> cb.equal(metadata.get("caste"), "%" + caste + "%");
    }

    static Specification<Metadata> genreContains(String genre) {
        return (metadata, cq, cb) -> cb.equal(metadata.get("genre"), "%" + genre + "%");
    }

    static Specification<Metadata> hasYearOfRelease(Integer yearOfRelease) {
        int year = Objects.nonNull(yearOfRelease) ? yearOfRelease : 0;
        return (metadata, cq, cb) -> cb.equal(metadata.get("yearOfRelease"), year);
    }
}
