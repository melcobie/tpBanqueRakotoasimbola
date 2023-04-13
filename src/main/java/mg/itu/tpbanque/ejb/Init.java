/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tpbanque.ejb;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.util.ArrayList;
import java.util.List;
import mg.itu.tpbanque.entities.CompteBancaire;


/**
 *
 * @author Mel
 */
@Singleton
@Startup
public class Init {
    
    @EJB
    GestionnaireCompte gestionnaireCompte;
    
    public Init(){
        
    }
    
    @PostConstruct
    public void init(){
        this.initCompte();
    }
    
    public void initCompte() {
        List<CompteBancaire> comptes = this.gestionnaireCompte.getAllComptes();
        if(!comptes.isEmpty())
            return;
        
        List<CompteBancaire> initialComptes = new ArrayList<>(4);
        initialComptes.add(new CompteBancaire("John Lennon", 150000));
        initialComptes.add(new CompteBancaire("Paul McCartney", 950000));
        initialComptes.add(new CompteBancaire("Ringo Starr", 20000));
        initialComptes.add(new CompteBancaire("Georges Harrisson", 100000));
        
        for(CompteBancaire compte: initialComptes){
            this.gestionnaireCompte.creerCompte(compte);
        }
    }
}
