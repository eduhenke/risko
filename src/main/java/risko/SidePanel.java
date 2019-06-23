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
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author henke
 */
public class SidePanel extends JPanel implements Map.RegionListener {
    Map map;
    private JLabel selectedRegion = new JLabel(noText);
    private JLabel selectedRegionLabel = new JLabel("Região Selecionada:");
    private JLabel hoveredRegion = new JLabel(noText);
    private JLabel hoveredRegionLabel = new JLabel("Região:");
    private JLabel currPlayer = new JLabel(noText);
    private JLabel currPlayerLabel = new JLabel("É a vez de:");
    private JLabel fromRegion = new JLabel(noText);
    private JLabel fromRegionLabel = new JLabel("De:");
    private JLabel toRegion = new JLabel(noText);
    private JLabel toRegionLabel = new JLabel("Para:");
    private JComboBox actionBox = new JComboBox<Action>();
    private JLabel actionLabel = new JLabel("Ação:");
    public JButton submitBtn = new JButton("Realizar ação");
    private JLabel msg = new JLabel();
    private static final String noText = "---";
    Player currentPlayer;
    
    SidePanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(selectedRegionLabel);
        this.add(selectedRegion);
        this.add(hoveredRegionLabel);
        this.add(hoveredRegion);
        this.add(currPlayerLabel);
        this.add(currPlayer);
        this.add(actionLabel);
        for (Action action: Action.values()) {
            actionBox.addItem(action);
        }
        this.add(actionBox);
        this.add(fromRegionLabel);
        this.add(fromRegion);
        this.add(toRegionLabel);
        this.add(toRegion);
        this.add(msg);
        this.add(submitBtn);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
        this.currPlayer.setText(player.name);   
    }
    
    public void setMessage(String text) {
        this.msg.setText(text);
    }
    
    public Action getAction() {
        return (Action) this.actionBox.getSelectedItem();
    }
    
    @Override
    public void onRegionSelect(Region reg) {
        this.selectedRegion.setText(reg.name + " de " + reg.owner.name);
        System.out.println(this.fromRegion.getText());
        if (this.fromRegion.getText().equals(noText)) {
            this.fromRegion.setText(reg.name);
        } else {
            
            this.toRegion.setText(reg.name);
        }
    }
    @Override
    public void onRegionDeselect() {
        this.selectedRegion.setText(noText);
    }
    @Override
    public void onRegionHover(Region reg) {
        this.hoveredRegion.setText(reg.name + " de " + reg.owner.name);
    }
    @Override
    public void onRegionUnhover() {
        this.hoveredRegion.setText(noText);
    }
    
}
