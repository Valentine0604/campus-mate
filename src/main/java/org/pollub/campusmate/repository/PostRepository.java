package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Post findByPostTitle(String postTitle);

    boolean existsByPostId(Long postId);

    boolean existsByPostTitle(String postTitle);

    void deleteByPostTitle(String postTitle);
}
