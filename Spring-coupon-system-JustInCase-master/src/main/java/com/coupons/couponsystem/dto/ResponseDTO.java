package com.coupons.couponsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public class ResponseDTO {
        private String accessToken;
        private final String tokenType = "Bearer ";

        private String grantedAuthority;


                public ResponseDTO(String accessToken,GrantedAuthority grantedAuthority) {
                        this.accessToken = accessToken;
                        this.grantedAuthority = grantedAuthority.getAuthority();
                }
        }
