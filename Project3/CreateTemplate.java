import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CreateTemplate {

    public static void createTemp(String id, String title, String img, String price, String seller, String auction, String query, int zip1, int zip2) {

        String readFile = "template/template.html";
        String outFile = "assets/" + id + "/print.html";
        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;

        Haversine calcDist = new Haversine();
        calcDist.setZip1(zip1);
        calcDist.setZip2(zip2);
        calcDist.setLatLon();
        double distance = calcDist.getDistance();

        try {
            fr = new FileReader(readFile);
            br = new BufferedReader(fr);
            fw = new FileWriter(outFile);
            bw = new BufferedWriter(fw);

            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains("replace-id")) bw.write(line.replace("replace-id-here",id));
                else if (line.contains("replace-title")) bw.write(line.replace("replace-title-here",title));
                else if (line.contains("replace-price")) bw.write(line.replace("replace-price-here",price));
                else if (line.contains("replace-seller")) bw.write(line.replace("replace-seller-here",seller));
                else if (line.contains("replace-auction")) bw.write(line.replace("replace-auction-here",auction));
                else if (line.contains("replace-query")) bw.write(line.replace("replace-query-here",query));
                else if (line.contains("replace-zip1")) bw.write(line.replace("replace-zip1-here",Integer.toString(zip1)));
                else if (line.contains("replace-zip2")) bw.write(line.replace("replace-zip2-here",Integer.toString(zip2)));
                else if (line.contains("replace-distance")) bw.write(line.replace("replace-distance-here",Double.toString(distance)));
                else bw.write(line);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
