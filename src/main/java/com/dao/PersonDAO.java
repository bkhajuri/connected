package com.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.model.Person;

public interface PersonDAO {
	int insertPerson(UUID id, Person Person);
	
	default int insertPerson(Person person) {
		UUID id = UUID.randomUUID();
		return insertPerson(id, person);
	}
	
	List<Person> selectAllPeople();
	
	Optional<Person> selectPersonById(UUID id);
	
	int deletePersonById(UUID id);
	
	int updatePersonById(UUID id, Person person);
	
}
