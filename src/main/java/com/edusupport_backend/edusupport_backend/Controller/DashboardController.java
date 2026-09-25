package com.edusupport_backend.edusupport_backend.Controller;


import com.edusupport_backend.edusupport_backend.DTO.DashboardResponse;
import com.edusupport_backend.edusupport_backend.Services.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {

        return ResponseEntity.ok(
                dashboardService.getDashboard()
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<DashboardResponse> getUserDashboard(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getUserDashboard(userId)
        );
    }
}
