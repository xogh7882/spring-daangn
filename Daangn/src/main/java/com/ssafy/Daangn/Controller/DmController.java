package com.ssafy.Daangn.Controller;

import com.ssafy.Daangn.Domain.DmRoom;
import com.ssafy.Daangn.Domain.User;
import com.ssafy.Daangn.Service.DmRoomService;
import com.ssafy.Daangn.Service.DmService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dm")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DmController {
    private final DmService dmService;
    private final DmRoomService dmRoomService;

    @Operation(summary = "전체 채팅방 조회")
    @GetMapping
    public ResponseEntity<List<DmRoom>> findAllDmRooms() {
        try{
            List<DmRoom> dmRooms = dmRoomService.findAllDmRooms();
            return ResponseEntity.ok(dmRooms);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "사용자 채팅방 조회")
    public ResponseEntity<List<DmRoom>> findDmRoomByUser(@RequestBody User user){
        try{
            List<DmRoom> dmRooms = dmRoomService.findDmRoomsByUser(user);
            return ResponseEntity.ok(dmRooms);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "채팅방 생성")
    @PostMapping("/create")
    public ResponseEntity<DmRoom> getOrCreateDmRoom(@RequestParam Integer userId, @RequestParam Long boardId) {
        try {
            DmRoom dmRoom = dmRoomService.getOrCreateDmRoom(userId, boardId);
            return ResponseEntity.ok(dmRoom);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "채팅방 저장")
    @PostMapping
    public ResponseEntity<DmRoom> saveDmRoom(@RequestBody DmRoom dmRoom) {
        try {
            DmRoom savedDmRoom = dmRoomService.saveDmRoom(dmRoom);
            return ResponseEntity.ok(savedDmRoom);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "채팅방 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDmRoom(@PathVariable Integer id) {
        try {
            dmRoomService.deleteDmRoom(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
