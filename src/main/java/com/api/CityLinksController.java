package com.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.service.CityLinksService;;

@RequestMapping("api/v1/connected")
@RestController
public class CityLinksController {
	private final CityLinksService countryService;;
	
	@Autowired
	public CityLinksController(CityLinksService countryService) {
		super();
		this.countryService = countryService;
	}
	
	@RequestMapping(value = "/{origin}/{destination}", method=RequestMethod.GET)
	public String getCitiesConnection(@PathVariable String origin, @PathVariable String destination) throws Exception{
		
		String result = "Link Does not Exist";
		try {
			result = countryService.getCitiesConnection(origin, destination);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return result ;
	}
	
}

