package com;

import org.junit.Before;
import org.junit.Test;

import com.service.CityLinksService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CityLinksTest {

  private CityLinksService underTest;

  @Before
  public void setUp() {
   
  }

  @Test
  public void citisLinkTest1() throws Exception{
    // Given person called James Bond aged 33
   
	  underTest = new CityLinksService();
	  underTest.getCitiesConnection("Toronto", "Dallas");
      assertThat(underTest.ifConnectionFound).isEqualTo(true);
    
  }
  
  @Test
  public void citisLinkTest2() throws Exception{
   
	  underTest = new CityLinksService();
	  
	  underTest.ifConnectionFound = false;
      underTest.getCitiesConnection("Houston", "Indianapolis");
      assertThat(underTest.ifConnectionFound).isEqualTo(true);
      
      underTest.ifConnectionFound = false;
	  underTest.getCitiesConnection("Toronto", "Las Vegas");
	  assertThat(underTest.ifConnectionFound).isEqualTo(true);
      
      underTest.ifConnectionFound = false;
      underTest.getCitiesConnection("Houston", "Toronto");
      assertThat(underTest.ifConnectionFound).isEqualTo(false);
      
      try {
    	  underTest.getCitiesConnection("", "Toronto");
      }catch(Exception ex) {
    	  ex.printStackTrace();
    	  assertThat(ex.toString()).isEqualTo("java.lang.Exception: The origin and or destination is empty");
      }
      
      try {
    	  underTest.getCitiesConnection("Toronto", "");
      }catch(Exception ex) {
    	  ex.printStackTrace();
    	  assertThat(ex.toString()).isEqualTo("java.lang.Exception: The origin and or destination is empty");
      }
      
      try {
    	  underTest.getCitiesConnection("", "");
      }catch(Exception ex) {
    	  ex.printStackTrace();
    	  assertThat(ex.toString()).isEqualTo("java.lang.Exception: The origin and or destination is empty");
      }
    
  }

}

