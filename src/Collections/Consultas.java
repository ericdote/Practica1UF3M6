/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collections;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

/**
 *
 * @author Eric
 */
public class Consultas {

    Collection coll;
    ConfigConexion conf = new ConfigConexion();

    public Consultas() {
        this.coll = conf.conexion();
    }

    public void nomColActu() {
        try {
            System.out.println("El nom de la col·leció actual es: " + coll.getName());
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void nomPare() {
        try {
            System.out.println("El nom del pare es: " + coll.getParentCollection().getName());
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] listarColection() {
        String[] lista = null;
        try {
            lista = coll.listChildCollections();
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public void crearCollecions(String nom) {
        Service [] serveis;  
        CollectionManagementService cms = null;
        try {
            serveis = coll.getServices();
            for (int i = 0; i < serveis.length; i++) {
                if(serveis[i].getName().equals("CollectionManagementService")){
                    cms = (CollectionManagementService) serveis[i];
                }
            }
            cms.createCollection(nom);
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eliminarCollecions(String nom){
        CollectionManagementService cms = null;
        
    }
}
