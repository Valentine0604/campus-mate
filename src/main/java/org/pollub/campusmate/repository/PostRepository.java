package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.Post;
import org.pollub.campusmate.entity.User;
import org.pollub.campusmate.service.UserService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {


    Post findPostByPostId(Long postId);

}

