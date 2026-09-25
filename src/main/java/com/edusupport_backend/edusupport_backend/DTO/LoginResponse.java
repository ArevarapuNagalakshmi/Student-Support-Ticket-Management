package com.edusupport_backend.edusupport_backend.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String token;
    private Long userId;
    private String name;
    private String email;
    private String role;
}
