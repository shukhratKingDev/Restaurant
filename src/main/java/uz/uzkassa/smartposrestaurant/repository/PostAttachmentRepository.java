package uz.uzkassa.smartposrestaurant.repository;

import org.springframework.data.jpa.repository.Modifying;
import uz.uzkassa.smartposrestaurant.domain.post.PostAttachment;

import java.util.List;


/**
 * Powered by: Rayimjonov Shuxratjon
 * Date: 11.30.2022 11:15
 */

public interface PostAttachmentRepository extends BaseRepository<PostAttachment, String> {

    @Modifying
    void deleteAllByPostId(String postId);

    List<PostAttachment> findAllByPostIdOrderBySorder(String postId);
}
