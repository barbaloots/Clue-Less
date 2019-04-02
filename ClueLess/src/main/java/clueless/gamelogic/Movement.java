package clueless.gamelogic;

/**
 * This class represents the movement from players and weapons
 * each turn of the game. Every turn, there is player movement
 * caused by the turn player for their character as well as player
 * movement on the other characters caused by suggestions. Weapons
 * are also moved by means of suggestion, and this class enables
 * that they move properly.
 */
public class Movement 
{
    private Location turnPlayerCurrentLocation;
    private Location destinationLocation;
    private String direction;
    private int spacesToMove;
    private boolean validMove;
}