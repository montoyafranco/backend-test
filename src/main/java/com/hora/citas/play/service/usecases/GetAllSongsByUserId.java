package com.hora.citas.play.service.usecases;

import com.hora.citas.play.entity.Favorite;
import com.hora.citas.play.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllSongsByUserId {

    private FavoriteRepository favoriteRepository;

    public GetAllSongsByUserId(FavoriteRepository favoriteRepository){
        this.favoriteRepository = favoriteRepository;
    }



    public List<Favorite> getAllSongs(Long userId) {
        return favoriteRepository.findByUser_Id(userId);
    }
}
