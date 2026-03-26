package com.portfolio.repository;

import com.portfolio.entity.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    List<BlogPost> findByPublishedTrueOrderByCreatedAtDesc();

    Page<BlogPost> findByPublishedTrue(Pageable pageable);

    List<BlogPost> findByCategoryAndPublishedTrueOrderByCreatedAtDesc(String category);
}
