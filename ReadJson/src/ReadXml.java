import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXml
{

    public static void main(String[] args) throws Exception
    {

        File xmlFile = new File( "exemplo.xml" );
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse( xmlFile );

        getRetasInfo( doc );
        getCirculosInfo( doc );
        getRetanguloInfo( doc );
        getPoligonoInfo(doc);
    }

    private static void getRootElement(Document doc)
    {
        System.out.println( doc.getDocumentElement().getNodeName() );
    }

    private static void getRetasInfo(Document doc)
    {
        System.out.println( "------------RETAS------------");
        NodeList nodeList = doc.getElementsByTagName( "Reta" );
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println( "-----------------------------");
            System.out.println( "Dados " + i);
            Node figureNode = nodeList.item( i );

            if (figureNode.getNodeType() == Node.ELEMENT_NODE) {
                Element figureElement = (Element) figureNode;

                NodeList ponto = figureElement.getElementsByTagName( "Ponto" );
                for (int j = 0; j < ponto.getLength(); j = j + 2) {
                    System.out.println( "Ponto x1: " + ponto.item( j ).getChildNodes().item( 0 ).getTextContent());
                    System.out.println( "Ponto y1: " + ponto.item( j ).getChildNodes().item( 1 ).getTextContent());
                    System.out.println( "Ponto x2: " + ponto.item( j + 1 ).getChildNodes().item( 0 ).getTextContent());
                    System.out.println( "Ponto y2: " + ponto.item( j + 1 ).getChildNodes().item( 1 ).getTextContent());
                }

                NodeList cor = figureElement.getElementsByTagName( "Cor" );
                for (int k = 0; k < cor.getLength(); k = k + 2) {
                    System.out.println( "R: " + cor.item( k ).getChildNodes().item( 0 ).getTextContent());
                    System.out.println( "G: " + cor.item( k ).getChildNodes().item( 1 ).getTextContent());
                    System.out.println( "B: " + cor.item( k ).getChildNodes().item( 2 ).getTextContent());
                }
            }
        }
        System.out.println( "-----------------------------");
    }

    private static void getCirculosInfo(Document doc)
    {
        System.out.println( "----------CÍRCULOS-----------");
        NodeList nodeList = doc.getElementsByTagName( "Circulo" );
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println( "-----------------------------");
            System.out.println( "Dados " + i);
            Node figureNode = nodeList.item( i );

            if (figureNode.getNodeType() == Node.ELEMENT_NODE) {
                Element figureElement = (Element) figureNode;

                NodeList ponto = figureElement.getElementsByTagName( "Ponto" );
                for (int j = 0; j < ponto.getLength(); j++) {
                    System.out.println( "Ponto x1: " + ponto.item( j ).getChildNodes().item( 0 ).getTextContent());
                    System.out.println( "Ponto y1: " + ponto.item( j ).getChildNodes().item( 1 ).getTextContent());
                }

                NodeList raio = figureElement.getElementsByTagName( "Raio" );
                System.out.println( "Raio: " + raio.item( 0 ).getChildNodes().item( 0 ).getTextContent());

                NodeList cor = figureElement.getElementsByTagName( "Cor" );
                for (int k = 0; k < cor.getLength(); k = k + 2) {
                    System.out.println( "R: " + cor.item( k ).getChildNodes().item( 0 ).getTextContent());
                    System.out.println( "G: " + cor.item( k ).getChildNodes().item( 1 ).getTextContent());
                    System.out.println( "B: " + cor.item( k ).getChildNodes().item( 2 ).getTextContent());
                }
            }
        }
        System.out.println( "-----------------------------");
    }

    private static void getRetanguloInfo(Document doc)
    {
        System.out.println( "---------RETÂNGULOS----------");
        NodeList nodeList = doc.getElementsByTagName( "Retangulo" );
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println( "-----------------------------");
            System.out.println( "Dados " + i);
            Node figureNode = nodeList.item( i );

            if (figureNode.getNodeType() == Node.ELEMENT_NODE) {
                Element figureElement = (Element) figureNode;

                NodeList ponto = figureElement.getElementsByTagName( "Ponto" );
                for (int j = 0; j < ponto.getLength(); j = j + 2) {
                    System.out.println( "Ponto x1: " + ponto.item( j ).getChildNodes().item( 0 ).getTextContent());
                    System.out.println( "Ponto y1: " + ponto.item( j ).getChildNodes().item( 1 ).getTextContent());
                    System.out.println( "Ponto x2: " + ponto.item( j + 1 ).getChildNodes().item( 0 ).getTextContent());
                    System.out.println( "Ponto y2: " + ponto.item( j + 1 ).getChildNodes().item( 1 ).getTextContent());
                }

                NodeList cor = figureElement.getElementsByTagName( "Cor" );
                for (int k = 0; k < cor.getLength(); k = k + 2) {
                    System.out.println( "R: " + cor.item( k ).getChildNodes().item( 0 ).getTextContent());
                    System.out.println( "G: " + cor.item( k ).getChildNodes().item( 1 ).getTextContent());
                    System.out.println( "B: " + cor.item( k ).getChildNodes().item( 2 ).getTextContent());
                }
            }
        }
        System.out.println( "-----------------------------");
    }

    private static void getPoligonoInfo(Document doc)
    {
        System.out.println( "---------POLÍGONOS----------");
        NodeList nodeList = doc.getElementsByTagName( "Poligono" );
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println( "-----------------------------");
            System.out.println( "Dados " + i);
            Node figureNode = nodeList.item( i );

            if (figureNode.getNodeType() == Node.ELEMENT_NODE) {
                Element figureElement = (Element) figureNode;

                NodeList ponto = figureElement.getElementsByTagName( "Ponto" );
                int pontos_count = ponto.getLength();
                for (int j = 0; j < ponto.getLength(); j = j + pontos_count) {
                    for (int pontos = 0; pontos < pontos_count; pontos++){
                        System.out.println( "Ponto x: " + ponto.item( pontos ).getChildNodes().item( 0 ).getTextContent());
                        System.out.println( "Ponto y: " + ponto.item( pontos ).getChildNodes().item( 1 ).getTextContent());
                    }
                }

                NodeList cor = figureElement.getElementsByTagName( "Cor" );
                for (int k = 0; k < cor.getLength(); k = k + 2) {
                    System.out.println( "R: " + cor.item( k ).getChildNodes().item( 0 ).getTextContent());
                    System.out.println( "G: " + cor.item( k ).getChildNodes().item( 1 ).getTextContent());
                    System.out.println( "B: " + cor.item( k ).getChildNodes().item( 2 ).getTextContent());
                }
            }
        }
        System.out.println( "-----------------------------");
    }

}