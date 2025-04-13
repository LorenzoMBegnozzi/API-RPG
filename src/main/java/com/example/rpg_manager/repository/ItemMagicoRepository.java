package com.example.rpg_manager.repository;

import com.example.rpg_manager.entity.ItemMagico;
import com.example.rpg_manager.entity.TipoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemMagicoRepository extends JpaRepository<ItemMagico, Long> {
    List<ItemMagico> findByTipoIn(List<TipoItem> tipos);

    List<ItemMagico> findByPersonagemId(Long personagemId);

    Optional<ItemMagico> findByPersonagemIdAndTipo(Long personagemId, TipoItem tipo);

}
