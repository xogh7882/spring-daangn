package com.ssafy.Daangn.Controller;

import com.ssafy.Daangn.Domain.Board;
import com.ssafy.Daangn.Service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Board API", description = "Board API")
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BoardController {
    private final BoardService boardService;

    @Operation(summary = "전체 게시판 조회")
    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards(){
        try{
            List<Board> boards = boardService.findAllBoards();
            return ResponseEntity.ok(boards);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "특정 위치의 게시판 조회")
    @PostMapping("/location")
    public ResponseEntity<List<Board>> getBoardsByLocation(@RequestBody Board board){
        try {
            List<Board> boards = boardService.findBoardsByLocation(board.getLocation());
            return ResponseEntity.ok(boards);
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "특정 카테고리 게시판 조회")
    @GetMapping("/category/{catoegory}")
    public ResponseEntity<List<Board>> getBoardsByCategory(@PathVariable String catoegory){
        try{
            List<Board> boards = boardService.findBoardsByCategory(catoegory);
            return ResponseEntity.ok(boards);
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "검색어를 포함한 제목의 게시판 조회")
    @GetMapping("/serach/{keyword}")
    public ResponseEntity<List<Board>> getBoardsByKeyword(@PathVariable String keyword){
        try{
            List<Board> boards = boardService.findBoardsByTitle(keyword);
            return ResponseEntity.ok(boards);
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "게시글 생성")
    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board){
        try{
            Board savedBoard = boardService.saveBoard(board);
            return ResponseEntity.ok(savedBoard);
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "게시글 수정")
    @PutMapping
    public ResponseEntity<Board> updateBoard(@RequestBody Board board){
        try{
            Board updatedBoard = boardService.saveBoard(board);
            return ResponseEntity.ok(updatedBoard);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Board> deleteBoard(@PathVariable Long id){
        try{
            boardService.deleteBoard(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
   }
}
