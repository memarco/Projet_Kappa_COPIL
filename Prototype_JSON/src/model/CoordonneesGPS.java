package model;

public class CoordonneesGPS {
	private static final CoordonneesGPS adresseBoutique = new CoordonneesGPS(41.235, 2.3354);
	
	public static double getDistanceBoutique(CoordonneesGPS adresseLivraison) {
		return adresseBoutique.distance(adresseLivraison);
	}
	
	
	
	private final double lat, lng;

	public CoordonneesGPS(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}
	
	public double distance(CoordonneesGPS adresse) {
		// Very inaccurate version. Look up the "Haversine formula" for the accurate version.
		return Math.sqrt(Math.pow(lat-adresse.lat, 2) + Math.pow(lng - adresse.lng, 2));
	}

	@Override
	public String toString() {
		return "CoordonneesGPS [lat=" + lat + ", lng=" + lng + "]";
	}
}
