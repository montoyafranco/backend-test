package com.hora.citas.play.controlador;

import com.hora.citas.play.controlador.dto.FavoriteDTO;
import com.hora.citas.play.controlador.dto.UserDTO;
import com.hora.citas.play.entity.Favorite;
import com.hora.citas.play.entity.User;
import com.hora.citas.play.service.usecases.CreateUserUseCase;
import com.hora.citas.play.service.usecases.GetAllSongsByUserId;
import com.hora.citas.play.service.usecases.GetUserByIdUseCase;
import com.hora.citas.play.service.usecases.SaveFavoriteUseCase;
import com.hora.citas.play.util.TokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FavoriteControllerTest {

    @Mock
    private CreateUserUseCase createUserUseCase;
    @Mock
    private GetUserByIdUseCase getUserByIdUseCase;
    @Mock
    private SaveFavoriteUseCase saveFavoriteUseCase;
    @Mock
    private GetAllSongsByUserId getAllSongsByUserId;


    @InjectMocks
    private FavoriteController favoriteController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFavorite_ValidToken() {
        // Token válido
        String SECRET_KEY = "montoya_clave_secreta343434343434343434#";

        String token = Jwts.builder()
                .setSubject("john_doe")
                .setExpiration(new Date(System.currentTimeMillis() + 86400000L))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        String authorizationHeader = "Bearer " + token;


        FavoriteDTO favoriteDTO = new FavoriteDTO();
        favoriteDTO.setUser(new com.hora.citas.play.entity.User());
        favoriteDTO.getUser().setId(1L);
        favoriteDTO.getUser().setUsername("john_doe");
        favoriteDTO.getUser().setPassword("password123");
        favoriteDTO.setSongId("12345");


        when(getUserByIdUseCase.findUserById(anyLong())).thenReturn(Optional.of(new com.hora.citas.play.entity.User()));


        when(saveFavoriteUseCase.saveFavorito(any(com.hora.citas.play.entity.Favorite.class))).thenReturn(new com.hora.citas.play.entity.Favorite());


        ResponseEntity<Object> response = favoriteController.addFavorite(favoriteDTO, authorizationHeader);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    @Test
    void testGetCancionesFavoritas_ValidToken() {
        // Token válido
        String SECRET_KEY = "montoya_clave_secreta343434343434343434#";

        String token = Jwts.builder()
                .setSubject("john_doe") // Establecer el nombre de usuario
                .setExpiration(new Date(System.currentTimeMillis() + 86400000L))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        String authorizationHeader = "Bearer " + token;


        List<Favorite> favorites = new ArrayList<>();
        favorites.add(new Favorite(new User(), "123"));
        favorites.add(new Favorite(new User(), "456"));
        when(getAllSongsByUserId.getAllSongs(anyLong())).thenReturn(favorites);


        ResponseEntity<Object> response = favoriteController.getCancionesFavoritas(1L, authorizationHeader);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());


        Set<String> uniqueSongIds = (Set<String>) response.getBody();
        assertNotNull(uniqueSongIds);
        assertEquals(2, uniqueSongIds.size());
        assertTrue(uniqueSongIds.contains("123"));
        assertTrue(uniqueSongIds.contains("456"));
    }

}