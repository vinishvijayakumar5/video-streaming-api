package com.xyzcorp.api.videostreaming.repository;

import com.xyzcorp.api.videostreaming.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    @Modifying
    @Query("update Content c set c.deleted = 1 where c.id = :id")
    int delete(@Param("id") long id);
    @Query("select c from Content c where c.id = :id and c.deleted = 0")
    Content findContentById(@Param("id") long id);

}
