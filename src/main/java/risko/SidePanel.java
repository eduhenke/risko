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

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author henke
 */
public class SidePanel extends JPanel implements Map.RegionListener {
    Map map;
    JLabel selectedRegion = new JLabel(noRegionText);
    JLabel selectedRegionLabel = new JLabel("Nome da Região selecionada:");
    JLabel hoveredRegion = new JLabel(noRegionText);
    JLabel hoveredRegionLabel = new JLabel("Nome da Região:");
    private static String noRegionText = "---";
    
    SidePanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBounds(0, 0, 100, 100);
        this.add(selectedRegionLabel);
        this.add(selectedRegion);
        this.add(hoveredRegionLabel);
        this.add(hoveredRegion);
    }
    
    public void onRegionSelect(Region reg) {
        this.selectedRegion.setText(reg.name);
    }
    public void onRegionDeselect() {
        this.selectedRegion.setText(noRegionText);
    }
    public void onRegionHover(Region reg) {
        this.hoveredRegion.setText(reg.name);
    }
    public void onRegionUnhover() {
        this.hoveredRegion.setText(noRegionText);
    }
    
}
