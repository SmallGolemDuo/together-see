package com.smallgolemduo.togethersee.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String birth;
    private String phoneNumber;
    @OneToMany(mappedBy = "user")
    private List<Board> boards;

    public void modifyUserInfo(String password, String phoneNumber) {
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

}
