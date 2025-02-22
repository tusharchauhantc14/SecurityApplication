package com.tushar.SecurityApp.SecurityApplication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String title;
    private String description;

    private UserDto author;


    public PostDTO(){

    }

    public PostDTO(Long id, String title, String description) {
        this.id=id;
        this.title = title;
        this.description = description;
    }
}
