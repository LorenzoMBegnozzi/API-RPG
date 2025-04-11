package com.example.rpg_manager.controller;

import com.example.rpg_manager.entity.Personagem;
import com.example.rpg_manager.service.PersonagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personagens")
public class PersonagemController {

    @Autowired
    private PersonagemService personagemService;

    @GetMapping
    public List<Personagem> listarTodos() {
        return personagemService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Personagem> buscarPorId(@PathVariable Long id) {
        return personagemService.buscarPorId(id);
    }

    @PostMapping
    public Personagem criar(@RequestBody Personagem personagem) {
        return personagemService.salvar(personagem);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        personagemService.deletar(id);
    }
}
