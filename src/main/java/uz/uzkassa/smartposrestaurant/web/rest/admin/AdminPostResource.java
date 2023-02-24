package uz.uzkassa.smartposrestaurant.web.rest.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.post.PostDTO;
import uz.uzkassa.smartposrestaurant.dto.post.PostDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.post.PostListDTO;
import uz.uzkassa.smartposrestaurant.enums.AttachmentType;
import uz.uzkassa.smartposrestaurant.enums.PostStatus;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.service.PostService;

import java.util.EnumSet;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 17.10.2022 15:32
 */
@RestController
@RequestMapping(ApiConstants.adminPostRootApi)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AdminPostResource {

    PostService postService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.create(postDTO));
    }

    @GetMapping(ApiConstants.id)
    public ResponseEntity<PostDetailDTO> get(@PathVariable String id) {
        return ResponseEntity.ok(postService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<PostListDTO>> getList(BaseFilter filter) {
        return ResponseEntity.ok(postService.getList(filter));
    }

    @PutMapping(ApiConstants.id)
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.update(id, postDTO));
    }

    @GetMapping(ApiConstants.statuses)
    public ResponseEntity<EnumSet<PostStatus>> getStatuses() {
        return ResponseEntity.ok(EnumSet.allOf(PostStatus.class));
    }

    @PatchMapping(ApiConstants.updateStatus)
    public ResponseEntity<Void> updateStatus(@PathVariable String id, @RequestParam("status") String status) {
        postService.updateStatus(id, PostStatus.valueOf(status));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/attachment-types")
    public ResponseEntity<EnumSet<AttachmentType>> getAttachmentTypes() {
        return ResponseEntity.ok().body(EnumSet.allOf(AttachmentType.class));
    }
}
