package com.epf.rentmanager.model;

public class Vehicle {
    private int id;
    private String constructeur;
    private String modele;
    private int nb_places;

    public Vehicle () {

    }

    public Vehicle(int id, String constructeur, String modele, int nb_places) {
        super();
    	this.id = id;
        this.constructeur = constructeur;
        this.modele = modele;
        this.nb_places = nb_places;
    }

    

    @Override
    public String toString (){
        return "Vehicule [id="+ id +", contrusteur="+ constructeur + ", modèle="+ modele +", nb_places=" + nb_places + "]";
    }


    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Vehicle( String constructeur, String model, int nb_places) {
		this.constructeur = constructeur;
		this.modele = model;
		this.nb_places = nb_places;
	}


}
