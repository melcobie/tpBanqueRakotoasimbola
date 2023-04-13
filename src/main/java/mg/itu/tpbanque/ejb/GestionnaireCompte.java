/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tpbanque.ejb;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import mg.itu.tpbanque.entities.CompteBancaire;

/**
 *
 * @author Mel
 */
@DataSourceDefinition(
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        name = "java:app/jdbc/banque",
        serverName = "localhost",
        portNumber = 3306,
        user = "root", // nom et
        password = "123456", // mot de passe que vous avez donnés lors de la création de la base de données
        databaseName = "banque",
        properties = {
            "useSSL=false",
            "allowPublicKeyRetrieval=true"
        }
)
@Stateless
public class GestionnaireCompte {

    @PersistenceContext(unitName = "banquePU")
    private EntityManager em;
    
    public boolean checkExistingName(CompteBancaire c){
        String q = "Select count(cb) from CompteBancaire cb "
                + "where cb.nom = :nom";
        Query query = em.createQuery(q);
        query.setParameter("nom", c.getNom());
        long count = (Long)query.getSingleResult();
        return count>0;
    }

    public void creerCompte(CompteBancaire c) {
        em.persist(c);
    }

    public List<CompteBancaire> getAllComptes() {
        TypedQuery<CompteBancaire> query = em.createNamedQuery("CompteBancaire.findAll", CompteBancaire.class);
        return query.getResultList();
    }
    
    public void initCompte() {
        List<CompteBancaire> comptes = this.getAllComptes();
        if(!comptes.isEmpty())
            return;
        
        List<CompteBancaire> initialComptes = new ArrayList<>(4);
        initialComptes.add(new CompteBancaire("John Lennon", 150000));
        initialComptes.add(new CompteBancaire("Paul McCartney", 950000));
        initialComptes.add(new CompteBancaire("Ringo Starr", 20000));
        initialComptes.add(new CompteBancaire("Georges Harrisson", 100000));
        
        for(CompteBancaire compte: initialComptes){
            this.creerCompte(compte);
        }
    }
    
    public void transferer(CompteBancaire source,
            CompteBancaire destinataire,
            int montant){
        source.retirer(montant);
        destinataire.deposer(montant);
        em.merge(source);
        em.merge(destinataire);
    }
    
    public void deposer(CompteBancaire c, int montant){
        c.deposer(montant);
        em.merge(c);
    }
    
    public void retirer(CompteBancaire c, int montant){
        c.retirer(montant);
        em.merge(c);
    }
    
    public CompteBancaire getCompteById(Long id){
        return em.find(CompteBancaire.class, id);
    }
}
