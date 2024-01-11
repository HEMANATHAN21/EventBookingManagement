package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import dao.AdminDao;
import dao.ClientDao;
import dao.ClientEventDao;
import dao.ClientServiceDao;
import dao.ServiceDao;
import dto.Admin;
import dto.ClientEvent;
import dto.Service;

public class EventManagement 
{
	Scanner sc = new Scanner(System.in);
	AdminDao adao = new AdminDao();
	ClientDao cdao = new ClientDao();
	ClientEventDao cedao = new ClientEventDao();
	ClientServiceDao csdao = new ClientServiceDao();
	ServiceDao sdao = new ServiceDao();
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("solo");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	public Admin saveAdmin()
	{
		Admin a = new Admin();
		System.out.print("Enter Admin Name :"); String Name = sc.next(); a.setAdminName(Name);
		System.out.print("Enter Admin Contact No : "); long contact = sc.nextLong(); a.setAdminContact(contact);
		System.out.print("Enter Email : "); String email = sc.next(); a.setAdminEmail(email);
		System.out.print("Enter Password : "); String password = sc.next(); a.setAdminPassword(password);
		return adao.saveAdmin(a);
	}
	public Admin adminLogin()
	{
		System.out.println("\t ----- Admin Login -----");
		System.out.print("Enter Email : "); String email = sc.next();
		System.out.print("Enter Password : "); String password = sc.next();
		Query query = em.createQuery("select a from Admin a where a.adminEmail = ?1");
		query.setParameter(1, email);
		Admin a = (Admin) query.getSingleResult();
		if(a != null)
		{
			if(a.getAdminPassword().equals(password))
				return a;
			return null;
		}
		return null;

	}
	public Service saveservice()
	{
		Admin exAdmin = adminLogin();
//		List<Service> adminServices = new ArrayList<Service>();
		if(exAdmin != null)
		{
			Service service = new Service();
			System.out.print("Enter Service Name : "); String serviceName = sc.next(); service.setServiceName(serviceName);
			System.out.print("Enter Service Cost  PerPerson : "); double servicePerPersonCost = sc.nextDouble(); service.setServiceCostPerPerson(servicePerPersonCost);
			System.out.print("Enter Service Cost PerDay : "); double serviceCostPerDay = sc.nextDouble(); service.setServiceCostPerDay(serviceCostPerDay);
			Service s = sdao.saveService(service);
//			adminServices.add(service);
//			exAdmin.setAdminServices(adminServices);
//			Admin a = adao.updateAdmin(exAdmin, exAdmin.getAdminId());
			return s;
		}
		return null;
	}
	
	public List<Service> getAllServices()
	{
		Admin a = adminLogin();
		if(a != null)
		{
			Query query = em.createQuery("select s from Service s");
			List<Service> listOfServices= query.getResultList();
			return listOfServices;
		}
		return null;
	}
	
	public Service updateService()
	{
		List<Service> listOfServices = getAllServices();
		System.out.println("\t ----- Service Lists -----");
		for (Service service : listOfServices) 
		{
			System.out.println(service);
		}
		System.out.print("Enter Service Id To Update : ");
		int id = sc.nextInt();
		for (Service service : listOfServices) 
		{
			if(service.getServiceId() == id)
			{
				//System.out.print("Enter Service Name : "); String serviceName = sc.next(); service.setServiceName(serviceName);
				System.out.print("Enter Service Cost  PerPerson : "); double servicePerPersonCost = sc.nextDouble(); service.setServiceCostPerPerson(servicePerPersonCost);
				System.out.print("Enter Service Cost PerDay : "); double serviceCostPerDay = sc.nextDouble(); service.setServiceCostPerDay(serviceCostPerDay);
				Service s = sdao.updateService(service, id);
				return s;
			}
		}
		return null;
		
	}

	public static void main(String[] args) 
	{
		EventManagement evm = new EventManagement();
//		Admin a = em.saveAdmin();
//		System.out.println(a);
		
//		Admin a = evm.adminLogin();
//		System.out.println(a);
		
//		System.out.println(evm.saveservice());
		System.out.println(evm.updateService());

	}

}
