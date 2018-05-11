package edu.esprit.smartInnov.gui.main;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.sun.prism.PhongMaterial.MapType;

import edu.esprit.smartInnov.entites.Local;
import edu.esprit.smartInnov.services.LocalService;
import edu.esprit.smartInnov.vues.VProSante;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ProSanteDetailsController implements MapComponentInitializedListener{

	@FXML
	private Label nom;
	@FXML
	private AnchorPane pane;
	private GoogleMapView mapView;
	private GoogleMap map;
	
	private LocalService localService;
	private Local local;
	public void initComponents(VProSante vProSante) {
		nom.setText(vProSante.getNom() +" "+ vProSante.getPrenom());
		localService = new LocalService();
		local = localService.getLocalByVProSante(vProSante);
		 mapView = new GoogleMapView();
		 mapView.addMapInializedListener(this);
		 mapView.setLayoutX(14);
			mapView.setLayoutY(35);
			mapView.setPrefHeight(706);
			mapView.setPrefWidth(990);
		 pane.getChildren().add(mapView);
	}
	@Override
	public void mapInitialized() {
		 MapOptions mapOptions = new MapOptions();

		    mapOptions.center(new LatLong(local.getLatitude(), local.getLongitude()))
		            .mapType(MapTypeIdEnum.ROADMAP)
		            .overviewMapControl(false)
		            .panControl(false)
		            .rotateControl(false)
		            .scaleControl(false)
		            .streetViewControl(false)
		            .zoomControl(false)
		            .zoom(12);

		    map = mapView.createMap(mapOptions);

		    //Add a marker to the map
		    MarkerOptions markerOptions = new MarkerOptions();

		    markerOptions.position( new LatLong(local.getLatitude(), local.getLongitude()) )
		                .visible(Boolean.TRUE)
		                .title("My Marker");

		    Marker marker = new Marker( markerOptions );

		    map.addMarker(marker);
	}
}
