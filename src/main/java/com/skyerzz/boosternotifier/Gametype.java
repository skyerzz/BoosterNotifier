package com.skyerzz.boosternotifier;

/**
 * Created by sky on 28-6-2016.
 */
public enum Gametype {
    ARCADE("Arcade"),
    ARENA_BRAWL("Arena"),
    BLITZ("Blitz"),
    COPS_AND_CRIMS("CvC"),
    CRAZY_WALLS("C walls"),
    MEGA_WALLS("M Walls"),
    PAINTBALL("Paintball"),
    QUAKECRAFT("Quake"),
    SKYWARS("Skywars"),
    SMASH_HEROES("Smash"),
    SPEED_UHC("S UHC"),
    TURBO_KART_RACERS("TKR"),
    TNT_GAMES("TNT"),
    UHC("UHC"),
    VAMPIREZ("Vampz"),
    WALLS("Walls"),
    WARLORDS("Warlords"),
    ERROR("Error");

    String displayName;

    Gametype(String displayName)
    {
        this.displayName = displayName;
    }
}
