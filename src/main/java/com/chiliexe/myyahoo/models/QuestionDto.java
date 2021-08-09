package com.chiliexe.myyahoo.models;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    
    @NotBlank
    private String email;
    @NotBlank
    private String accessKey;
}
