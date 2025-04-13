package com.example.rpg_manager.dto;

import com.example.rpg_manager.entity.ItemMagico;

public class ItemMagicoDTO {
    private Long id;
    private String nome;
    private String tipo;
    private int forca;
    private int defesa;
    private PersonagemResumoDTO personagem;

    public ItemMagicoDTO(ItemMagico item) {
        this.id = item.getId();
        this.nome = item.getNome();
        this.tipo = item.getTipo().name();
        this.forca = item.getForca();
        this.defesa = item.getDefesa();
        this.personagem = new PersonagemResumoDTO(item.getPersonagem());
    }

}
