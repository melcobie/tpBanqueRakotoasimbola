/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanque.jsf;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
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

    public void loadCompte() {
        this.compte = gestionnaire.getCompteById(id);
    }

    public String editCompte() {
        try {
            if (this.gestionnaire.checkExistingName(compte)) {
                Util.messageErreur("Ce nom est déjà utilisé !", "Ce nom est déjà utilisé !", "form:nom");
                return null;
            }
            gestionnaire.update(compte);
            Util.addFlashInfoMessage("Le compte numero " + compte.getId() + " a été modifié");
            return "listeComptes?faces-redirect=true";
        } catch (EJBException ex) {
            Throwable cause = ex.getCause();
            if (cause != null) {
                if (cause instanceof OptimisticLockException) {
                    Util.messageErreur("Le compte " + compte.getNom()
                            + " a été modifié ou supprimé par un autre utilisateur !");
                } else { // Afficher le message de ex si la cause n'est pas une OptimisticLockException
                    Util.messageErreur(cause.getMessage());
                }
            } else { // Pas de cause attachée à l'EJBException
                Util.messageErreur(ex.getMessage());
            }
            return null; // pour rester sur la page s'il y a une exception
        }
    }

}
