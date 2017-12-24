package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainCalculator {
	
	
	
	static String csvFileDeprem = "src/data/deprem.csv";
	static String csvFileToprak = "src/data/toprak.csv";
	
	// TO DO : delete deneme
	static String csvFileDeneme = "src/data/deneme.csv";
	
    static String line = "";
    static String cvsSplitBy = ",";
    
    
    
    

    private static MainCalculator instance = null;

    protected MainCalculator() {
       // Exists only to defeat instantiation.
    }
    public static MainCalculator getInstance() {
       if(instance == null) {
          instance = new MainCalculator();
          ReadAllCsv();
       }
       return instance;
    }
	
	private static void ReadAllCsv() {

		// read earthquakes
		try (BufferedReader br = new BufferedReader(new FileReader(csvFileDeneme))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] earthquake = line.split(cvsSplitBy);
                //System.out.println(earthquake[0] + "-" + earthquake[1]);
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd-HH-mm-ss"); 
                Date t;
                	t = ft.parse(earthquake[0]);
                	System.out.println(earthquake[0] + " as is : " + t);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Class: MainCalculator; Unparseable");
		}
		
		// read observations
		/*
		try (BufferedReader bre = new BufferedReader(new FileReader(csvFileToprak))) {
            while ((line = bre.readLine()) != null) {
                // use comma as separator
                String[] observation = line.split(cvsSplitBy);
                //System.out.println(observation[0] + "-" + observation[1]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        */
		
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
