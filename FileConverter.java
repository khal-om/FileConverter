import javax.sql.rowset.spi.XmlWriter;
import java.io.*;

public class FileConverter {

    protected BufferedWriter bufferedWriter;
    protected BufferedReader bufferedReader;
    protected FileWriter fileWriter;
    protected FileReader fileReader;
    protected XmlWriter xmlWriter;
    protected FileOutputStream fileOutputStream;

    public void generate(String[][] data, boolean header) {

    }
}