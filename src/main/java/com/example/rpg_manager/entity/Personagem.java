package com.example.rpg_manager.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)  // Inclui o personagem nas respostas
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

    @JsonProperty("forcaTotal")
    public int getForcaTotal() {
        return this.forca + itensMagicos.stream().mapToInt(ItemMagico::getForca).sum();
    }

    @JsonProperty("defesaTotal")
    public int getDefesaTotal() {
        return this.defesa + itensMagicos.stream().mapToInt(ItemMagico::getDefesa).sum();
    }

    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemMagico> itensMagicos = new ArrayList<>();
}
