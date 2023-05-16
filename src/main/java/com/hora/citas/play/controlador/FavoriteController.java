package com.hora.citas.play.controlador;

import com.hora.citas.play.controlador.dto.FavoriteDTO;
import com.hora.citas.play.entity.Favorite;
import com.hora.citas.play.entity.User;
import com.hora.citas.play.service.usecases.CreateUserUseCase;
import com.hora.citas.play.service.usecases.GetAllSongsByUserId;
import com.hora.citas.play.service.usecases.GetUserByIdUseCase;
import com.hora.citas.play.service.usecases.SaveFavoriteUseCase;
import com.hora.citas.play.util.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/api/favorites")
@RestController
@CrossOrigin("*")
public class FavoriteController {
    public FavoriteController(CreateUserUseCase createUserUseCase
            , GetUserByIdUseCase getUserByIdUseCase
            , SaveFavoriteUseCase saveFavoriteUseCase
            , GetAllSongsByUserId getAllSongsByUserId) {
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.saveFavoriteUseCase = saveFavoriteUseCase;
        this.getAllSongsByUserId = getAllSongsByUserId;
    }

    private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    private CreateUserUseCase createUserUseCase;

    private GetUserByIdUseCase getUserByIdUseCase;

    private SaveFavoriteUseCase saveFavoriteUseCase;

    private GetAllSongsByUserId getAllSongsByUserId;


    @PostMapping("/guardar")
    public ResponseEntity<Object> addFavorite(@RequestBody FavoriteDTO favoriteDTO, @RequestHeader("Authorization") String token) {
        try {

            if (!TokenUtil.validateToken(token)) {
                logger.warn("Invalid access token");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de acceso inválido");
            }
            Long userId = favoriteDTO.getUser().getId();

            User user = getUserByIdUseCase
                    .findUserById(userId)
                    .orElse(null); //

            if (user == null) {
                logger.warn("User not found for ID: {}", userId);
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }

            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setSongId(favoriteDTO.getSongId());

            Favorite favoriteSaved = saveFavoriteUseCase.saveFavorito(favorite);
            logger.info("Favorite saved successfully: {}", favoriteSaved.getId());
            return ResponseEntity.ok().body(favoriteSaved);
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/canciones/{userId}")
    public ResponseEntity<Object> getCancionesFavoritas(@PathVariable Long userId, @RequestHeader("Authorization") String token) {
        try {
            if (!TokenUtil.validateToken(token)) {
                logger.warn("Invalid access token");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de acceso inválido");
            }

            List<Favorite> favorites = getAllSongsByUserId.getAllSongs(userId);
            Set<String> uniqueSongIds = favorites.stream()
                    .map(Favorite::getSongId)
                    .collect(Collectors.toSet());
            logger.info("Retrieved favorite songs for user: {}", userId);

            return ResponseEntity.ok().body(uniqueSongIds);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de acceso expirado");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de acceso inválido");
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}

