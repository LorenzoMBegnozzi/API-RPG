package com.example.rpg_manager.dto;

import com.example.rpg_manager.entity.Personagem;

public class PersonagemResumoDTO {
    private Long id;
    private String nome;
    private String nomeAventureiro;
    private String classe;
    private int level;
    private int forca;
    private int defesa;

    public PersonagemResumoDTO(Personagem personagem) {
        this.id = personagem.getId();
        this.nome = personagem.getNome();
        this.nomeAventureiro = personagem.getNomeAventureiro();
        this.classe = personagem.getClasse().name();
        this.level = personagem.getLevel();
        this.forca = personagem.getForca();
        this.defesa = personagem.getDefesa();
    }

    // Getters e Setters (ou use Lombok com @Getter @Setter @AllArgsConstructor)
}
