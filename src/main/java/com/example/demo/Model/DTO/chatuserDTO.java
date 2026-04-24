package com.example.demo.Model.DTO;



public class chatuserDTO {
     private Long userId;
    private String name;
        public chatuserDTO(Long userId, String name) {
            this.userId = userId;
            this.name = name;
        }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
