/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanque.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import mg.itu.tpbanque.ejb.GestionnaireCompte;
import mg.itu.tpbanque.entities.CompteBancaire;

/**
 *
 * @author Mel
 */
@Named(value = "listeComptes")
@ViewScoped
public class ListeComptes implements Serializable {
    private List<CompteBancaire> allComptes;
    
    @EJB
    GestionnaireCompte gestionnaire;

    /**
     * Creates a new instance of ListeComptes
     */
    public ListeComptes() {
    }
    
    public List<CompteBancaire> getAllComptes(){
        if(this.allComptes == null){
            this.allComptes = this.gestionnaire.getAllComptes();
        }
        return this.allComptes;
    }
    
    public String supprimer(CompteBancaire c){
        gestionnaire.deleteCompte(c);
        return "listeComptes";
    }
}
