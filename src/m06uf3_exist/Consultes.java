/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m06uf3_exist;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;
import org.w3c.dom.Node;

/**
 *
 * @author Jorge
 */
public class Consultes {

    private final XQConnection con;
    private XQExpression xqe;
    private XQPreparedExpression xqpe;

    public Consultes(XQConnection con) {
        this.con = con;
    }
    /**
     * Obte una llista amb totes les plantes
     * @return 
     */
    public List<Node> obtenirPlantes() {
        List<Node> plantas = new ArrayList<>();
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc ('/plantas/plantes.xml')//PLANT return $b";

            XQResultSequence rs = xqe.executeQuery(xq);
            while (rs.next()) {
                plantas.add(rs.getItem().getNode());
            }
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return plantas;
    }
    /**
     * Obte una planta pel seu nom
     * @param nom
     * @return 
     */
    public Node cercarNom(String nom) {
        Node planta = null;
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/plantas/plantes.xml')"
                    + "//PLANT where every $a in $b/COMMON satisfies ($a = '" + nom + "') return $b";

            XQResultSequence rs = xqe.executeQuery(xq);
            rs.next();
            planta = rs.getItem().getNode();
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return planta;
    }
    /**
     * Afegeix una planta nova al XML a la primera posicio
     * @param common
     * @param botanical
     * @param zone
     * @param light
     * @param price
     * @param availabilitiy 
     */
    public void afegirPlanta(String common, String botanical, String zone, String light, String price, String availabilitiy) {
        try {
            xqe = con.createExpression();
            String xq = "update insert "
                    + "    <PLANT>"
                    + "        <COMMON>" + common + "</COMMON>"
                    + "        <BOTANICAL>" + botanical + "</BOTANICAL>"
                    + "        <ZONE>" + zone + "</ZONE>"
                    + "        <LIGHT>" + light + "</LIGHT>"
                    + "        <PRICE>" + price + "</PRICE>"
                    + "        <AVAILABILITY>" + availabilitiy + "</AVAILABILITY>"
                    + "    </PLANT>\n"
                    + "preceding doc('/plantas/plantes.xml')//PLANT[1]";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Afegeix un atribut amb un valor a totes les plantes
     * @param atributo
     * @param valor 
     */
    public void afegirAtribut(String atributo, String valor) {
        try {
            xqe = con.createExpression();
            String xq = "update insert attribute " + atributo + " {'" + valor + "'} into doc('/plantas/plantes.xml')//PLANT";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Afegeix una etiqueta a unes plantes, les quals es filten per la zona
     * @param etiqueta
     * @param valor
     * @param zona 
     */
    public void afegirEtiqueta(String etiqueta, String valor, String zona) {
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/plantas/plantes.xml')//PLANT where every $a in $b/ZONE satisfies ($a='" + zona + "') return update insert <" + etiqueta.toUpperCase() + "> {'" + valor + "'} </" + etiqueta.toUpperCase() + "> into $b";
            xqe.executeCommand(xq);

        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Tradueix el nom de les etiquetes, buscant primer el nom original de la etiqueta i després ho sustitueix per la seva traduccio
     * @param original
     * @param traducido 
     */
    public void traduirEtiquetas(String[] original, String[] traducido) {
        try {
            xqe = con.createExpression();
            for (int i = 0; i < original.length; i++) {
                String xq = "update rename doc('/plantas/plantes.xml')//PLANT/" + original[i] + " as '" + traducido[i] + "'";
                xqe.executeCommand(xq);
            }
        } catch (XQException e) {
        }
    }
    /**
     * Metode que busca tots els preus de les plantes i elimina el dolar de tots els preus
     */
    public void eliminarDolar() {
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/plantas/plantes.xml')//PLANT/PRICE return update value $b with substring($b,2)";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            Logger.getLogger(Consultes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
     * Obté totes les plantes per un rang de preus
     * @param preuInferior
     * @param preuSuperior
     * @return 
     */
    public List<Node> obtenirPlantesPreu(double preuInferior, double preuSuperior) {
        List<Node> plantas = new ArrayList<>();
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc ('/plantas/plantes.xml')//PLANT where every $a in $b/ZONE satisfies($a >= '"+ preuInferior + "' and $a <= '" + preuSuperior + "') return $b";

            XQResultSequence rs = xqe.executeQuery(xq);
            while (rs.next()) {
                plantas.add(rs.getItem().getNode());
            }
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return plantas;
    }
    /**
     * Obte una llista de plantes per la zona com filtre
     * @param zona
     * @return 
     */
    public List<Node> obtenirPerZona(String zona){
        List<Node> plantas = new ArrayList<>();
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc ('/plantas/plantes.xml')//PLANT where every $a in $b/ZONE satisfies($a = '"+ zona +"') return $b";

            XQResultSequence rs = xqe.executeQuery(xq);
            while (rs.next()) {
                plantas.add(rs.getItem().getNode());
            }
        } catch (XQException ex) {
            
            System.out.println(ex.getMessage());
        }
        return plantas;
    }
    /**
     * Modifica el preu d'una planta pel seu nom
     * @param nom
     * @param preu 
     */
    public void modificarPreu (String nom, double preu){
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/plantas/plantes.xml')//PLANT where every $a in $b/COMMON satisfies($a = '" + nom + "') return update value $b/PRICE with " + preu;
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            Logger.getLogger(Consultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    /**
     * Elimina una planta pel seu nom
     * @param nom 
     */
    public void eliminarPerNom(String nom){
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/plantas/plantes.xml')//PLANT where every $a in $b/COMMON satisfies($a = '" + nom + "') return update delete $b";
            xqe.executeCommand(xq);
        } catch (XQException e) {
        }
    }

}
