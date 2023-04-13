/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanque.jsf;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.PositiveOrZero;
import mg.itu.tpbanque.ejb.GestionnaireCompte;
import mg.itu.tpbanque.entities.CompteBancaire;
import mg.itu.tpbanque.jsf.util.Util;

/**
 *
 * @author Mel
 */
@Named(value = "ajout")
@RequestScoped
public class AjoutCompte {

    private String nom;
    @PositiveOrZero
    private int montant;

    @EJB
    GestionnaireCompte gestionnaire;

    /**
     * Creates a new instance of AjoutCompte
     */
    public AjoutCompte() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String creer() {
        CompteBancaire compte = new CompteBancaire(nom, montant);
        if (this.gestionnaire.checkExistingName(compte)) {
            Util.messageErreur("Ce nom est déjà utilisé !", "Ce nom est déjà utilisé !", "form:nom");
            return null;
        }
        this.gestionnaire.creerCompte(compte);
        Util.addFlashInfoMessage("Nouveau compte créé : "
                + compte.getNom() + " ("
                + compte.getSolde() + ")");
        return "listeComptes?faces-redirect=true";
    }

}
