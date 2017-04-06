/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collections;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.BinaryResource;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

/**
 *
 * @author Eric
 */
public class Consultas {

    Collection coll;
    ConfigConexion conf = new ConfigConexion();
    Service[] serveis;
    CollectionManagementService cms;
    XMLResource resource;
    BinaryResource resourceBinary;
    /**
     * Metode constructor que estableix conexio amb la BBDD i busca les collections.
     */
    public Consultas() {
        this.coll = conf.conexion();
        buscarCollectionManagement();
    }
    
    /**
     * Metode que obte el nom de la coleccio actual.
     */
    public void nomColActu() {
        try {
            System.out.println("El nom de la col·leció actual es: " + coll.getName());
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Metode que obte el nom de la coleccio pare de la coleccio actual.
     */
    public void nomPare() {
        try {
            System.out.println("El nom del pare es: " + coll.getParentCollection().getName());
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Metode que lista totes les coleccions filles de l'actual.
     * @return 
     */
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
    
    public void generarRecursXML(String nom) throws XMLDBException, ParserConfigurationException, SAXException, IOException{
        resource = (XMLResource) coll.createResource(nom, XMLResource.RESOURCE_TYPE);  
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(nom));
        resource.setContentAsDOM(doc);
        coll.storeResource(resource);
    }
    
    public void obtenirRecurs(String nom){
        try {
            resource = (XMLResource) coll.getResource(nom);
            System.out.println("Nom del fitxer: " + resource.getId());
            System.out.println("Informacio dins: " + resource.getContent());
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eliminarRecurs(String nom){
        try {
            resource = (XMLResource) coll.getResource(nom);
            coll.removeResource(resource);
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void afegirRecursBinari(String nom) throws XMLDBException, ParserConfigurationException, SAXException, IOException{
        resourceBinary = (BinaryResource) coll.createResource(nom, BinaryResource.RESOURCE_TYPE);     
        resourceBinary.setContent(new File(nom));
        coll.storeResource(resourceBinary);
    }
    
    public void descarregarRecursBinar(String nom) throws XMLDBException, IOException{
        resourceBinary = (BinaryResource) coll.getResource(nom);
        byte[] contenido = (byte[]) resourceBinary.getContent();
        Path p = Paths.get(nom);
        Files.write(p, contenido);
        coll.storeResource(resourceBinary);    
    }
    
    

}
