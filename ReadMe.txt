This is to find the link between the source and destination cities. The Default list of cities is maintained in 
src/cities2.csv.

To test the 2 cities 
1)  http://localhost:8080/api/v1/connected/Dallas/Washington

If the City is  not connected it will return 
Links Does Not Exists

2) localhost:8080/api/v1/connected/Toronto/Dallas
If the City link exists
Links Exists

The logic works as follows:

Toronto,Buffalo
Buffalo,NewYork
Buffalo,Boston
Boston,Buffalo
Washington,Las Vegas
Las Vegas,Washington
Toronto,NewYork
NewYork,Boston
Toronto,Boston
Philadelphia,Buffalo
Philadelphia,Boston
NewYork,Washington
Washington,Boston
Boston,Philadelphia
Philadelphia,Miami
Toronto,Detroit
Miami,Houston
Houston,Dallas
Dallas,Indianapolis

Example from Toronto to Indianapolis
===============1================
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
===============1================
  
  In the First pass when 
  
  finalListStartIndex == 0 && finalListEndIndex == 0 
  
  the code creates a list of toCity from the from City

tempCityToCityList = (List<Map<String, String>>) allCityLinks.stream()
	    	    	        .filter(map -> ((Map<String, String>) map).containsKey(entry.getKey()))
	    	    	        .collect(Collectors.toList());
	    			  	System.out.println("The first List "+tempCityToCityList);
Generates the list :

[{Toronto=Buffalo}, {Toronto=NewYork}, {Toronto=Boston}, {Toronto=Detroit}]

toCityList = (List<Map<String, String>>) fromCity.stream()
	    	        	        .filter(map -> ((Map<String, String>) map).containsValue(toCity))
	    	        	        .collect(Collectors.toList());

if the toCity is in the list then return connection found

if(toCityList.size() > 0) {
	 this.ifConnectionFound = true;	  

Else call the function again recursively:

getCityLink(finalListStartIndex, finalListEndIndex, tempCityToCityList, toCity);

===============2================	  

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
===============2================
List from Pass 1:

[{Toronto=Buffalo}, {Toronto=NewYork}, {Toronto=Boston}, {Toronto=Detroit}]

In the 2nd pass the code goes to the index and traverses the list from the ToCity. So for instance the first list is 
Toronto, Buffalo the code will look for Buffalo as the from to create the next set of list 
{Buffalo=NewYork}, {Buffalo=Boston}]

Likewise it will traverse through the other elements from the list:

[{Buffalo=NewYork}, {Buffalo=Boston}]
[{Buffalo=NewYork}, {Buffalo=Boston}, {NewYork=Boston}, {NewYork=Washington}]
[{Buffalo=NewYork}, {Buffalo=Boston}, {NewYork=Boston}, {NewYork=Washington}, {Boston=Buffalo}, {Boston=Philadelphia}]
[{Buffalo=NewYork}, {Buffalo=Boston}, {NewYork=Boston}, {NewYork=Washington}, {Boston=Buffalo}, {Boston=Philadelphia}]

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

nextStartIndex is a pointer to start from the next location. So for instance when the code traverses the first time the 
is nextStartIndex is 0. So it must start from index 0. After the first list  

[{Toronto=Buffalo}, {Toronto=NewYork}, {Toronto=Boston}, {Toronto=Detroit}]
it must start from index 4. So that the toList cities are not recreated.

fromCity.addAll(tempHoldCity);

fromCity =  (List<Map<String, String>>)fromCity.stream().distinct().collect(Collectors.toList());

The toCity list is added to the fromCity and it filters out any duplicates.

 toCityList = (List<Map<String, String>>) fromCity.stream()
        .filter(map -> ((Map<String, String>) map).containsValue(toCity))
        .collect(Collectors.toList());
 
 if the toCity is in the fromCity list then we have found a connection so return connectionFound as true.
 
 if(toCityList.size() > 0) {
	this.ifConnectionFound = true;
	return returnFinal;
}

if(finalListEndIndex == fromCity.size()) {
	returnFinal++;
	return returnFinal;
} else getCityLink(nextStartIndex, fromCity.size(), fromCity, toCity);

This is an important condition to terminate the recursion. If there are no new cities link added to the fromCity then that means 
that we have reached the end, and we should terminate the recursion.

Running the above logic for the cities set listed above and for Toronto to Indianapolis below is the cities it traverses to find the connectionb:

The first List [{Toronto=Buffalo}, {Toronto=NewYork}, {Toronto=Boston}, {Toronto=Detroit}]
Else fromCity [{Buffalo=NewYork}, {Buffalo=Boston}]
Else fromCity [{Buffalo=NewYork}, {Buffalo=Boston}, {NewYork=Boston}, {NewYork=Washington}]
Else fromCity [{Buffalo=NewYork}, {Buffalo=Boston}, {NewYork=Boston}, {NewYork=Washington}, {Boston=Buffalo}, {Boston=Philadelphia}]
Else fromCity [{Buffalo=NewYork}, {Buffalo=Boston}, {NewYork=Boston}, {NewYork=Washington}, {Boston=Buffalo}, {Boston=Philadelphia}]
Else fromCity [{NewYork=Boston}, {NewYork=Washington}]
Else fromCity [{NewYork=Boston}, {NewYork=Washington}, {Boston=Buffalo}, {Boston=Philadelphia}]
Else fromCity [{NewYork=Boston}, {NewYork=Washington}, {Boston=Buffalo}, {Boston=Philadelphia}, {Boston=Buffalo}, {Boston=Philadelphia}]
Else fromCity [{NewYork=Boston}, {NewYork=Washington}, {Boston=Buffalo}, {Boston=Philadelphia}, {Boston=Buffalo}, {Boston=Philadelphia}, {Washington=Las Vegas}, {Washington=Boston}]
Else fromCity [{NewYork=Boston}, {NewYork=Washington}, {Boston=Buffalo}, {Boston=Philadelphia}, {Boston=Buffalo}, {Boston=Philadelphia}, {Washington=Las Vegas}, {Washington=Boston}, {Buffalo=NewYork}, {Buffalo=Boston}]
Else fromCity [{NewYork=Boston}, {NewYork=Washington}, {Boston=Buffalo}, {Boston=Philadelphia}, {Boston=Buffalo}, {Boston=Philadelphia}, {Washington=Las Vegas}, {Washington=Boston}, {Buffalo=NewYork}, {Buffalo=Boston}, {Philadelphia=Buffalo}, {Philadelphia=Boston}, {Philadelphia=Miami}]
Else fromCity [{Las Vegas=Washington}]
Else fromCity [{Las Vegas=Washington}, {Boston=Buffalo}, {Boston=Philadelphia}]
Else fromCity [{Las Vegas=Washington}, {Boston=Buffalo}, {Boston=Philadelphia}, {Buffalo=NewYork}, {Buffalo=Boston}]
Else fromCity [{Las Vegas=Washington}, {Boston=Buffalo}, {Boston=Philadelphia}, {Buffalo=NewYork}, {Buffalo=Boston}, {Boston=Buffalo}, {Boston=Philadelphia}]
Else fromCity [{Las Vegas=Washington}, {Boston=Buffalo}, {Boston=Philadelphia}, {Buffalo=NewYork}, {Buffalo=Boston}, {Boston=Buffalo}, {Boston=Philadelphia}, {Miami=Houston}]
Else fromCity [{Washington=Las Vegas}, {Washington=Boston}]
Else fromCity [{Washington=Las Vegas}, {Washington=Boston}, {Houston=Dallas}]
Else fromCity [{Dallas=Indianapolis}]

