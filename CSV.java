import java.io.*;

public class CSV extends FileConverter{

    @Override
    public void generate(String[][] data, boolean header){
        super.generate(data, header);

        bufferedWriter = null;
        try {
            fileWriter = new FileWriter("data.csv");
            bufferedWriter = new BufferedWriter(fileWriter);

            for (int x = 0; x < data.length; x++){

                for (int y = 0; y < data[x].length; y++){
                    bufferedWriter.write(data[x][y]);
                    if ((y != data[x].length-1))
                        bufferedWriter.write(",");
                    else
                        bufferedWriter.newLine();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                bufferedWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void convertToXML(File file, boolean header){
        String[][] data = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            int rowCounter = 0;
            int colCounter = 0;
            String testRow = "";

            while ((testRow = bufferedReader.readLine()) != null){
                rowCounter++;
                int counter = testRow.split(",").length;
                if (counter > colCounter)
                    colCounter = counter;
            }
            bufferedReader.close();

            data = new String[rowCounter][colCounter];
            String row = "";
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            for (int x = 0; (row = bufferedReader.readLine()) !=null; x++){
                String[] rowData = row.split(",");

                for (int y = 0; y < rowData.length; y++){
                    data[x][y] = rowData[y];
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
                fileReader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        XML xml = new XML();
        xml.generate(data, header);
    }
}
