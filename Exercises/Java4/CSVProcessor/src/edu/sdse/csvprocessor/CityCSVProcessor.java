package edu.sdse.csvprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import java.util.Comparator;
public class CityCSVProcessor {

	public static final void main(String[] args) {
		CityCSVProcessor reader = new CityCSVProcessor();
		
		File dataDirectory = new File("data/");
		File csvFile = new File(dataDirectory, "Cities.csv");
		
		HashMap<String, List<CityRecord>> cityRecords = reader.readAndProcess(csvFile);
		for (String city : cityRecords.keySet()) {

			List<CityRecord> RecordsOfCity = cityRecords.get(city);
			Integer average = CalculateAverage(RecordsOfCity);
			String range = FindRangeOfYears(RecordsOfCity);

			System.out.println(city + range + " : " + average );


		}

	}

	public static Integer CalculateAverage(List<CityRecord> recordsOfCity) {
		
		int total = 0;
		int n = 0;

		for (CityRecord record : recordsOfCity) {
			int population = record.population;
			total += population;
			n+=1;
		}

		return total/n;
	} 

	public static String FindRangeOfYears(List<CityRecord> recordsOfCity) {

		List<Integer> years = new ArrayList<Integer>();
		for (CityRecord record : recordsOfCity) {
			Integer year = record.year;
			years.add(year);
		}

		Integer size = years.size();
		years.sort(Comparator.naturalOrder());

		String minimum = Integer.toString(years.get(0));
		String maximum = Integer.toString(years.get(size-1));

		return " (" + minimum + " - " + maximum + "; " + size + " entries)";
	}

	public static class CityRecord {
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
			//return String.format("ID: %d, Year: %d, City: %s, Population: %d", id, year, city, population);
			return "ID: " + id + ", Year: " + year + ", City: " + city + ", Population: " + population;
		}

		public static CityRecord main() {
			CityRecord Record = new CityRecord(0, 0, null, 0);
			return Record;
		}

	}
	
	public HashMap<String, List<CityRecord>> readAndProcess(File file) {
		//Try with resource statement (as of Java 8)

		List<CityRecord> allRecords = new ArrayList<CityRecord>();

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
				
				
				CityRecord NewRecord = new CityRecord(id, year, city, population);
				//System.out.println(NewRecord);

				allRecords.add(NewRecord);

			}
		} catch (Exception e) {
			System.err.println("An error occurred:");
			e.printStackTrace();
		}

		HashMap<String, List<CityRecord>> recordsOfCity = new HashMap<String, List<CityRecord>>();

		for (CityRecord cityRecord : allRecords) {

			String key = cityRecord.city;
			List<CityRecord> value = new ArrayList<CityRecord>();
			
			if (recordsOfCity.containsKey(key)) {
				recordsOfCity.get(key).add(cityRecord);
			} 
			else {recordsOfCity.put(key, value);}

		}

		return recordsOfCity;
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
