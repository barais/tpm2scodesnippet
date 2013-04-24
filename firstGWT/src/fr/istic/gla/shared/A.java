package fr.istic.gla.shared;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class A {

	@Id
	@GeneratedValue
	long id;
	
}
