package com.hora.citas.play.service.usecases;

import com.hora.citas.play.entity.Favorite;
import com.hora.citas.play.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveFavoriteUseCase {

    private FavoriteRepository favoriteRepository;

    public SaveFavoriteUseCase(FavoriteRepository favoriteRepository){
        this.favoriteRepository = favoriteRepository;
    }



    public Favorite saveFavorito(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }
}
