package com.ssafy.Daangn.Controller;

import com.ssafy.Daangn.Domain.Board;
import com.ssafy.Daangn.Domain.Like;
import com.ssafy.Daangn.Domain.User;
import com.ssafy.Daangn.Service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Like API", description = "Like API")
@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "사용자 좋아요 조회")
    @PostMapping("/user")
    public ResponseEntity<List<Like>> addLike(@RequestBody User user) {
        try{
            List<Like> likes = likeService.findLikesByUser(user);
            return ResponseEntity.ok(likes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "특정 사용자 좋아요 게시판 전체 조회")
    @PostMapping("/boards/user")
    public ResponseEntity<List<Board>> findLikedBoardsByUser(@RequestBody User user) {
        try{
            List<Board> boards = likeService.findLikedBoardsByUser(user);
            return ResponseEntity.ok(boards);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(summary = "좋아요 저장")
    @PostMapping
    public ResponseEntity<Like> saveLike(@RequestBody Like like) {
        try {
            Like savedLike = likeService.saveLike(like);
            return ResponseEntity.ok(savedLike);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "좋아요 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
        try {
            likeService.deleteLike(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
