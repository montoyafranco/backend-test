package com.hora.citas.play.repository;

import com.hora.citas.play.entity.Cita;
import com.hora.citas.play.entity.Favorite;
import com.hora.citas.play.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
