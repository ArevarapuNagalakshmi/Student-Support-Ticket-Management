package com.edusupport_backend.edusupport_backend.Services;


import com.edusupport_backend.edusupport_backend.DTO.DashboardResponse;

public interface DashboardService {

    DashboardResponse getDashboard();

    DashboardResponse getUserDashboard(Long userId);
}