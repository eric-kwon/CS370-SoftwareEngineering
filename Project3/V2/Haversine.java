import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Haversine {

	public static int zip1;
	public static int zip2;
	public static double lat_zip1;
	public static double lon_zip1;
	public static double lat_zip2;
	public static double lon_zip2;
	
	public static void setZip1 (int zip) {
		zip1 = zip;
	}
	
	public static void setZip2 (int zip) {
		zip2 = zip;
	}
	
	public static void setLatLon () {
		String zipCodeFile = "zipcode/zipcodes.txt";
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader(zipCodeFile);
			br = new BufferedReader(fr);
			
			String line;
			
			while ((line = br.readLine()) != null) {
				String[] data = new String[3];
				if (line.contains(String.valueOf(zip1))) {
					data = line.split(",");
					lat_zip1 = Double.valueOf(data[1]);
					lon_zip1 = Double.valueOf(data[2]);
				}
				if (line.contains(String.valueOf(zip2))) {
					data = line.split(",");
					lat_zip2 = Double.valueOf(data[1]);
					lon_zip2 = Double.valueOf(data[2]);
				}
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
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static double getDistance() {
		double radius = 6371;
		double deltaLat = Math.toRadians(lat_zip2 - lat_zip1);
		double deltaLon = Math.toRadians(lon_zip2 - lon_zip1);
		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(Math.toRadians(lat_zip1)) * Math.cos(Math.toRadians(lat_zip2)) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return radius * c;
		
	}
}
