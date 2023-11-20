package com.xyzcorp.api.videostreaming.repository;

import com.xyzcorp.api.videostreaming.entity.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Long>, JpaSpecificationExecutor<Metadata> {

    @Query("select m from Metadata m where m.title = :title and m.deleted = 0")
    Metadata findByTitle(@Param("title") String title);

    @Query("select m from Metadata m where m.id = :id and m.deleted = 0")
    Metadata findMetadataById(@Param("id") long id);

    @Query("select m from Metadata m where m.deleted = 0")
    List<Metadata> findAllMetadata();

    @Modifying
    @Query("update Metadata m set m.deleted = 1 where m.id = :id")
    int delete(@Param("id") long id);
}
