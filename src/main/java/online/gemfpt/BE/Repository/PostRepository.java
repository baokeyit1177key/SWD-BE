package online.gemfpt.BE.Repository;

import online.gemfpt.BE.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}