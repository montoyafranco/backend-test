package com.hora.citas.play.controlador.dto;

import com.hora.citas.play.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDTO {


    private Long id;


    private User user;


    private String songId;


    public FavoriteDTO(User user, String songId) {
        this.user = user;
        this.songId = songId;
    }
}
