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
    boolean origin = false;
    int soldiers = 1;
    
    Region(String name, Polygon border) {
        this.border = border;
        this.name = name;
    }
    
    public void setHovered(boolean value) {
        this.hovered = value;
    }
    public void setSelected(boolean value) {
        this.selected = value;
    }

    public void setOrigin(boolean value){
        this.origin = value;
    }

    public boolean isOrigin(){
        return this.origin == true;
    }

    public boolean isSelected(){
        return this.selected == true;
    }
    
    @Override
    public boolean contains(Point p) {
        return this.border.contains(p);
    }

    public int getSoldiers(){
        return this.soldiers;
    }

    public boolean isValid(boolean selectingOrigin, String playerName){
        if ((selectingOrigin && playerName == this.owner.getName()) && this.soldiers > 1 || (!selectingOrigin && playerName != this.owner.getName())){
            return true;
        }
        return false;
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
