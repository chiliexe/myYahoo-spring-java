package com.chiliexe.myyahoo.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 220, nullable = false, unique = true)
    @NotBlank(message = "Campo Título deve ser preenchido")
    private String title;

    @Column(length = 255, nullable = false)
    private String slug;

    @Column(nullable = false)
    @NotBlank(message = "Campo email deve ser preenchido")
    @Email
    private String email;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Campo Descrição deve ser preenchido")
    private String description;

    @Column(nullable = false)
    private String accessKey;

    @OneToMany(mappedBy = "question")
    private List<Reply> replies;

    private boolean active;
}
