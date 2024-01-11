package dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import dto.Client;

public class ClientDao 
{
	public EntityManager getEm()
	{
		return Persistence.createEntityManagerFactory("solo").createEntityManager();
	}
	
	public Client saveClient(Client client)
	{
		EntityManager em = getEm();
		em.getTransaction().begin();
		em.persist(client);
		em.getTransaction().commit();
		return client;
	}
	
	public Client findClient(int clientId)
	{
		EntityManager em = getEm();
		Client client = em.find(Client.class, clientId);
		if(client != null)
		{
			return client;
		}
		else
		{
			return null;
		}
	}
	
	public Client deleteClient(int clientId)
	{
		EntityManager em = getEm();
		Client client = em.find(Client.class, clientId);
		if(client != null)
		{
			em.getTransaction().begin();
			em.remove(client);
			em.getTransaction().commit();
			return client;
		}
		return null;
	}
	
	public Client updateClient(Client client, int clientId)
	{
		EntityManager em = getEm();
		Client exClient = em.find(Client.class, clientId);
		if(exClient != null)
		{
			em.getTransaction().begin();
			client.setClientId(clientId);
			Client cl = em.merge(client);
			em.getTransaction().commit();
			return cl;
		}
		return null;
	}

}
