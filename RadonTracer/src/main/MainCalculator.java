package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainCalculator {

	private long adp = 0;
	private int aiv = 0;
	private int afv = 0;
	private int eiv = 0;
	private int efv = 0;
	private int dpr = 0;

	private final String FILENAME = "src/data/results.txt";
	private final String RESULTS = "src/data/RadonTracerResults.csv";
	private final String csvFileDeprem = "src/data/deprem.csv";
	private final String csvFileToprak = "src/data/toprak.csv";

	static String line = "";
	static String cvsSplitBy = ",";

	List<Earthquake> earthquakes = new ArrayList<Earthquake>();
	List<Soil> soils = new ArrayList<Soil>();

	protected MainCalculator() {
		// Exists only to defeat instantiation.
	}

	public String AnomalyCalculate(int adpi, int aivi, int afvi, int eivi, int efvi, int dpri) {
		
		//System.out.println("Reading CSV Files");

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
		//System.out.println("1218: Reading-" + earthquakes.get(1217).geteDate() + " observe: " + earthquakes.get(1217).geteMe());

		//System.out.println("Anomaly Calculate Method Called");

		String result = "";
		double num_anomaly = 0;
		double num_anomalywithEarthquake = 0;

		this.adp = adpi;
		this.aiv = aivi;
		this.afv = afvi;
		this.eiv = eivi;
		this.efv = efvi;
		this.dpr = dpri;

		// print out initials
		
		try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(FILENAME))) {
			String content = "Radon Tracer Results\n\nDate-Time of Calculation: " + LocalDateTime.now().toString() + 
					"\n\nValues: " + adp + ", " + aiv + ", " + afv + ", " +
					eiv + ", " + efv + ", " + dpr +  "\n\n";
			bw1.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		boolean soilsCheck = true;
		for (int is = 1; (is < soils.size() && soilsCheck); is++) {
		//for (int is = 3720; (is < 3730 && soilsCheck); is++) {
			if (soils.get(is).getsDate().getTime() - soils.get(0).getsDate().getTime() < adp * 60 * 60 * 1000) {
				// do nothing, because we need to increase "is" for adp period
			} else if (soils.get(soils.size()-1).getsDate().getTime() - soils.get(is).getsDate().getTime() < adp * 60 * 60 * 1000) {
				// adp period is over, so break the main loop
				//System.out.println("Deneme: " + soils.get(is).getsDate());
				soilsCheck = false;
			}
			else {
				// now we are in the adp period, first calculate the mean
				double soil_mean = 0;
				int adpCounter = 0;
				for (int adpc = 0; adpc < soils.size(); adpc++) {
					if (soils.get(is).getsDate().getTime() - soils.get(adpc).getsDate().getTime() <= adp * 60 * 60 * 1000 && 
							soils.get(adpc).getsDate().getTime() - soils.get(is).getsDate().getTime() <= adp * 60 * 60 * 1000) {
						
						// not considering the me values less than 75.
						if (soils.get(adpc).getsObserv() > 75) {
							soil_mean = soil_mean + soils.get(adpc).getsObserv();
							adpCounter++;
						} else {
							// do nothing
						}
						
					} else {
						// do nothing
					}
				}
				soil_mean = soil_mean / adpCounter;

				// check whether observation is an anomaly
				if (soils.get(is).getsObserv() < soil_mean * (1 + ((double) aiv / 100.0)) || soils.get(is).getsObserv() > soil_mean * (1 + ((double) afv / 100.0))) {
					// there is no anomaly in this observation, so do nothing
				}
				else {
					// there is an anomaly
					num_anomaly++;
					
					try (BufferedWriter bw2 = new BufferedWriter(new FileWriter(FILENAME, true))) {
						String content = "Anomaly # " + num_anomaly + "\t\t\tAnomaly at index: " + is + "\t\tValue: " + soils.get(is).getsObserv() + 
								 "\t\tTime: " + soils.get(is).getsDate() + "\n";
						bw2.write(content);
					} catch (IOException e) {
						e.printStackTrace();
					}

					// find the earthquakes related to anomaly
					boolean mainEarthquakeLoop = true;
					for (int eq = 0; (eq < earthquakes.size() && mainEarthquakeLoop); eq++) {
						//System.out.println(is + "First#" + eq + " date: " + earthquakes.get(eq).geteDate() + " Me: " + earthquakes.get(eq).geteMe());
						if (earthquakes.get(eq).geteDate().getTime() - soils.get(is).getsDate().getTime() <= 0) {
							//System.out.println(is + "Wait#" + eq + " me: " + earthquakes.get(eq).geteDate() + " Me: " + earthquakes.get(eq).geteMe());
							// do nothing because the earthquake was happened before anomaly
						} else {
							if(earthquakes.get(eq).geteDate().getTime() - soils.get(is).getsDate().getTime() > dpr * 60 * 60 * 1000) {
								// break the loop because detection period has passed
								//System.out.println(is + "Leave#" + eq + " me: " + earthquakes.get(eq).geteDate() + " Me: " + earthquakes.get(eq).geteMe());
								mainEarthquakeLoop = false;
							}
							else {
								if (earthquakes.get(eq).geteMe() > (double) eiv && earthquakes.get(eq).geteMe() < (double) efv) {
									// there is an earthquake, break the loop
									mainEarthquakeLoop = false;
									num_anomalywithEarthquake++;
									//System.out.println(is + "Found#" + eq + " me: " + earthquakes.get(eq).geteDate() + " Me: " + earthquakes.get(eq).geteMe());
									//System.out.println("Anomaly Current Time: " + soils.get(is).getsDate());
									//System.out.println("Ea Time             : " + earthquakes.get(eq).geteDate());
									
									try (BufferedWriter bw3 = new BufferedWriter(new FileWriter(FILENAME, true))) {
										String content = "E.quake # " + num_anomalywithEarthquake + "\t\t\tAnomaly at index: " + eq +
												"\t\tValue: " + earthquakes.get(eq).geteMe() + "\t\t\tTime: " + earthquakes.get(eq).geteDate() + "\n\n";
										bw3.write(content);
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else {
									// do nothing
								}
							}
						}
					}
				}
			}
		}

		System.out.println("Anomalies with earthquake: " + num_anomalywithEarthquake);
		System.out.println("Anomaly #                : " + num_anomaly);

		DecimalFormat decimalFormat = new DecimalFormat("#.000");
		if (num_anomalywithEarthquake == 0.0) {
			result =  "0.0 / " +  num_anomaly + " = % 0.0";
		} else {
			double number = (num_anomalywithEarthquake * 100) / num_anomaly;
			result = num_anomalywithEarthquake + " / " + num_anomaly + " = % " + decimalFormat.format(number);
		}
		System.out.println("Values: " + adp + ", " + aiv + ", " + afv + ", " +
				eiv + ", " + efv + ", " + dpr);
		System.out.println("Result: " + result);
		System.out.println("Calculation is done!\n");
		
		try (BufferedWriter bw4 = new BufferedWriter(new FileWriter(RESULTS, true))) {
			String content = adp + ";" + aiv + ";" + afv + ";" + eiv + ";" + efv + ";" + dpr + ";" +
					num_anomalywithEarthquake + ";" + num_anomaly + ";" + 
					decimalFormat.format((num_anomalywithEarthquake * 100) / num_anomaly) + "\n";
			bw4.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return adp + "-" + aiv + "-" + afv + "-" + eiv + "-" + efv + "-" + dpr + " -> " + result;
	}
}