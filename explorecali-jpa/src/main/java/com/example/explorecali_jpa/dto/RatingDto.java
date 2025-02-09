package com.example.explorecali_jpa.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RatingDto {

    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Integer customerId;

    RatingDto(Integer score, String comment, Integer customerId){
        this.score = score;
        this.comment = comment;
        this.customerId = customerId;
    }



}