package com.example.rpg_manager.repository;

import com.example.rpg_manager.entity.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
}
