package dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import dto.ClientEvent;


public class ClientEventDao 
{
	public EntityManager getEm()
	{
		return Persistence.createEntityManagerFactory("solo").createEntityManager();
	}
	
	public ClientEvent saveClientEvent(ClientEvent clientEvent)
	{
		EntityManager em = getEm();
		em.getTransaction().begin();
		em.persist(clientEvent);
		em.getTransaction().commit();
		return clientEvent;
	}
	
	public ClientEvent findClientEvent(int clientEventId)
	{
		EntityManager em = getEm();
		ClientEvent clientEvent = em.find(ClientEvent.class, clientEventId);
		if(clientEvent != null)
		{
			return clientEvent;
		}
		else
		{
			return null;
		}
	}
	
	public ClientEvent deleteClientEvent(int clientEventId)
	{
		EntityManager em = getEm();
		ClientEvent clientEvent = em.find(ClientEvent.class, clientEventId);
		if(clientEvent != null)
		{
			em.getTransaction().begin();
			em.remove(clientEvent);
			em.getTransaction().commit();
			return clientEvent;
		}
		return null;
	}
	
	public ClientEvent updateClientEvent(ClientEvent clientEvent, int clientEventId)
	{
		EntityManager em = getEm();
		ClientEvent exClientEvent = em.find(ClientEvent.class, clientEventId);
		if(exClientEvent != null)
		{
			em.getTransaction().begin();
			clientEvent.setClientEventId(clientEventId);
			ClientEvent cl = em.merge(clientEvent);
			em.getTransaction().commit();
			return cl;
		}
		return null;
	}
}
