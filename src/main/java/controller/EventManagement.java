package controller;

import java.time.LocalDate;
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
import dto.Client;
import dto.ClientEvent;
import dto.ClientService;
import dto.EventType;
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
		if(exAdmin != null)
		{
			System.out.println("\t ----- Save Service -----");
			Service service = new Service();
			System.out.print("Enter Service Name : "); String serviceName = sc.next(); service.setServiceName(serviceName);
			System.out.print("Enter Service Cost  PerPerson : "); double servicePerPersonCost = sc.nextDouble(); service.setServiceCostPerPerson(servicePerPersonCost);
			System.out.print("Enter Service Cost PerDay : "); double serviceCostPerDay = sc.nextDouble(); service.setServiceCostPerDay(serviceCostPerDay);
			Service s = sdao.saveService(service);
			
			exAdmin.getAdminServices().add(s);
			Admin a = adao.updateAdmin(exAdmin, exAdmin.getAdminId());
			
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
	
	public List<Service> getAllListServices()
	{
		Query query = em.createQuery("select s from Service s");
		List<Service> listOfServices= query.getResultList();
		return listOfServices;

	}
	
	public String updateService()
	{
		List<Service> listOfServices = getAllServices();
		System.out.println("\t ----- Service Lists -----");
		for (Service service : listOfServices) 
		{
			System.out.println(service);
		}
		System.out.print("Enter Service Id To Update : ");
		int id = sc.nextInt();
		Service service = sdao.findService(id);
		System.out.print("Enter Service Cost  PerPerson : "); double servicePerPersonCost = sc.nextDouble(); service.setServiceCostPerPerson(servicePerPersonCost);
		System.out.print("Enter Service Cost PerDay : "); double serviceCostPerDay = sc.nextDouble(); service.setServiceCostPerDay(serviceCostPerDay);
		Service s = sdao.updateService(service, id);
		if(s != null)
			return "Updated Successfully";
		
		return "Service Not Updated";
		
	}
	
	public String deleteService()
	{
		System.out.println("\t ----- Delete Service -----");
		Admin exAdmin = adminLogin();
		List<Service> listOfServices = exAdmin.getAdminServices();
		List<Service> newlistOfServices = new ArrayList<Service>();
		if(exAdmin != null)
		{
			System.out.println("\t ----- Service Lists -----");
			for (Service service : listOfServices) 
			{
				System.out.println(service);
			}
			System.out.print("Enter Service Id To Delete : ");
			int id = sc.nextInt();
			for (Service service : listOfServices) 
			{
				if(service.getServiceId() == id)
				{

					exAdmin.setAdminServices(newlistOfServices);
					adao.updateAdmin(exAdmin, exAdmin.getAdminId());
					sdao.deleteService(service.getServiceId());
				}
				else if(service.getServiceId() != id)
				{
					newlistOfServices.add(service);
				}
			}
			exAdmin.setAdminServices(newlistOfServices);
			adao.updateAdmin(exAdmin, exAdmin.getAdminId());
			return "Service Deleted";
			
		}
		return "Service Not Deleted";
	}
	
	public Client saveClient()
	{
		System.out.println("\t ----- Save Client -----");
		Client c = new Client();
		System.out.print("Enter Client Name : "); c.setClientName(sc.next());
		System.out.print("Enter Client Contact No : "); c.setClientContact(sc.nextLong());
		System.out.print("Enter Client Email : "); c.setClientEmail(sc.next());
		System.out.print("Enter Client Password : "); c.setClientPassword(sc.next());
		return cdao.saveClient(c);
	}
	
	public Client clientLogin()
	{
		System.out.println("\t ----- Client Login -----");
		System.out.print("Enter Email : "); String email = sc.next();
		System.out.print("Enter Password : "); String password = sc.next();
		Query query = em.createQuery("select c from Client c where c.clientEmail = ?1");
		query.setParameter(1, email);
		Client c = (Client) query.getSingleResult();
		if(c != null)
		{
			if(c.getClientPassword().equals(password))
				return c;
			return null;
		}
		return null;
	}

	public String createClientEvent() 
	{
		System.out.println("\t ----- Save ClientEvent -----");
		Client exClient = clientLogin();
		List<ClientEvent> clientEvents = new ArrayList<ClientEvent>();
		if(exClient != null)
		{
			ClientEvent ce = new ClientEvent();
			
			System.out.println("\tEnter Event Type  ");
			System.out.println("Press -1- for Marriage");
			System.out.println("Press -2- for Engagement");
			System.out.println("Press -3- for Birthday");
			System.out.println("Press -4- for Babyshower");
			System.out.println("Press -5- for Anniversary");
			System.out.println("Press -6- for BachelorParty");
			System.out.println("Press -7- for NamingCeremony");
			System.out.println("Press -8- for Reunion");
			
			int value = sc.nextInt();
			if(value == 1)
				ce.setEventType(EventType.Marriage);
			else if(value == 2)
				ce.setEventType(EventType.Engagement);
			else if(value == 3)
				ce.setEventType(EventType.Birthday);
			else if(value ==4)
				ce.setEventType(EventType.Babyshower);
			else if(value == 5)
				ce.setEventType(EventType.Anniversary);
			else if(value == 6)
				ce.setEventType(EventType.BachelorParty);
			else if(value == 7)
				ce.setEventType(EventType.NamingCeremony);
			else if(value == 8)
				ce.setEventType(EventType.Reunion);
			
			ce.setStartDate(LocalDate.now());
			System.out.print("No Of Days : "); ce.setClientEventNoOfDays(sc.nextInt());
			System.out.print("No Of People : "); ce.setClientEventNoOfPeople(sc.nextInt());
			System.out.print("Event Location : "); ce.setClientEventLocation(sc.next());
			//System.out.println("To Add A Client Give Id Of Particular client "); int cId = sc.nextInt();
			ce.setClient(exClient);
			System.out.print("Enter Adding Count Of Services : "); int sCount = sc.nextInt();
			double eventCost = 0;
			List<ClientService> clientServices = new ArrayList<ClientService>();
			for(int i=0;i<sCount;i++)
			{
				ClientService cs = new ClientService();
//				System.out.println("Press -1- for Catering");
//				System.out.println("Press -2- for Dance");
//				System.out.println("Press -3- for Food");
				List<Service> listOfServices = getAllListServices();
				System.out.println("\t ----- Service Lists -----");
				for (Service service : listOfServices) 
				{
					System.out.println(service);
				}
				System.out.println("Enter Service Id :");
				int svalue = sc.nextInt();
				Service s1 = sdao.findService(svalue);
				//System.out.println(s1);
				cs.setClientServiceName(s1.getServiceName());
				cs.setClientServiceNoOfDays(ce.getClientEventNoOfDays());
				cs.setClientServiceCostPerPerson(s1.getServiceCostPerPerson());
//				cs.setClientServiceCost(ce.getClientEventNoOfPeople() * cs.getClientServiceCostPerPerson());
				cs.setClientServiceCost(ce.getClientEventNoOfPeople() * cs.getClientServiceCostPerPerson() * cs.getClientServiceNoOfDays());
				eventCost = eventCost + cs.getClientServiceCost();
				clientServices.add(cs);
				ClientService cs1= csdao.saveClientService(cs);
				//System.out.println(cs1);
				
			}
			ce.setClientEventCost(eventCost);
			ce.setClientServices(clientServices);
			clientEvents.add(ce);
			exClient.setClientEvent(clientEvents);
			Client updatedClient = cdao.updateClient(exClient, exClient.getClientId());
			if(updatedClient != null)
			{
				return "Client Event Added";
			}
		}
		return "Client Event Not Added";
	}
	public ClientEvent getClientEvent()
	{
		Client c = clientLogin();
		if(c != null)
		{
			System.out.print("Enter ClientEventId : ");int cliEveValue = sc.nextInt();
			Query query = em.createQuery("select ce from ClientEvent ce where ce.clientEventId = ?1");
			query.setParameter(1, cliEveValue);
			ClientEvent ce = (ClientEvent) query.getSingleResult();
			return ce;
		}
		return null;
	}
	
	public void displayClientEventDetails(ClientEvent ce)
	{
		System.out.println("Event ID : "+ce.getClientEventId());
		System.out.println("Event Type : "+ce.getEventType());
		System.out.println("Event Client Name : "+ce.getClient().getClientName());
		System.out.println("Event Client Email : "+ce.getClient().getClientEmail());
		System.out.println("Event Location : "+ce.getClientEventLocation());
		System.out.println("Total People Count : "+ce.getClientEventNoOfPeople());
		System.out.println("Total Cost : "+ce.getClientEventCost());
		
		List<ClientService> clientServicesList = ce.getClientServices();
		for (ClientService clientService : clientServicesList) 
		{
			System.out.println(clientService);
		}
	}
	
	public String addClientService()
	{
		System.out.println("------Add Client Service--------");
		Client exClient = clientLogin();
		if(exClient != null)
		{
			List<ClientEvent> exClientEvents = exClient.getClientEvent();
			System.out.println("Enter Client Event Id : "); int exClientEventId = sc.nextInt();
			int count = 0;
			for(ClientEvent events : exClientEvents)
			{
				if(events.getClientEventId() == exClientEventId)
				{
					count ++;
					double eventCost = events.getClientEventCost();
					List<ClientService> exClientServices = events.getClientServices();
					System.out.println("Enter Service Adding Count : "); int serviceCount = sc.nextInt();
					for(int i=1;i<=serviceCount;i++)
					{
						ClientService cs = new ClientService();
						List<Service> listOfServices = getAllListServices();
						System.out.println("\t ----- Service Lists -----");
						for (Service service : listOfServices) 
						{
							System.out.println(service);
						}
						System.out.print("Enter Service Id :");
						int svalue = sc.nextInt();
						Service s1 = sdao.findService(svalue);
						cs.setClientServiceName(s1.getServiceName());
						cs.setClientServiceNoOfDays(events.getClientEventNoOfDays());
						cs.setClientServiceCostPerPerson(s1.getServiceCostPerPerson());
						cs.setClientServiceCost(events.getClientEventNoOfPeople() * cs.getClientServiceCostPerPerson() * cs.getClientServiceNoOfDays());
						eventCost = eventCost + cs.getClientServiceCost();
						exClientServices.add(cs);
						ClientService cs1= csdao.saveClientService(cs);
					}
					
					events.setClientEventCost(eventCost);
					events.setClientServices(exClientServices);
					ClientEvent ce1 = cedao.updateClientEvent(events, events.getClientEventId());
					if(ce1 != null)
					{
						return "Client Service Added";
					}
				}
			}
			if(count == 0)
				return "Invalid Id Event Not Found";
		}
		return "Client Service Not Added";
	}
	public static void main(String[] args) 
	{
		EventManagement evm = new EventManagement();
		ClientEventDao cedao = new ClientEventDao();
//		Admin a = evm.saveAdmin();
//		System.out.println(a);
		
//		Admin a = evm.adminLogin();
//		System.out.println(a);
		
//		System.out.println(evm.saveservice());
//		System.out.println(evm.updateService());
//		System.out.println(evm.deleteService());
		
//		System.out.println(evm.saveClient());
//		System.out.println(evm.clientLogin());
//		System.out.println(evm.createClientEvent());
		
//		System.out.println(cedao.findClientEvent(3));
		System.out.println(evm.addClientService());
	}

}
