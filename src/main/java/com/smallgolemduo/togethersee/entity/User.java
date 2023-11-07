package com.smallgolemduo.togethersee.entity;

import javax.persistence.*;

import com.smallgolemduo.togethersee.dto.request.UpdateBoardRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    public void addBoards(Board board) {
        board.setUser(this);
        this.boards.add(board);
    }

    public void modifyUserInfo(String password, String phoneNumber) {
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public boolean isUserIdByBoard(UpdateBoardRequest updateBoardRequest) {
        return this.boards.stream()
                .anyMatch(board -> board.getUser().getId().equals(updateBoardRequest.getUserId()));
    }

}