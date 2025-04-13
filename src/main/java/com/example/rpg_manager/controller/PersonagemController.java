package com.example.rpg_manager.controller;

import com.example.rpg_manager.dto.AtualizarNomeAventureiroDTO;
import com.example.rpg_manager.entity.ItemMagico;
import com.example.rpg_manager.entity.Personagem;
import com.example.rpg_manager.entity.TipoItem;
import com.example.rpg_manager.repository.ItemMagicoRepository;
import com.example.rpg_manager.service.PersonagemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personagens")
public class PersonagemController {

    @Autowired
    private ItemMagicoRepository itemMagicoRepository;

    @Autowired
    private PersonagemService personagemService;

    @GetMapping
    public List<Personagem> listarTodos() {
        return personagemService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personagem> buscarPorId(@PathVariable Long id) {
        Optional<Personagem> personagemOpt = personagemService.buscarPorId(id);
        return personagemOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Personagem personagem) {
        int totalAtributos = personagem.getForca() + personagem.getDefesa();
        if (totalAtributos > 10) {
            return ResponseEntity.badRequest().body("A soma de força e defesa não pode ser maior que 10.");
        }

        Personagem personagemSalvo = personagemService.salvar(personagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(personagemSalvo);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        personagemService.deletar(id);
    }

    @PostMapping("/{id}/amuleto")
    public ResponseEntity<String> adicionarAmuleto(@PathVariable Long id, @RequestBody ItemMagico amuleto) {
        try {
            Personagem personagem = personagemService.buscarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado"));

            if (personagem.getItensMagicos() == null) {
                personagem.setItensMagicos(new ArrayList<>());
            }

            // Verifica se o personagem já tem um amuleto
            long countAmuletos = personagem.getItensMagicos().stream()
                    .filter(item -> item.getTipo() == TipoItem.AMULETO)
                    .count();

            if (countAmuletos > 0) {
                return ResponseEntity.badRequest().body("Este personagem já possui um amuleto.");
            }

            // Adiciona o amuleto ao personagem
            amuleto.setPersonagem(personagem);
            personagem.getItensMagicos().add(amuleto);
            personagemService.salvar(personagem);

            return ResponseEntity.ok("Amuleto adicionado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao adicionar amuleto: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/nome-aventureiro")
    public ResponseEntity<Personagem> atualizarNomeAventureiro(@PathVariable Long id, @RequestBody AtualizarNomeAventureiroDTO dto) {
        Optional<Personagem> personagemOptional = personagemService.buscarPorId(id);
        if (personagemOptional.isPresent()) {
            Personagem personagem = personagemOptional.get();
            personagem.setNomeAventureiro(dto.getNomeAventureiro());
            personagemService.salvar(personagem);
            return ResponseEntity.ok(personagem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{personagemId}/itens/{itemId}")
    public ResponseEntity<String> removerItemDoPersonagem(@PathVariable Long personagemId, @PathVariable Long itemId) {
        Optional<Personagem> personagemOptional = personagemService.buscarPorId(personagemId);

        if (personagemOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personagem não encontrado");
        }

        Personagem personagem = personagemOptional.get();

        boolean itemRemovido = personagem.getItensMagicos().removeIf(item -> item.getId().equals(itemId));

        if (!itemRemovido) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado no personagem");
        }

        itemMagicoRepository.deleteById(itemId);

        personagemService.salvar(personagem);  // Atualiza personagem sem o item
        return ResponseEntity.ok("Item removido com sucesso");
    }

}
