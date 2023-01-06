package com.coupons.couponsystem.DOT;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

        @Data
        @NoArgsConstructor
        @Builder
        public class ResponseDTO {
        private String accessToken;
        private final String tokenType = "Bearer ";


        public ResponseDTO(String accessToken)
        {
            this.accessToken=accessToken;
        }
}
