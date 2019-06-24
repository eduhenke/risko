/*
 * Copyright (C) 2019 henke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package risko;

import java.awt.Color;
import java.util.List;

/**
 *
 * @author henke
 */
public class Player {
    Repository<String, Region> states = new Repository();
    Color color;
    String name;
    int freeSoldiers;
    
    Player(String name, Color color) {
        this.color = color;
        this.name = name;
        this.freeSoldiers = 20;
    }
    
    public int allocatedSoldiers() {
        int total = 0;
        for (Region reg: this.states.values()) {
            total += reg.soldiers;
        }
        return total;
    }

    public String getName() {
        return this.name;
    }
    
    public int totalSoldiers() {
        return this.allocatedSoldiers() + freeSoldiers;
    }
    
    public void addRegion(Region reg) {
        this.states.put(reg.name, reg);
        reg.owner = this;
    }
    
    public void attack(Region from, Region to) {
        System.out.println("Attacking from " + from.name + " to " + to.name);
    }
}
