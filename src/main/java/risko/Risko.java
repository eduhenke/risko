/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risko;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class Risko extends JFrame {
    Risko(List<Region> regions) {
        super();
        int WIDTH = 1024;
        int HEIGHT = 600;
        
        this.setBounds(0, 0, WIDTH, HEIGHT);
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        Map map = new Map(regions);
        SidePanel sidePanel = new SidePanel();
        map.addRegionListener(sidePanel);
        
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(map, BorderLayout.CENTER);
        this.getContentPane().add(sidePanel, BorderLayout.EAST);
        this.setVisible(true);
        
    }
    public static void main(String[] args) throws IOException, FileNotFoundException, InvalidGeoFile, FeatureNotPolygon, MoreThanOneRegion {
        double latSC = -27.1658;
        double lngSC = -50.3504;
        RegionFactory regionFactory;
        regionFactory = new RegionFactory("/home/henke/risko/42MIE250GC_SIR.json", latSC, lngSC, 100, 450, 250);
        
        
        List<Region> regions = regionFactory.allRegions();
        List<Region> aRegions = new ArrayList();
        aRegions.add(regions.get(0));
        aRegions.add(regions.get(3));
        aRegions.add(regions.get(2));
        Player A = new Player(Color.CYAN, aRegions);
        for (Region reg: aRegions) {
            reg.owner = A;
        }
        new Risko(regions);
    }
    
}
