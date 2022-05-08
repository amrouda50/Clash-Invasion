package com.mygdx.claninvasion.view.utils;

import org.javatuples.Septet;

public class PlayerUIData {
    public final String level;
    public final String name;
    public final String wealth;
    public final String towers;
    public final String soldiers;
    public final String minings;
    public final String health;

    public PlayerUIData(
            int level,
            String name,
            int wealth,
            int towers,
            int soldiers,
            int minings,
            int health
            ) {
        this.level = String.valueOf(level);
        this.name = name;
        String asterisk = wealth >= 1000 ? "k" :  "";
        this.wealth = (wealth >= 1000 ?  String.format("%.1f",wealth / 1000f) : wealth) + asterisk;
        this.towers = String.valueOf(towers);
        this.soldiers = String.valueOf(soldiers);
        this.minings = String.valueOf(minings);
        this.health = String.valueOf(health);
    }

    public String get(int index) {
        if (index < 0 || index > 7) {
            throw new RuntimeException("Please provide valid index for the player data");
        }

        Septet<String, String, String, String, String, String, String> septet = new Septet<>(
                level,
                name,
                wealth,
                towers,
                soldiers,
                minings,
                health
        );
        return (String) septet.getValue(index);
    }
}
