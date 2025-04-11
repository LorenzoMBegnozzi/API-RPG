package com.example.rpg_manager.service;

import com.example.rpg_manager.entity.Personagem;
import com.example.rpg_manager.repository.PersonagemRepository;
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
        return personagemRepository.save(personagem);
    }

    public void deletar(Long id) {
        personagemRepository.deleteById(id);
    }
}
