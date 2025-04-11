package com.example.rpg_manager.controller;

import com.example.rpg_manager.entity.ItemMagico;
import com.example.rpg_manager.service.ItemMagicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itens-magicos")
public class ItemMagicoController {

    @Autowired
    private ItemMagicoService itemMagicoService;

    @GetMapping
    public List<ItemMagico> listarTodos() {
        return itemMagicoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<ItemMagico> buscarPorId(@PathVariable Long id) {
        return itemMagicoService.buscarPorId(id);
    }

    @PostMapping
    public ItemMagico criar(@Valid @RequestBody ItemMagico itemMagico) {
        return itemMagicoService.salvar(itemMagico);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        itemMagicoService.deletar(id);
    }

    @PostMapping("/adicionar/{personagemId}")
    public ItemMagico adicionarItemAoPersonagem(@PathVariable Long personagemId, @Valid @RequestBody ItemMagico itemMagico) {
        return itemMagicoService.adicionarItemAoPersonagem(personagemId, itemMagico);
    }
}
