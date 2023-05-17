package com.hora.citas.play.service.usecases;

import com.hora.citas.play.entity.Favorite;
import com.hora.citas.play.repository.FavoriteRepository;
import com.hora.citas.play.service.usecases.SaveFavoriteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SaveFavoriteUseCaseTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    private SaveFavoriteUseCase saveFavoriteUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        saveFavoriteUseCase = new SaveFavoriteUseCase(favoriteRepository);
    }

    @Test
    void testSaveFavorite() {
        // Crear un objeto Favorite para el test
        Favorite favorite = new Favorite();

        // Simular el comportamiento del repositorio
        when(favoriteRepository.save(favorite)).thenReturn(favorite);

        // Ejecutar el caso de uso
        Favorite savedFavorite = saveFavoriteUseCase.saveFavorito(favorite);

        // Verificar que se haya guardado correctamente
        assertNotNull(savedFavorite);
        verify(favoriteRepository).save(favorite);
    }
}
