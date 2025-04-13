package com.example.rpg_manager.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemMagico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do item não pode estar em branco")
    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoItem tipo;

    @Min(value = 0, message = "A força não pode ser negativa")
    @Max(value = 10, message = "A força não pode ser maior que 10")
    private Integer forca;

    @Min(value = 0, message = "A defesa não pode ser negativa")
    @Max(value = 10, message = "A defesa não pode ser maior que 10")
    private Integer defesa;

    @ManyToOne
    @JoinColumn(name = "personagem_id")
    @JsonIgnore // Ignora a serialização do personagem
    private Personagem personagem;

    @PrePersist
    @PreUpdate
    private void validarItem() {
        if (tipo == TipoItem.ARMA && defesa != 0) {
            throw new IllegalArgumentException("Uma Arma deve ter defesa igual a zero.");
        }
        if (tipo == TipoItem.ARMADURA && forca != 0) {
            throw new IllegalArgumentException("Uma Armadura deve ter força igual a zero.");
        }
        if (forca == 0 && defesa == 0) {
            throw new IllegalArgumentException("Um Item Mágico não pode ter força e defesa iguais a zero.");
        }
    }
}
