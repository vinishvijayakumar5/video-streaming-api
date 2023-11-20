package com.xyzcorp.api.videostreaming.repository;

import com.xyzcorp.api.videostreaming.entity.Views;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewsRepository extends JpaRepository<Views, Long> {

    @Modifying
    @Query("update Views v set v.deleted = 1 where v.content.id = :contentId")
    int delete(@Param("contentId") long contentId);

    @Query("select count(*) from Views v where v.content.id = :contentId and v.deleted = 0")
    int countByContentId(long contentId);
}
