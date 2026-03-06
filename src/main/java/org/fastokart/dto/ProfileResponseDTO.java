package org.fastokart.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponseDTO {
    private String name;
    private String email;
    private String phone;
    private String profileImageUrl;
    private boolean emailVerified;
    private boolean phoneVerified;
}
