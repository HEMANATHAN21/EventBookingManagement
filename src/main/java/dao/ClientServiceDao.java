package dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import dto.ClientService;


public class ClientServiceDao 
{
	public EntityManager getEm()
	{
		return Persistence.createEntityManagerFactory("solo").createEntityManager();
	}
	
	public ClientService saveClientService(ClientService clientService)
	{
		EntityManager em = getEm();
		em.getTransaction().begin();
		em.persist(clientService);
		em.getTransaction().commit();
		return clientService;
	}
	
	public ClientService findClientService(int clientServiceId)
	{
		EntityManager em = getEm();
		ClientService clientService = em.find(ClientService.class, clientServiceId);
		if(clientService != null)
		{
			return clientService;
		}
		else
		{
			return null;
		}
	}
	
	public ClientService deleteClientService(int clientServiceId)
	{
		EntityManager em = getEm();
		ClientService clientService = em.find(ClientService.class, clientServiceId);
		if(clientService != null)
		{
			em.getTransaction().begin();
			em.remove(clientService);
			em.getTransaction().commit();
			return clientService;
		}
		return null;
	}
	
	public ClientService updateClientService(ClientService clientService, int clientServiceId)
	{
		EntityManager em = getEm();
		ClientService exClientService = em.find(ClientService.class, clientServiceId);
		if(exClientService != null)
		{
			em.getTransaction().begin();
			clientService.setClientServiceId(clientServiceId);
			ClientService cl = em.merge(clientService);
			em.getTransaction().commit();
			return cl;
		}
		return null;
	}

}
