package com.quickpixelstudio.literalura.services;

public class CharacterLength {
    public static String limitLength(String character, int maxLength){
        if(character.length() <= maxLength){
            return character;
        } else {
            return character.substring(0, maxLength);
        }
    }
}