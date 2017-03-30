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
import org.xmldb.api.DatabaseManager;
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
    Service[] serveis;
    CollectionManagementService cms;

    public Consultas() {
        this.coll = conf.conexion();
        buscarCollectionManagement();
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
        try {
            cms.createCollection(nom);
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarCollecions(String nom) {
        try {
            cms.removeCollection(nom);
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void recursDinsDeCollection(String coleccio, String nom) throws XMLDBException {
        if (coleccio.length() != 0) {
            coll = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/" + coleccio, "admin", "");
        }
        System.out.println("Trobat el recurs: " + coll.getResource(nom).getId());
    }

    public void buscarCollectionManagement() {
        try {
            serveis = coll.getServices();
            for (Service servei : serveis) {
                if (servei.getName().equals("CollectionManagementService")) {
                    cms = (CollectionManagementService) servei;
                }
            }
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
