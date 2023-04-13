/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanque.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import mg.itu.tpbanque.ejb.GestionnaireCompte;
import mg.itu.tpbanque.entities.CompteBancaire;
import mg.itu.tpbanque.jsf.util.Util;

/**
 *
 * @author Mel
 */
@Named(value = "edit")
@ViewScoped
public class Edit implements Serializable {
    private Long id;
    private CompteBancaire compte;
    
    @EJB
    GestionnaireCompte gestionnaire;
    
    /**
     * Creates a new instance of Edit
     */
    public Edit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompteBancaire getCompte() {
        return compte;
    }
    
    public void loadCompte(){
        this.compte = gestionnaire.getCompteById(id);
    }
    
    public String editCompte(){
        if (this.gestionnaire.checkExistingName(compte)) {
            Util.messageErreur("Ce nom est déjà utilisé !", "Ce nom est déjà utilisé !", "form:nom");
            return null;
        }
        gestionnaire.update(compte);
        Util.addFlashInfoMessage("Le compte numero " + compte.getId() + " a été modifié");
        return "listeComptes?faces-redirect=true";
    }
    
}
