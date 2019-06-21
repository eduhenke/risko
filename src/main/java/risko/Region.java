/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risko;

import java.awt.*;
import javax.swing.JComponent;


public class Region extends JComponent {
    String name;
    Polygon border;
    Player owner;
    boolean hovered = false;
    boolean selected = false;
    
    Region(String name, int[] xs, int[] ys, int n) {
        this.border = new Polygon(xs, ys, n);
        this.name = name;
    }
    
    public void setHovered(boolean value) {
        this.hovered = value;
    }
    public void setSelected(boolean value) {
        this.selected = value;
    }
    
    @Override
    public boolean contains(Point p) {
        return this.border.contains(p);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (this.selected) {
            g2.setStroke(new BasicStroke(5));
        } else {
            g2.setStroke(new BasicStroke(1));
        }
        g.setColor(Color.BLACK);
        
        g.drawPolygon(this.border);
        Color fillColor = Color.LIGHT_GRAY;
        if (this.owner != null) {
            fillColor = this.owner.color;
        }
        if (!this.hovered) {
            fillColor = fillColor.darker();
        }
        g.setColor(fillColor);
        g.fillPolygon(this.border);
    }
    
    

}
