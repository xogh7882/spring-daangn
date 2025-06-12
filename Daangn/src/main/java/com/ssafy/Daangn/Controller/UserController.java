package com.ssafy.Daangn.Controller;


import com.ssafy.Daangn.Domain.User;
import com.ssafy.Daangn.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "User API", description = "User API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @Operation(summary = "전체 사용자 조회")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "userId로 특정 유저 찾기")
    @GetMapping("/userid/{userId}")
    public ResponseEntity<User> getUserByUserId(@PathVariable String userId) {
        User user = userService.getUserByUserId(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "locationId로 특정 위치에 사는 유저 찾기")
    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<User>> getUserByLocationId(@PathVariable Integer locationId){
        List<User> users = userService.findUsersByLocationId(locationId);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "회원가입")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "사용자 정보 수정")
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        try{
            User updateUser = userService.updateUser(user);
            return ResponseEntity.ok(updateUser);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "사용자 삭제")
    @DeleteMapping
    public ResponseEntity<User> deleteUser(@RequestBody User user){
        try {
            userService.deleteUser(user.getId());
            return ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


    // 내 프로필 조회하기
    @Operation(summary = "내 프로필 조회", security = @SecurityRequirement(name = "bearerAuth" ))
    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(){
        try{
            User myProfile = userService.getMyProfile();
            return ResponseEntity.ok(myProfile);
        } catch(IllegalStateException  e){
            Map<String, Object> error = new HashMap<>();
            error.put("code", 401);
            error.put("message", e.getMessage());
            return ResponseEntity.status(401).body(error);
        } catch(Exception e){
            Map<String, Object> error = new HashMap<>();
            error.put("code", 404);
            error.put("message", e.getMessage());
            return ResponseEntity.status(404).body(error);
        }
    }
}
