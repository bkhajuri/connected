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

===============1================

