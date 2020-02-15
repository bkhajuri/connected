package com.service;


import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CityLinksService {
	List allCityLinks = null;
	String originalFromCity;
	public boolean ifConnectionFound = false;
	
	public CityLinksService() {
		this.allCityLinks = getCities("src/cities2.csv");
	}
	
	public CityLinksService(String fileLocation) {
		this.allCityLinks = getCities(fileLocation);
	}
	
    private List getCities(String fileLocation) {

        String csvFile = fileLocation;
        String line = "";
        String cvsSplitBy = ",";
        List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
        Map<String, String> list = null;
        int i=0;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] cities = line.split(cvsSplitBy);

                //System.out.println(country[0] +"  "  + country[1]);
                list = new HashMap<String, String>();
                if(cities[0].trim().length()>0 && cities[1].trim().length() >0)
                {	
                	list.put(cities[0], cities[1]);
                	lists.add(list);
                }
            }
            
            System.out.println("Print City List "+lists.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lists;
    }
   
    public static void main(String args[]) throws Exception{
    	CityLinksService citilinks = new CityLinksService();
    
    	List fromList = new ArrayList<>();
    	Map fromCityMap = new HashMap();
    	fromCityMap.put("Houston", "Houston");
    	fromList.add(fromCityMap);
    	citilinks.getCityLink(0, 0, fromList, "Indianapolis");
    	System.out.println("Found the connection "+citilinks.ifConnectionFound);
    }
    
    public String getCitiesConnection(String fromCity, String toCity) throws Exception{
    	
    	if(fromCity != null && fromCity.trim().length() >0 && toCity != null &&  toCity.trim().length() >0)
    	{	
	    	this.ifConnectionFound = false;
	    	List fromList = new ArrayList<>();
	    	Map fromCityMap = new HashMap();
	    	fromCityMap.put(fromCity, fromCity);
	    	fromList.add(fromCityMap);
	    	this.getCityLink(0, 0, fromList, toCity);
	    	if(this.ifConnectionFound) return "Links Exists";
	    	else return "Links Does Not Exists";
    	} else throw new Exception("The origin and or destination is empty");
    }
    
    private int getCityLink(int finalListStartIndex, int finalListEndIndex, List fromCity, String toCity) throws Exception{
    	
    	int returnFinal = 0;
    	// Build the Initial of From Cities from the 
    	if(finalListStartIndex == 0 && finalListEndIndex == 0) {
    		finalListEndIndex = fromCity.size();
    		Iterator iterator = fromCity.iterator();
    		while(iterator.hasNext()){
    			for (Map.Entry<String, String> entry : ((Map<String,String>)iterator.next()).entrySet()) {
    				originalFromCity = entry.getKey();
	    			List<Map<String, String>> tempCityToCityList;
	    			tempCityToCityList = (List<Map<String, String>>) allCityLinks.stream()
	    	    	        .filter(map -> ((Map<String, String>) map).containsKey(entry.getKey()))
	    	    	        .collect(Collectors.toList());
	    			  	System.out.println("The first List "+tempCityToCityList);
	    			  	List<Map<String, String>> toCityList = null;
	    	    		toCityList = (List<Map<String, String>>) fromCity.stream()
	    	        	        .filter(map -> ((Map<String, String>) map).containsValue(toCity))
	    	        	        .collect(Collectors.toList());
	    	    		if(toCityList.size() > 0) {
	    	    			this.ifConnectionFound = true;
	    	    			return returnFinal;
	    	    		} else {
	    			  	getCityLink(finalListStartIndex, finalListEndIndex, tempCityToCityList, toCity);  	
	    	    		}
    				}
    			};
    	} else {
    		finalListEndIndex = fromCity.size();
    		Iterator iterator = fromCity.iterator();
    		int nextStartIndex=0;
    		List<Map<String, String>> tempCityToCityList=null;
    		List<Map<String, String>> tempHoldCity = new ArrayList<Map<String, String>>();
    		while(iterator.hasNext()){
    			nextStartIndex++;
    			for (Map.Entry<String, String> entry : ((Map<String,String>)iterator.next()).entrySet()) {
    		
    				if(!originalFromCity.equals(entry.getValue()) && nextStartIndex > finalListStartIndex)
    				{
	    				tempCityToCityList = (List<Map<String, String>>) allCityLinks.stream()
	    	    	        .filter(map -> ((Map<String, String>) map).containsKey(entry.getValue()))
	    	    	        .collect(Collectors.toList());
	    				tempHoldCity.addAll(tempCityToCityList);
	    				
    					System.out.println("Else fromCity "+tempHoldCity.toString());
    					}
	    			}
    			};
    			
    		fromCity.addAll(tempHoldCity);
    		
    		fromCity =  (List<Map<String, String>>)fromCity.stream().distinct().collect(Collectors.toList());
    		
    		
    		List<Map<String, String>> toCityList = null;
    		toCityList = (List<Map<String, String>>) fromCity.stream()
        	        .filter(map -> ((Map<String, String>) map).containsValue(toCity))
        	        .collect(Collectors.toList());
    		
    		if(toCityList.size() > 0) {
    			this.ifConnectionFound = true;
    			return returnFinal;
    		}
    		if(finalListEndIndex == fromCity.size()) {
    			returnFinal++;
    			return returnFinal;
    		} else getCityLink(nextStartIndex, fromCity.size(), fromCity, toCity);
    			
    		return returnFinal;
    	}
    	return returnFinal;
    	
    }
   
}