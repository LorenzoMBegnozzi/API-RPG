package com.example.rpg_manager.service;

import com.example.rpg_manager.dto.ItemMagicoDTO;
import com.example.rpg_manager.entity.ItemMagico;
import com.example.rpg_manager.entity.Personagem;
import com.example.rpg_manager.entity.TipoItem;
import com.example.rpg_manager.repository.ItemMagicoRepository;
import com.example.rpg_manager.repository.PersonagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ItemMagicoService {

    @Autowired
    private ItemMagicoRepository itemMagicoRepository;

    @Autowired
    private PersonagemRepository personagemRepository;

    private static final Logger logger = LoggerFactory.getLogger(ItemMagicoService.class);

    public List<ItemMagico> listarTodos() {
        return itemMagicoRepository.findAll();
    }

    public Optional<ItemMagico> buscarPorId(Long id) {
        return itemMagicoRepository.findById(id);
    }

    public ResponseEntity<String> salvarComMensagem(ItemMagico itemMagico) {
        try {
            StringBuilder mensagem = new StringBuilder("Item salvo com sucesso.");

            if (itemMagico.getTipo() == TipoItem.ARMA) {
                if (itemMagico.getDefesa() == null || itemMagico.getDefesa() != 0) {
                    itemMagico.setDefesa(0);
                    mensagem = new StringBuilder("Item do tipo ARMA não pode ter defesa. Defesa ajustada para 0.");
                    logger.info(mensagem.toString());
                }
            }

            if (itemMagico.getTipo() == TipoItem.ARMADURA) {
                if (itemMagico.getForca() == null || itemMagico.getForca() != 0) {
                    itemMagico.setForca(0);
                    mensagem = new StringBuilder("Item do tipo ARMADURA não pode ter força. Força ajustada para 0.");
                    logger.info(mensagem.toString());
                }
            }

            itemMagicoRepository.save(itemMagico);
            return ResponseEntity.ok(mensagem.toString());

        } catch (Exception e) {
            logger.error("Erro ao salvar item mágico: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar item mágico: " + e.getMessage());
        }
    }

    public void deletar(Long id) {
        itemMagicoRepository.deleteById(id);
    }

    public ItemMagico adicionarItemAoPersonagem(Long personagemId, ItemMagico itemMagico) {
        try {
            Personagem personagem = personagemRepository.findById(personagemId)
                    .orElseThrow(() -> new IllegalArgumentException("Personagem não encontrado."));

            if (itemMagico.getId() != null) {
                Optional<ItemMagico> itemExistente = itemMagicoRepository.findById(itemMagico.getId());
                if (itemExistente.isPresent()) {
                    ItemMagico itemAtualizado = itemExistente.get();
                    itemAtualizado.setPersonagem(personagem);
                    return itemMagicoRepository.save(itemAtualizado);
                }
            }

            itemMagico.setPersonagem(personagem);
            return itemMagicoRepository.save(itemMagico);

        } catch (Exception e) {
            logger.error("Erro ao adicionar item mágico ao personagem: {}", e.getMessage());
            throw e;
        }
    }
}
