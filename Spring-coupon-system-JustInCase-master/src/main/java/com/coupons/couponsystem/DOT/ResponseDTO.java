package com.coupons.couponsystem.DOT;

import lombok.Data;

        @Data
        public class ResponseDTO {
        private String accessToken;
        private String tokenType = "Bearer ";


        public ResponseDTO(String accessToken)
        {
            this.accessToken=accessToken;
        }
}
