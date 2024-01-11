package dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import dto.Service;


public class ServiceDao 
{
	public EntityManager getEm()
	{
		return Persistence.createEntityManagerFactory("solo").createEntityManager();
	}
	
	public Service saveService(Service service)
	{
		EntityManager em = getEm();
		em.getTransaction().begin();
		em.persist(service);
		em.getTransaction().commit();
		return service;
	}
	
	public Service findService(int serviceId)
	{
		EntityManager em = getEm();
		Service service = em.find(Service.class, serviceId);
		if(service != null)
		{
			return service;
		}
		else
		{
			return null;
		}
	}
	
	public Service deleteService(int serviceId)
	{
		EntityManager em = getEm();
		Service service = em.find(Service.class, serviceId);
		if(service != null)
		{
			em.getTransaction().begin();
			em.remove(service);
			em.getTransaction().commit();
			return service;
		}
		return null;
	}
	
	public Service updateService(Service service, int serviceId)
	{
		EntityManager em = getEm();
		Service exService = em.find(Service.class, serviceId);
		if(exService != null)
		{
			em.getTransaction().begin();
			service.setServiceId(serviceId);
			Service se = em.merge(service);
			em.getTransaction().commit();
			return se;
		}
		return null;
	}
}
