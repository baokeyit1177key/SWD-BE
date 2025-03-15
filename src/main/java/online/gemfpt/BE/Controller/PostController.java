package online.gemfpt.BE.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import online.gemfpt.BE.model.PostRequest;
import online.gemfpt.BE.model.PostResponse;
import online.gemfpt.BE.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    @Operation(summary = "Tạo bài viết mới",
            description = "Tạo một bài viết hoặc blog mới với tiêu đề, nội dung và thông tin tác giả.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bài viết được tạo thành công, trả về thông tin chi tiết."),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ (thiếu tiêu đề, authorId không tồn tại).")
    })
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.createPost(request));
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách bài viết",
            description = "Trả về danh sách tất cả bài viết hoặc blog trong hệ thống.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Danh sách bài viết được trả về thành công.")
    })
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Xem chi tiết bài viết",
            description = "Lấy thông tin chi tiết của một bài viết dựa trên ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thông tin bài viết được trả về thành công."),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy bài viết với ID đã cung cấp.")
    })
    public ResponseEntity<PostResponse> getPostById(
            @Parameter(description = "ID của bài viết cần xem", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Chỉnh sửa bài viết",
            description = "Cập nhật thông tin của một bài viết hiện có dựa trên ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bài viết được cập nhật thành công."),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ."),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy bài viết hoặc tác giả với ID đã cung cấp.")
    })
    public ResponseEntity<PostResponse> updatePost(
            @Parameter(description = "ID của bài viết cần chỉnh sửa", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa bài viết",
            description = "Xóa một bài viết hoặc blog dựa trên ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bài viết được xóa thành công."),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy bài viết với ID đã cung cấp.")
    })
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "ID của bài viết cần xóa", example = "1")
            @PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}