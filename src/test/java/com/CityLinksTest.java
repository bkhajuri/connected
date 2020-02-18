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
	  String result = underTest.getCitiesConnection("Toronto", "Dallas");
      assertThat(result).isEqualTo("Links Exists");
    
  }
  
  @Test
  public void citisLinkTest2() throws Exception{
   
	  underTest = new CityLinksService();
	  String result = underTest.getCitiesConnection("Houston", "Indianapolis");
      assertThat(result).isEqualTo("Links Exists");
      
   
      result  = underTest.getCitiesConnection("Toronto", "Las Vegas");
	  assertThat(result).isEqualTo("Links Exists");
      
      
      result = underTest.getCitiesConnection("Houston", "Toronto");
      assertThat(result).isEqualTo("Links Does Not Exists");
      
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

