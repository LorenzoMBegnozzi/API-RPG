package com.example.rpg_manager.service;

import com.example.rpg_manager.entity.ItemMagico;
import com.example.rpg_manager.entity.Personagem;
import com.example.rpg_manager.entity.TipoItem;
import com.example.rpg_manager.repository.PersonagemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonagemService {

    @Autowired
    private PersonagemRepository personagemRepository;

    public List<Personagem> listarTodos() {
        return personagemRepository.findAll();
    }

    public Optional<Personagem> buscarPorId(Long id) {
        return personagemRepository.findById(id);
    }

    public Personagem salvar(Personagem personagem) {
        if (personagem.getItensMagicos() != null) {
            long countAmuletos = personagem.getItensMagicos().stream()
                    .filter(item -> item.getTipo() == TipoItem.AMULETO)
                    .count();

            // Se já tiver um amuleto, lança uma exceção
            if (countAmuletos > 1) {
                throw new IllegalArgumentException("O personagem já possui um amuleto.");
            }
        }

        return personagemRepository.save(personagem);
    }

    public void deletar(Long id) {
        personagemRepository.deleteById(id);
    }

    public ItemMagico buscarAmuletoDoPersonagem(Long personagemId) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado"));

        return personagem.getItensMagicos().stream()
                .filter(item -> item.getTipo() == TipoItem.AMULETO)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Amuleto não encontrado para o personagem"));
    }

    public void atualizarNome(Long id, String novoNome) {
        Optional<Personagem> personagemOptional = personagemRepository.findById(id);
        if (personagemOptional.isPresent()) {
            Personagem personagem = personagemOptional.get();
            personagem.setNome(novoNome);
            personagemRepository.save(personagem);
        } else {
            throw new IllegalArgumentException("Personagem não encontrado");
        }
    }
}
