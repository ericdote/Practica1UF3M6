package m06uf3_exist;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQResultSequence;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class M06uf3_exist {
    
    public static void imprimirPlantas(List<Node> ns) {
        for (Node n : ns) {
            System.out.println(n.getTextContent());
        }
    }
    
    private static void imprimirPlanta(Node planta) {
        if (planta != null) {
            NamedNodeMap attributes = planta.getAttributes();
            
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println(attributes.item(i).getNodeValue());
            }
            
            System.out.println(planta.getTextContent());
        }
    }
    
    public static void main(String[] args) {
        String[] etiquetas = {"COMMON", "BOTANICAL", "ZONE", "LIGHT", "PRICE", "AVAILABILITY"};
        String[] etiTradu = {"NOMBRE", "PLANTA", "ZONA", "LUZ", "PRECIO", "DISPONIBILIDAD"};
        
        ConfigConnexio cc = new ConfigConnexio();
        Consultes cs = new Consultes(cc.getCon());

//        cs.traduirEtiquetas(etiquetas, etiTradu);
//        cs.eliminarDolar();
//        imprimirPlantas(cs.obtenirPlantes());    
//        imprimirPlanta(cs.cercarNom(JOptionPane.showInputDialog("Introdueix un nom de la planta")));
//        cs.afegirPlanta("Planta", "Planta", "HONDA", "OVSUCRO", "2.35", "05478");
//        cs.afegirAtribut("CODIGO", "1");
//        cs.afegirEtiqueta("MARCOS", "TONTO", "4");
//        imprimirPlantas(cs.obtenirPlantesPreu(1, 3));
//        imprimirPlantas(cs.obtenirPerZona("4"));
        
    }
    
}
