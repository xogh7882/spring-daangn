package com.ssafy.Daangn.Controller;

import com.ssafy.Daangn.Domain.Location;
import com.ssafy.Daangn.Service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Location API", description = "Location API")
@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LocationController {
    private final LocationService locationService;

    @Operation(summary = "전체 위치 조회")
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        try{
            List<Location> locations = locationService.findAllLocations();
            return ResponseEntity.ok().body(locations);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "특정 위치 검색 조회")
    @GetMapping("/name/{locationName}")
    public ResponseEntity<List<Location>> getLocationByName(@PathVariable String locationName) {
        try{
            List<Location> locations = locationService.findLocationsByName(locationName);
            return ResponseEntity.ok(locations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
