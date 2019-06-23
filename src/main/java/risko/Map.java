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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import javax.swing.JPanel;

/**
 *
 * @author henke
 */
public class Map extends JPanel {
    List<Region> regions;
    Region hoveredRegion;
    Region pastHoveredRegion;
    Region selectedRegion;
    Region selectedPastRegion;
    Region selectedOriginRegion;

    List<RegionListener> listeners = new ArrayList();
    
    Map(List<Region> regions) {
        this.regions = regions;
        this.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent evt) {
                boolean hasSelected = false;
                for (Region reg: regions) {
                    if (reg.contains(evt.getPoint())) {
                        reg.setHovered(true);
                        hasSelected = true;
                        hoveredRegion = reg;
                        for (RegionListener lst: listeners) {
                            lst.onRegionHover(reg);
                        }
                    } else {
                        reg.setHovered(false);
                    }
                }
                if (!hasSelected) {
                    for (RegionListener lst: listeners) {
                        lst.onRegionUnhover();
                    }
                }
                repaint();
            }
        });
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent evt) {
                boolean hasSelected = false;                
                for (Region reg: regions) {
                    if (reg.contains(evt.getPoint())) {
                        
                        reg.setSelected(true);
                        hasSelected = true;
                        if (selectedPastRegion != null && selectedRegion != null) {
                            System.out.println("[map] past: " + selectedPastRegion.name + " | sel: " + selectedRegion.name);
                        }
                        selectedPastRegion = selectedRegion;
                       
                        selectedRegion = reg;

                        if (selectedOriginRegion == null){
                            selectedOriginRegion = selectedRegion;
                            reg.setOrigin(true);
                        }
                        for (RegionListener lst: listeners) {
                            lst.onRegionSelect(reg);
                        }
                    } else if (!reg.isOrigin()) {
                        reg.setSelected(false);
                    }
                }
                if (!hasSelected) {
                    for (RegionListener lst: listeners) {
                        lst.onRegionDeselect();
                    }
                }
                repaint();
            }
        });
    }
    
    public void addRegionListener(RegionListener lst) {
        this.listeners.add(lst);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Region selected = this.regions.stream().filter(r -> r.selected).findFirst().orElse(null);
        for (Region reg: this.regions) {
            reg.paintComponent(g);
        }
        if (selected != null) {
            selected.paintComponent(g);
        }
    }
    
    public static interface RegionListener {
        public void onRegionSelect(Region reg);
        public void onRegionDeselect();
        public void onRegionHover(Region reg);
        public void onRegionUnhover();
    }
}
