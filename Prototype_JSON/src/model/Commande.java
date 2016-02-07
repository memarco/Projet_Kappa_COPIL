package model;

import java.util.List;

public class Commande {
	private List<Colis> colisage;
	private CoordonneesGPS adresseLivraison;
	
	public Commande(List<Colis> colisage, CoordonneesGPS adresseLivraison) {
		super();
		this.colisage = colisage;
		this.adresseLivraison = adresseLivraison;
	}

	public List<Colis> getColisage() {
		return colisage;
	}

	public void setColisage(List<Colis> colisage) {
		this.colisage = colisage;
	}

	public CoordonneesGPS getAdresseLivraison() {
		return adresseLivraison;
	}

	public void setAdresseLivraison(CoordonneesGPS adresseLivraison) {
		this.adresseLivraison = adresseLivraison;
	}

	@Override
	public String toString() {
		return "Commande [colisage=" + colisage + ", adresseLivraison="
				+ adresseLivraison + "]";
	}
}
