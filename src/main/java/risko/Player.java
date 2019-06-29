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
import java.util.Random;

/**
 *
 * @author henke
 */
public class Player {
    Repository<String, Region> states = new Repository();
    Color color;
    String name;
    int freeSoldiers;
    Random gen = new Random();
    
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

    public void fillRandomSoldiers(){

        while (freeSoldiers > 0){
            for (Region reg: this.states.values()) {
                if (freeSoldiers == 0){
                    break;
                }
                int n = freeSoldiers > 1 ? freeSoldiers / 2 : freeSoldiers;
            
                int nextSoldiers = gen.nextInt(n) + 1;
                freeSoldiers = freeSoldiers - nextSoldiers;
                reg.soldiers = reg.soldiers + nextSoldiers;
            }
        }
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
        int soldierDefenser = to.getSoldiers();
        int defenseDices = soldierDefenser > 3 ? 3 : soldierDefenser;
        
        Random defenseGen = new Random();
        int totalDefense = 0;
        for (int i = 0; i < defenseDices; i++) {
            totalDefense += defenseGen.nextInt(6)+1;
        }

        int soldierAttacker = from.getSoldiers();
        int attackerDices = soldierAttacker > 3 ? 3 : soldierAttacker - 1;
        
        Random attackGen = new Random();
        int totalAttack = 0;
        for (int i = 0; i < attackerDices; i++) {
            totalAttack += attackGen.nextInt(6)+1;
        }
        System.out.println("Attack: " + totalAttack + ". Defense: " + totalDefense);
        if (totalAttack > totalDefense) {
            System.out.println(this.name + " has won the battle against " + to.owner.name);
            try {
                this.states.takeFrom(to.owner.states, to.name);
            } catch (NotFound e) {
                System.out.println(e);
            }
        } else {
            System.out.println(this.name + " has lost the battle against " + to.owner.name);
        }
    }


}
