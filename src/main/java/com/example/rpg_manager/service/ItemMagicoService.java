package com.example.rpg_manager.service;

import com.example.rpg_manager.entity.ItemMagico;
import com.example.rpg_manager.entity.Personagem;
import com.example.rpg_manager.entity.TipoItem;
import com.example.rpg_manager.repository.ItemMagicoRepository;
import com.example.rpg_manager.repository.PersonagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemMagicoService {

    @Autowired
    private ItemMagicoRepository itemMagicoRepository;

    @Autowired
    private PersonagemRepository personagemRepository;

    public List<ItemMagico> listarTodos() {
        return itemMagicoRepository.findAll();
    }

    public Optional<ItemMagico> buscarPorId(Long id) {
        return itemMagicoRepository.findById(id);
    }

    public ItemMagico salvar(ItemMagico itemMagico) {
        return itemMagicoRepository.save(itemMagico);
    }

    public void deletar(Long id) {
        itemMagicoRepository.deleteById(id);
    }

    public ItemMagico adicionarItemAoPersonagem(Long personagemId, ItemMagico itemMagico) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado."));

        // Verifica se o personagem já possui um Amuleto
        if (itemMagico.getTipo() == TipoItem.AMULETO) {
            boolean jaPossuiAmuleto = personagem.getItensMagicos().stream()
                    .anyMatch(item -> item.getTipo() == TipoItem.AMULETO);

            if (jaPossuiAmuleto) {
                throw new IllegalArgumentException("O personagem já possui um Amuleto.");
            }
        }

        itemMagico.setPersonagem(personagem);
        return itemMagicoRepository.save(itemMagico);
    }
}
