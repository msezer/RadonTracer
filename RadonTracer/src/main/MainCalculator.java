package main;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainCalculator {

	static String csvFileDeprem = "src/data/deprem.csv";
	static String csvFileToprak = "src/data/toprak.csv";

	static String line = "";
	static String cvsSplitBy = ",";

	static List<Earthquake> earthquakes = new ArrayList<Earthquake>();
	static List<Soil> soils = new ArrayList<Soil>();

	private static MainCalculator instance = null;

	protected MainCalculator() {
		// Exists only to defeat instantiation.
	}

	public static MainCalculator getInstance() {
		if (instance == null) {
			instance = new MainCalculator();
			ReadAllCsv();
		}
		return instance;
	}

	private static void ReadAllCsv() {

		// read earthquakes
		try (BufferedReader br = new BufferedReader(new FileReader(csvFileDeprem))) {

			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] earthquake = line.split(cvsSplitBy);
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				Date earthquaketime;
				earthquaketime = ft.parse(earthquake[0]);
				earthquakes.add(new Earthquake(earthquaketime, Double.valueOf(earthquake[3])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Class: MainCalculator; EarthquakeTime Unparseable");
		}

		// read soils
		try (BufferedReader br = new BufferedReader(new FileReader(csvFileToprak))) {

			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] soil = line.split(cvsSplitBy);
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				Date soiltime;
				soiltime = ft.parse(soil[0]);
				soils.add(new Soil(soiltime, Double.valueOf(soil[1])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Class: MainCalculator; SoilTime Unparseable");
		}
	}

	public double result = 0;
	@SuppressWarnings("unused")
	private int adp = 0;
	@SuppressWarnings("unused")
	private int aiv = 0;
	@SuppressWarnings("unused")
	private int afv = 0;
	@SuppressWarnings("unused")
	private double eiv = 0;
	@SuppressWarnings("unused")
	private double efv = 0;
	@SuppressWarnings("unused")
	private int dpr = 0;

	public double AnomalyCalculate(int adpi, int aivi, int afvi, double eivi, double efvi, int dpri) {

		this.adp = adpi;
		this.aiv = aivi;
		this.afv = afvi;
		this.eiv = eivi;
		this.efv = efvi;
		this.dpr = dpri;
		return result;
	}
}
