package com.smallgolemduo.togethersee.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genre {

    ETC("Etc"),
    ACTION("Action"),
    SF_FANTASY("Fantasy"),
    ROMANCE_COMEDY("Romance_Comedy"),
    HORROR_THRILLER("Horror_Thriller"),
    DRAMA_DOCUMENTARY("Drama_Documentary");

    private final String movieGenre;

}