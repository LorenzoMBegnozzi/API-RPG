package com.example.rpg_manager.controller;

import com.example.rpg_manager.entity.ItemMagico;
import com.example.rpg_manager.entity.TipoItem;
import com.example.rpg_manager.repository.ItemMagicoRepository;
import com.example.rpg_manager.service.ItemMagicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itens-magicos")
public class ItemMagicoController {

    @Autowired
    private ItemMagicoService itemMagicoService;

    @Autowired
    private ItemMagicoRepository itemMagicoRepository;

    @GetMapping
    public List<ItemMagico> listarTodos() {
        return itemMagicoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<ItemMagico> buscarPorId(@PathVariable Long id) {
        return itemMagicoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<String> criarItemMagico(@RequestBody ItemMagico itemMagico) {
        return itemMagicoService.salvarComMensagem(itemMagico);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        itemMagicoService.deletar(id);
    }

    @PostMapping("/adicionar/{personagemId}")
    public ResponseEntity<ItemMagico> adicionarItemAoPersonagem(@PathVariable Long personagemId, @Valid @RequestBody ItemMagico itemMagico) {
        try {
            ItemMagico item = itemMagicoService.adicionarItemAoPersonagem(personagemId, itemMagico);
            return ResponseEntity.status(HttpStatus.CREATED).body(item);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/armas-armaduras")
    public List<ItemMagico> listarArmasEArmaduras() {
        return itemMagicoRepository.findByTipoIn(List.of(TipoItem.ARMA, TipoItem.ARMADURA));
    }

    @GetMapping("/personagem/{personagemId}")
    public List<ItemMagico> listarItensPorPersonagem(@PathVariable Long personagemId) {
        return itemMagicoRepository.findByPersonagemId(personagemId);
    }

    @GetMapping("/personagem/{personagemId}/amuleto")
    public ResponseEntity<ItemMagico> buscarAmuletoDoPersonagem(@PathVariable Long personagemId) {
        return itemMagicoRepository.findByPersonagemIdAndTipo(personagemId, TipoItem.AMULETO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
