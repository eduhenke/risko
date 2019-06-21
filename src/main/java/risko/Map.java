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

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author henke
 */
public class Map extends JPanel {
    List<Region> regions;
    Map(List<Region> regions) {
        this.regions = regions;
        this.addMouseMotionListener(new MouseAdapter(){
            public void mouseMoved(MouseEvent evt) {
                for (Region reg: regions) {
                    if (reg.contains(evt.getPoint())) {
                        reg.setHovered();
                    } else {
                        reg.setUnhovered();
                    }
                }
                repaint();
            }
        });
    }
    
    @Override
    public void paint(Graphics g) {
        for (Region reg: this.regions) {
            reg.paintComponent(g);
        }
    }
}
