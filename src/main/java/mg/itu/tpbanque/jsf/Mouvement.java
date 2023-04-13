/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanque.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import mg.itu.tpbanque.ejb.GestionnaireCompte;
import mg.itu.tpbanque.entities.CompteBancaire;
import mg.itu.tpbanque.jsf.util.Util;

/**
 *
 * @author Mel
 */
@Named(value = "mouvement")
@ViewScoped
public class Mouvement implements Serializable {
    private Long id;
    private String typeMouvement;
    @PositiveOrZero
    private int montant;
    private CompteBancaire compte;
    
    @EJB
    GestionnaireCompte gestionnaire;

    /**
     * Creates a new instance of Mouvement
     */
    public Mouvement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public CompteBancaire getCompte() {
        return compte;
    }
    
    public void loadCompte(){
        this.compte = gestionnaire.getCompteById(id);
    }
    
    public String enregistrer(){
        if(typeMouvement.equals("ajout")){
            gestionnaire.deposer(compte, montant);
        }
        else if(typeMouvement.equals("retrait")){
            if(montant > compte.getSolde()){
                Util.messageErreur("Votre solde est insuffisant !", "Solde insuffisant", "form:montant");
                return null;
            }
            gestionnaire.retirer(compte, montant);
        }
        Util.addFlashInfoMessage(typeMouvement + " enregistré sur le compte de " + compte.getNom());
        return "listeComptes?faces-redirect=true";
    }
    
}
