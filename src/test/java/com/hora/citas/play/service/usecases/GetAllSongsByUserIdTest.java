package com.hora.citas.play.service.usecases;

import com.hora.citas.play.entity.Favorite;
import com.hora.citas.play.repository.FavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllSongsByUserIdTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @InjectMocks
    private GetAllSongsByUserId getAllSongsByUserId;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSongs() {

        Long userId = 1L;
        List<Favorite> expectedFavorites = new ArrayList<>();
        expectedFavorites.add(new Favorite());
        expectedFavorites.add(new Favorite());


        when(favoriteRepository.findByUser_Id(anyLong())).thenReturn(expectedFavorites);


        List<Favorite> actualFavorites = getAllSongsByUserId.getAllSongs(userId);


        assertEquals(expectedFavorites.size(), actualFavorites.size());
        assertEquals(expectedFavorites, actualFavorites);
    }
}
