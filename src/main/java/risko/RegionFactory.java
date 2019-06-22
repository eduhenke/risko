package risko;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;

class FeatureNotPolygon extends Exception {
    FeatureNotPolygon() {
        super("inner geometry is not a polygon");
    }
}

class MoreThanOneRegion extends Exception {
    MoreThanOneRegion() {
        super("polygon has more than one region");
    }
}

class InvalidGeoFile extends Exception {
    InvalidGeoFile(String fileName) {
        super(fileName + " is not a feature collection");
    }
}

public class RegionFactory {
    private double centerLat, centerLng, scale;
    private int deltaX, deltaY;
    public FeatureCollection features;
    RegionFactory(String fileName, double centerLat, double centerLng, double scale, int deltaX, int deltaY) throws FileNotFoundException, IOException, InvalidGeoFile {
        InputStream inputStream = new FileInputStream(fileName);
        GeoJsonObject object = new ObjectMapper().readValue(inputStream, GeoJsonObject.class);
        if (!(object instanceof FeatureCollection)) {
            throw new InvalidGeoFile(fileName);
        }
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.features = (FeatureCollection) object;
        this.centerLat = centerLat;
        this.centerLng = centerLng;
        this.scale = scale;
    }
    
    public List<Region> allRegions() throws FeatureNotPolygon, MoreThanOneRegion {
        List<Region> regions = new ArrayList<>();
        for (Feature f: this.features) {
            Region reg = this.newRegion(f);
            regions.add(reg);
        }
        return regions;
    }
    
    public Region newRegion(Feature f) throws FeatureNotPolygon, MoreThanOneRegion {
        String name = f.getProperty("NM_MICRO");
        GeoJsonObject object = f.getGeometry();
        if (!(object instanceof Polygon)) {
            throw new FeatureNotPolygon();   
        }
        
        List<List<LngLatAlt>> coordsLists = ((Polygon) object).getCoordinates();
        if (coordsLists.size() != 1) {
            throw new MoreThanOneRegion();
        }
        
        List<LngLatAlt> coords = coordsLists.get(0);
        int n = coords.size();
        int[] xs = new int[n];
        int[] ys = new int[n];
        for (int i=0; i<n; i++) {
            LngLatAlt c = coords.get(i);
            int screenX = (int) (scale*(c.getLongitude() - centerLng)) + this.deltaX;
            int screenY = (int) (scale*(centerLat - c.getLatitude())) + this.deltaY;
            xs[i] = screenX;
            ys[i] = screenY;
        }
        java.awt.Polygon border = new java.awt.Polygon(xs, ys, n);
        return new Region(name, border);
    }
}
