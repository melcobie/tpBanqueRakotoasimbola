package mg.itu.tpbanque.jsf;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.constraints.PositiveOrZero;
import mg.itu.tpbanque.ejb.GestionnaireCompte;
import mg.itu.tpbanque.entities.CompteBancaire;
import mg.itu.tpbanque.jsf.util.Util;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
/**
 *
 * @author Mel
 */
@Named(value = "transfert")
@RequestScoped
public class Transfert {

    private Long idSource;
    private Long idDestinataire;
    @PositiveOrZero
    private int montant;

    @EJB
    GestionnaireCompte gestionnaire;

    /**
     * Creates a new instance of Transfert
     */
    public Transfert() {
    }

    public Long getIdSource() {
        return idSource;
    }

    public Long getIdDestinataire() {
        return idDestinataire;
    }

    public int getMontant() {
        return montant;
    }

    public void setIdSource(Long idSource) {
        this.idSource = idSource;
    }

    public void setIdDestinataire(Long idDestinataire) {
        this.idDestinataire = idDestinataire;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String transferer() {
        try {
            boolean erreur = false;
            CompteBancaire source = this.gestionnaire.getCompteById(idSource);
            if (source == null) {
                Util.messageErreur("Aucun compte avec cet id !", "Aucun compte avec cet id !", "form:source");
                erreur = true;
            } else {
                if (source.getSolde() < montant) { // à compléter pour le cas où le solde du compte source est insuffisant...
                    Util.messageErreur("Votre solde est insuffisant !", "Solde insuffisant", "form:montant");
                    erreur = true;
                }
            }
            CompteBancaire destinataire = this.gestionnaire.getCompteById(idDestinataire);
            if (destinataire == null) {
                Util.messageErreur("Aucun compte avec cet id !", "Aucun compte avec cet id !", "form:destinataire");
                erreur = true;
            }
            if (montant < 0) {
                Util.messageErreur("Le montant ne doit pas etre négatif !", "Montant négatif", "form:montant");
                erreur = true;
            }
            if (erreur) {
                return null;
            }
            this.gestionnaire.transferer(source, destinataire, montant);
            Util.addFlashInfoMessage("Tranfert correctement effectué : "
                    + source.getNom()
                    + " a transféré "
                    + montant
                    + " à " + destinataire.getNom());
            return "listeComptes?faces-redirect=true";
        } catch (EJBException ex) {
            Throwable cause = ex.getCause();
            if (cause != null) {
                if (cause instanceof OptimisticLockException) {
                    Util.messageErreur("Le compte " + idSource 
                            + " ou " + idDestinataire
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
