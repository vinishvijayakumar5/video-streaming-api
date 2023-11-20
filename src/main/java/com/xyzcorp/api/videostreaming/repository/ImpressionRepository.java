package com.xyzcorp.api.videostreaming.repository;

import com.xyzcorp.api.videostreaming.entity.Impressions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpressionRepository extends JpaRepository<Impressions, Long> {

    @Modifying
    @Query("update Impressions i set i.deleted = 1 where i.metadata.id = :metadataId")
    int delete(@Param("metadataId") long metadataId);

    @Query("select count(*) from Impressions i where i.metadata.id = :metadataId and i.deleted = 0")
    int countByMetadataId(long metadataId);
}
