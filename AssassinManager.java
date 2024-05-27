package AssassinManager;

import java.util.*;

public class AssassinManager {

    private AssassinNode killring;
    private AssassinNode graveyard;

    /**
     * Constructor
     * @param names the list of string names
     */
    public AssassinManager (List<String> names) {
        if (names.isEmpty()) throw new IllegalArgumentException();

        for (int i=0;i<names.size(); i++) {
            AssassinNode newNode = new AssassinNode (names.get(i));

            // 1. attach the node to the last
            AssassinNode current = killring;
            if (current == null) killring = newNode; // if the list is empty, update killring
            else {
                while (current.next != null) { // traverse down to the last node
                    current = current.next;
                }
                current.next = newNode;      // attach the new node
            }
        }
    }


    /**
     * printKillRing()
     * print the names of the people in the kill ring, one per line,
     * indented four spaces, with output of the form “<name> is stalking <name>”.
     */
    public void printKillRing() {
        AssassinNode current = this.killring;
        while (current.next != null){
            System.out.println("    " + current.name + " is stalking " + current.next.name);
            current = current.next;
        }
        System.out.println("    " + current.name + " is stalking " + killring.name);
    }

    /**
     * printGraveyard()
     * print the names of the people in the graveyard, one per line,
     * indented four spaces, with output of the form “ was killed by ”.
     * print the names in reverse kill order (most recently killed first)
     */
    public void printGraveyard() {
        AssassinNode current = this.graveyard;
        while (current != null){
            System.out.println("    " + current.name + " was killed by " + current.killer);
            current = current.next;
        }
    }

    /**
     * killRingContains(name)
     * ignore case in comparing names
     * @param name String value of player
     * @return true if the given name is in the current kill ring
     */
    public boolean killRingContains (String name) {
        AssassinNode current = this.killring;
        if (gameOver()) return name.equalsIgnoreCase((current.name));
        while (current != null) {
            if (name.equalsIgnoreCase(current.name)) return true;
            else {
                current = current.next;
            }
        }
        return false;
    }
    /**graveyardContains(name)
     * ignore case in comparing names.
     * @param name String value of player
     * @return true if the given name is in the current graveyard
     */
    public boolean graveyardContains (String name) {
        if (this.graveyard == null) return false;
        AssassinNode current = this.graveyard;
        if (name.equalsIgnoreCase(current.name)) return true;

        while (current.next != null){
            if (name.equalsIgnoreCase(current.name)) return true;
            else current = current.next;
        }
        return false;
    }

    /**
     * gameOver()
     * @return true if the game is over
     */
    public boolean gameOver() {
        return (this.killring.next == null);
    }

    /**
     * winner()
     * @return the name of last person in killRing, return null if game is not over
     */
    public String winner () {
        if (gameOver()) return this.killring.name;
        return null;
    }

    /**
     * kill(name)
     * records the killing of the person with the given name
     * transferring the person from the killRing to tard
     * throw an IllegalArgumentException if the given name is not part of the current kill ring
     * throw an IllegalStateException if the game is over
     * ignore case in comparing names.
     * @param name will be killed person of name
     */
    public void kill (String name) {

        if (gameOver()) throw new IllegalStateException("Game is over");
        if (!killRingContains(name)) throw new IllegalArgumentException();

        AssassinNode current = this.killring;
        AssassinNode killed = this.killring; //will update to graveyard

        while (current.next != null) {
            if (name.equalsIgnoreCase(current.next.name)) {
                killed = current.next;
                killed.killer = current.name;
                current.next = current.next.next;
                killed.next = this.graveyard;
                this.graveyard = killed;
            } else {
                current = current.next;
            }
        } //the case when the first person got killed
        if (this.killring.name.equalsIgnoreCase(name)){
            killed = this.killring;
            this.killring = this.killring.next;
            killed.next = this.graveyard;
            this.graveyard = killed;
            this.graveyard.killer = current.name;
        }



    }




}



