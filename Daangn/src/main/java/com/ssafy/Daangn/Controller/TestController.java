package com.ssafy.Daangn.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Test API", description = "CI/CD 테스트 Controller")
@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {

    @Operation(summary = "CI/CD 테스트 API")
    @GetMapping("/CICD")
    public ResponseEntity<Map<String,Object>> CICDTest(){
        Map<String,Object> response = new HashMap<>();
        response.put("message", "CI/CD 배포 성공");
        return ResponseEntity.ok(response);
    }
}
