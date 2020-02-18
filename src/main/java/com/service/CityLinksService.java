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
    	citilinks.getCityLink(false,"Houston", 0, 0, fromList, "Indianapolis");
    	
    }
    
    public String getCitiesConnection(String fromCity, String toCity) throws Exception{
    	String originalFromCity = "";
    	boolean connectionNotFound = true;
    	int counter =0;
    	if(fromCity != null && fromCity.trim().length() >0 && toCity != null &&  toCity.trim().length() >0)
    	{	
	    	List fromList = new ArrayList<>();
	    	Map fromCityMap = new HashMap();
	    	fromCityMap.put(fromCity, fromCity);
	    	fromList.add(fromCityMap);
	    	connectionNotFound = this.getCityLink(connectionNotFound, originalFromCity, 0, 0, fromList, toCity);
	    	System.out.println("connectionNotFound 2 "+connectionNotFound);
	    	counter++;
	    	if(!connectionNotFound) return "Links Exists";
	    	else return "Links Does Not Exists";
    	} else throw new Exception("The origin and or destination is empty");
    }
    
    private boolean getCityLink(boolean connectionNotFound, String originalFromCity, int finalListStartIndex, int finalListEndIndex, List fromCity, String toCity) throws Exception{
    	
    	System.out.println("connectionNotFound 1 "+connectionNotFound);
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
	    			  	
	    			  	if(tempCityToCityList.size() ==0 )
	    			  		return connectionNotFound;
	    			  	else {
		    			  	List<Map<String, String>> toCityList = null;
		    	    		toCityList = (List<Map<String, String>>) fromCity.stream()
		    	        	        .filter(map -> ((Map<String, String>) map).containsValue(toCity))
		    	        	        .collect(Collectors.toList());
		    	    		
		    	    		if(toCityList.size() > 0) {
		    	    			connectionNotFound = false;
		    	    			return connectionNotFound;
		    	    		} else {
		    			  	if(getCityLink(connectionNotFound, originalFromCity, finalListStartIndex, finalListEndIndex, tempCityToCityList, toCity)) {
		    			  		return true;
		    			  	}else{
		    			  		return false;
		    			  	}	
	    	    		}
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
    			connectionNotFound = false;
    			return connectionNotFound;
    		}
    		if(finalListEndIndex == fromCity.size()) {
    			return connectionNotFound;
    		} else if(getCityLink(connectionNotFound, originalFromCity, nextStartIndex, fromCity.size(), fromCity, toCity)) {
    			return true;
    		}else return false;
    			
    		
    	}

    	return connectionNotFound;
    }
   
}