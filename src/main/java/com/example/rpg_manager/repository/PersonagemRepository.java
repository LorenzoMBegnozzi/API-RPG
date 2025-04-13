package com.example.rpg_manager.repository;

import com.example.rpg_manager.entity.Personagem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
    @Override
    @EntityGraph(attributePaths = "itensMagicos")
    List<Personagem> findAll();

    @EntityGraph(attributePaths = "itensMagicos")
    Optional<Personagem> findById(Long id);
}
