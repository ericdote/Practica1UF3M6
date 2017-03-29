/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collections;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;

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
    
    public void nomColActu(){
        try {
            System.out.println("El nom de la col·leció actual es: " + coll.getName());
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
