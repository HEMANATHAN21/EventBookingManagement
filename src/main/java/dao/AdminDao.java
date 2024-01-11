package dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import dto.Admin;

public class AdminDao 
{
	public EntityManager getEm()
	{
		return Persistence.createEntityManagerFactory("solo").createEntityManager();
	}
	
	public Admin saveAdmin(Admin admin)
	{
		EntityManager em = getEm();
		em.getTransaction().begin();
		em.persist(admin);
		em.getTransaction().commit();
		return admin;
	}
	
	public Admin findAdmin(int adminId)
	{
		EntityManager em = getEm();
		Admin admin = em.find(Admin.class, adminId);
		if(admin != null)
		{
			return admin;
		}
		else
		{
			return null;
		}
	}
	
	public Admin deleteAdmin(int adminId)
	{
		EntityManager em = getEm();
		Admin admin = em.find(Admin.class, adminId);
		if(admin != null)
		{
			em.getTransaction().begin();
			em.remove(admin);
			em.getTransaction().commit();
			return admin;
		}
		return null;
	}
	
	public Admin updateAdmin(Admin admin, int adminId)
	{
		EntityManager em = getEm();
		Admin exAdmin = em.find(Admin.class, adminId);
		if(exAdmin != null)
		{
			em.getTransaction().begin();
			admin.setAdminId(adminId);
			Admin ad = em.merge(admin);
			em.getTransaction().commit();
			return ad;
		}
		return null;
	}
}
