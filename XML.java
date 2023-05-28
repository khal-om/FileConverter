import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.dom4j.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class XML extends FileConverter{

    @Override
    public void generate(String[][] data, boolean header){
        super.generate(data, header);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement("root");
            document.appendChild(root);

            if (header){
                for (int x = 1; x < data.length; x++){
                    Element node = document.createElement("node");
                    root.appendChild(node);

                    for (int y = 0; y < data[x].length; y++){
                        Element childNode = document.createElement(data[0][y]);
                        childNode.appendChild(document.createTextNode(data[x][y]));
                        node.appendChild(childNode);
                    }
                }
            }else {
                for (int x = 0; x < data.length; x++){
                    Element node = document.createElement("node");
                    root.appendChild(node);

                    for (int y = 0; y < data[x].length; y++){
                        Element childNode = document.createElement("childnode");
                        childNode.appendChild(document.createTextNode(data[x][y]));
                        node.appendChild(childNode);
                    }
                }
            }

            DOMReader reader = new DOMReader();
            org.dom4j.Document doc = reader.read(document);
            fileOutputStream = new FileOutputStream(new File("data.xml"));
            xmlWriter = new XMLWriter(fileOutputStream, OutputFormat.createPrettyPrint());
            xmlWriter.write(doc);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                xmlWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void convertToCSV(File file){
        String[][] data = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            Element root = document.getDocumentElement();
            NodeList nodeList = root.getChildNodes();

            int rows = 0;
            int columns = 0;
            for (int x = 0; x < nodeList.getLength(); x++){
                Node node = nodeList.item(x);

                if (node.getNodeType() == Node.ELEMENT_NODE){
                    rows++;
                    columns = 0;
                    NodeList liste = node.getChildNodes();
                    for (int y = 0; y < liste.getLength(); y++){
                        Node n = liste.item(y);
                        if (n.getNodeType() == Node.ELEMENT_NODE){
                            columns++;
                        }
                    }
                }
            }

            ////////////////////////////////

            data = new String[rows][];
            int rowCounter = 0;
            for (int x = 0; x < nodeList.getLength(); x++){
                Node node = nodeList.item(x);

                if (node.getNodeType() == Node.ELEMENT_NODE){
                    NodeList liste = node.getChildNodes();
                    String[] dat = new String[columns];
                    int columnCounter = 0;

                    for (int y = 0; y < liste.getLength(); y++){
                        Node n = liste.item(y);

                        if (n.getNodeType() == Node.ELEMENT_NODE){
                            if (rowCounter == 0){
                                dat[columnCounter] == n.getNodeName();
                            }else{
                                dat[columnCounter] = n.getFirstChild().getNodeValue();
                            }
                            columnCounter
                        }
                    }
                    data[rowCounter] = dat;
                    rowCounter++;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }

        CSV csv = new CSV();
        csv.generate(data, true);

    }
}
