package com.example.rpg_manager.entity;

import com.example.rpg_manager.entity.ClassePersonagem;
import com.example.rpg_manager.entity.ItemMagico;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String nomeAventureiro;

    @Enumerated(EnumType.STRING)
    private ClassePersonagem classe;

    private int level;
    private int forca;
    private int defesa;

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL)
    private List<ItemMagico> itensMagicos;
}
