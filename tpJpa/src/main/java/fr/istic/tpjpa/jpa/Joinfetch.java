package fr.istic.tpjpa.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.istic.tpjpa.domain.Department;
import fr.istic.tpjpa.domain.Employee;

public class Joinfetch {

	private EntityManager manager;

	public Joinfetch(EntityManager manager) {
		this.manager = manager;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("withoutcreate");
		EntityManager manager = factory.createEntityManager();
		Joinfetch test = new Joinfetch(manager);

		
		TypedQuery<Department> q = test.manager.createQuery("select distinct d from Department d join fetch d.employees e",Department.class);
		long start = System.currentTimeMillis();
		List<Department> res = q.getResultList();
		
		for (Department d : res){
			for (Employee e : d.getEmployees()){
				e.getName();
			}
		}

		long end = System.currentTimeMillis();
		long duree = end - start;
		System.err.println("temps d'exec = " +  duree);

		// TODO persist entity


		// TODO run request

		System.out.println(".. done");
	}

}
