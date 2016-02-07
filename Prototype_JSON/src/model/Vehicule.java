package model;

import java.util.List;

public class Vehicule {
	private final String nom;
	private final int longueur;
	private final int largeur;
	private final int hauteur;
	private final double poids;
	private final double prixAuKilometre;
	
	public boolean peutContenir(List<Colis> colisage) {
		// Le vrai algorithme est beaucoup plus compliqué. Ici se trouve une version simplifiée pour la démonstration.
		for(Colis colis : colisage) {
			if(colis.getLargeur() > largeur)
				return false;
			if(colis.getLongueur() > longueur)
				return false;
			if(colis.getHauteur() > hauteur)
				return false;
			if(colis.getPoids() > poids)
				return false;
		}
		
		return true;
	}
	
	public Vehicule(String nom, int longueur, int largeur, int hauteur, double poids, double prixAuKilometre) {
		super();
		this.nom = nom;
		this.longueur = longueur;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.poids = poids;
		this.prixAuKilometre = prixAuKilometre;
	}
	
	public String getNom() {
		return nom;
	}

	public int getLongueur() {
		return longueur;
	}

	public int getLargeur() {
		return largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public double getPoids() {
		return poids;
	}
	
	public double getPrixAuKilometre() {
		return prixAuKilometre;
	}

	@Override
	public String toString() {
		return "Vehicule [nom=" + nom + ", longueur=" + longueur + ", largeur="
				+ largeur + ", hauteur=" + hauteur + ", poids=" + poids
				+ ", prixAuKilometre=" + prixAuKilometre + "]";
	}
}
