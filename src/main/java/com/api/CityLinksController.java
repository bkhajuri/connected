package com.api;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.service.CityLinksService;;

@RequestMapping("api/v1/connected")
@RestController
public class CityLinksController {
	private CityLinksService countryService;;
	
	@Autowired
	public CityLinksController(CityLinksService countryService) {
		super();
		this.countryService = countryService;
	}
	
	@RequestMapping(value = "/{origin}/{destination}", method=RequestMethod.GET)
	public String getCitiesConnection(@PathVariable String origin, @PathVariable String destination, HttpServletResponse response){
		
		System.out.println("Origin "+origin+" destination "+destination);
		
		String result = "Link Does not Exist";
		try {
			if(origin != null && origin.trim().length() >0 && destination != null && destination.trim().length() >0)
				result = countryService.getCitiesConnection(origin, destination);
			else {
				System.out.println("Inside Bad Request");
				throw new ResponseStatusException(
				           HttpStatus.BAD_REQUEST, "Origin or destination is Empty");
				    }
		}catch(Exception ex) {
			throw new ResponseStatusException(
			           HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex);
		}
		return result ;
	}
	
	
	
}

