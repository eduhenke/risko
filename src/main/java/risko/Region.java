/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risko;

public class Region extends java.awt.Polygon {
    String name;
    
    Region(String name, int[] xs, int[] ys, int n) {
        super(xs, ys, n);
        this.name = name;
    }

}
