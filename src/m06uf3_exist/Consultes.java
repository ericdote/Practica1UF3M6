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

    public void afegirAtribut(String atributo, String valor) {
        try {
            xqe = con.createExpression();
            String xq = "update insert attribute " + atributo + " {'" + valor + "'} into doc('/plantas/plantes.xml')//PLANT";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void afegirEtiqueta(String etiqueta, String valor, String zona) {
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/plantas/plantes.xml')//PLANT where every $a in $b/ZONE satisfies ($a='" + zona + "') return update insert <" + etiqueta.toUpperCase() + "> {'" + valor + "'} </" + etiqueta.toUpperCase() + "> into $b";
            xqe.executeCommand(xq);

        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modificarPreuNode(String codigo, String precio) {
        try {
            xqe = con.createExpression();
            String xq = "update value doc('/plantas/plantes.xml')//libro[@codigo='" + codigo + "']/preu '" + precio + "'";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void eliminarLlibre(String codigo) {

        try {
            xqe = con.createExpression();
            String xq = "update delete doc('/plantas/plantes.xml')//libro[@codigo='" + codigo + "']";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void eliminarEtiqueta(String etiqueta) {
        try {
            xqe = con.createExpression();
            String xq = "update delete doc('/plantas/plantes.xml')//libro/" + etiqueta;
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void eliminarAtribut(String atributo) {
        try {
            xqe = con.createExpression();
            String xq = "update delete doc('/plantas/plantes.xml')//libro/@" + atributo;
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

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

    public void eliminarDolar() {
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/plantas/plantes.xml')//PLANT/PRICE return update value $b with substring($b,2)";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            Logger.getLogger(Consultes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
    
    public void modificarPreu (String nom){
        
    }  

}
