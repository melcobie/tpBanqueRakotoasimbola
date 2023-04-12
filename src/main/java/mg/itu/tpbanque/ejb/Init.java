/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tpbanque.ejb;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;


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
        this.gestionnaireCompte.initCompte();
    }
}
