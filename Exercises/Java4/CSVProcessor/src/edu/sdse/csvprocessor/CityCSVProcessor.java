package edu.sdse.csvprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class CityCSVProcessor {

	public static final void main(String[] args) {
		CityCSVProcessor reader = new CityCSVProcessor();
		
		File dataDirectory = new File("data/");
		File csvFile = new File(dataDirectory, "Cities.csv");
		
		reader.readAndProcess(csvFile);
	}

	public class CityRecord {
		int id;
		int year;
		String city;
		int population;

		public CityRecord(int id, int year, String city, int population) {
			this.id = id;
			this.year = year;
			this.city = city;
			this.population = population;
		}

		// override!
		public String toString() {
			return "ID: " + id + ", Year: " + year + ", City: " + city + ", Population: " + population;
		}

	}
	
	public void readAndProcess(File file) {
		//Try with resource statement (as of Java 8)
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			//Discard header row
			br.readLine();
			
			String line;
			
			while ((line = br.readLine()) != null) {
				// Parse each line
				String[] rawValues = line.split(",");
				
				int id = convertToInt(rawValues[0]);
				int year = convertToInt(rawValues[1]);
				String city = convertToString(rawValues[2]);
				int population = convertToInt(rawValues[3]);
				
				// Uncomment to print entries!
				//System.out.println("id: " + id + ", year: " + year + ", city: " + city + ", population: " + population);
				
				CityRecord NewRecord = new CityRecord(id, year, city, population);
				System.out.println(NewRecord);


			}
		} catch (Exception e) {
			System.err.println("An error occurred:");
			e.printStackTrace();
		}
	}
	
	private String cleanRawValue(String rawValue) {
		return rawValue.trim();
	}
	
	private int convertToInt(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		return Integer.parseInt(rawValue);
	}
	
	private String convertToString(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		
		if (rawValue.startsWith("\"") && rawValue.endsWith("\"")) {
			return rawValue.substring(1, rawValue.length() - 1);
		}
		
		return rawValue;
	}
	
}
