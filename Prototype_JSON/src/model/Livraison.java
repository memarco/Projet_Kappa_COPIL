package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Livraison {
	private final double distance;
	private final String typeVehicule;
	private final double prixLivraison;
	
	public Livraison(Commande c, List<Vehicule> vehiculesDisponibles) throws CourseImpossibleException {
		Collections.sort(vehiculesDisponibles, new Comparator<Vehicule>(){
			@Override
			public int compare(Vehicule v1, Vehicule v2) {
				return (v1.getPrixAuKilometre()-v2.getPrixAuKilometre() > 0)?1:-1;
			}
		});
		
		for(Vehicule vehicule : vehiculesDisponibles) {
			if(vehicule.peutContenir(c.getColisage())) {
				typeVehicule = vehicule.getNom();
				distance = CoordonneesGPS.getDistanceBoutique(c.getAdresseLivraison());
				prixLivraison = vehicule.getPrixAuKilometre() * distance;
				return;
			}
		}
		
		throw new CourseImpossibleException("Aucun véhicule capable de transporter cette commande n'a été trouvé.");
	}

	public double getDistance() {
		return distance;
	}

	public String getTypeVehicule() {
		return typeVehicule;
	}

	public double getPrixLivraison() {
		return prixLivraison;
	}

	@Override
	public String toString() {
		return "Livraison [distance=" + distance + ", typeVehicule="
				+ typeVehicule + ", prixLivraison=" + prixLivraison + "]";
	}
}
