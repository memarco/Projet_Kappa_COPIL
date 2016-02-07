package model;

public class Colis {
	private int longueur;
	private int largeur;
	private int hauteur;
	private double poids;
	private boolean estSolide;
	
	public Colis(int longueur, int largeur, int hauteur, double poids, boolean estSolide) {
		super();
		this.longueur = longueur;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.poids = poids;
		this.estSolide = estSolide;
	}

	public int getLongueur() {
		return longueur;
	}

	public void setLongueur(int longueur) {
		this.longueur = longueur;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public double getPoids() {
		return poids;
	}

	public void setPoids(double poids) {
		this.poids = poids;
	}

	public boolean isEstSolide() {
		return estSolide;
	}

	public void setEstSolide(boolean estSolide) {
		this.estSolide = estSolide;
	}

	@Override
	public String toString() {
		return "Colis [longueur=" + longueur + ", largeur=" + largeur
				+ ", hauteur=" + hauteur + ", poids=" + poids + ", estSolide="
				+ estSolide + "]";
	}
}
