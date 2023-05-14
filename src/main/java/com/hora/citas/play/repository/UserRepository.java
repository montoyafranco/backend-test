package com.hora.citas.play.repository;

import com.hora.citas.play.entity.Cita;
import com.hora.citas.play.entity.Paciente;
import com.hora.citas.play.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
