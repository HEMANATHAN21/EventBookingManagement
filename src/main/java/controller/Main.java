package controller;

import java.util.List;
import java.util.Scanner;

import dto.Admin;
import dto.Client;
import dto.ClientEvent;
import dto.Service;

public class Main 
{

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		EventManagement evm = new EventManagement();
		while(true)
		{
			System.out.println("\t--------------------------------------------------");
			System.out.println("\t|           EVENT BOOKING MANAGEMENT             |");
			System.out.println("\t--------------------------------------------------");
			System.out.println("Press -1- For Admin Operation");
			System.out.println("Press -2- For Admin Operation");
			int operationValue = sc.nextInt();
			if(operationValue == 1)
			{
				while(true)
				{
					System.out.println("Press -1- For Create Admin User");
					System.out.println("Press -2- For Save Service To Admin DB");
					System.out.println("Press -3- For Update Service To Admin DB");
					System.out.println("Press -4- For Delete Service To Admin DB");
					System.out.println("Press -5- For Show List Of Services In DB");
					System.out.println("Press -6- For Back");
					int adOpr = sc.nextInt();
					if(adOpr == 1)
					{
						Admin a = evm.saveAdmin();
						if(a != null)
							System.out.println("Admin User Created....");
						else
							System.out.println("Admin User Not Created....");
					}
					else if(adOpr == 2)
					{
						Service s = evm.saveservice();
						if(s != null)
							System.out.println("Service Added Successfully....");
						else
							System.out.println("Service Not Added....");
					}
					else if(adOpr == 3)
					{
						 String s = evm.updateService();
						 System.out.println(s);
					}
					else if(adOpr == 4)
					{
						String s = evm.deleteService();
						System.out.println(s);
					}
					else if(adOpr == 5)
					{
						List<Service> listOfServices = evm.getAllServices();
						for (Service service : listOfServices) 
						{
							System.out.println(service);
						}
					}
					else if(adOpr == 6)
					{
						break;
					}
					
				}
			}
			else if(operationValue == 2)
			{
				while(true)
				{
					System.out.println("Press -1- For Create Client User");
					System.out.println("Press -2- For Create ClientEvent");
					System.out.println("Press -3- For Show Services");
					System.out.println("Press -4- For Display ClientEvent Details");
					System.out.println("Press -5- For Back");
					int cliOpr = sc.nextInt();
					if(cliOpr == 1)
					{
						Client c = evm.saveClient();
						if(c != null)
							System.out.println("Client User Created....");
						else
							System.out.println("Client User Not Created....");
					}
					else if(cliOpr == 2)
					{
						String s = evm.createClientEvent();
						System.out.println(s);
					}
					else if(cliOpr == 3)
					{
						List<Service> listOfServices = evm.getAllListServices();
						for (Service service : listOfServices) 
						{
							System.out.println(service);
						}
					}
					else if(cliOpr == 4)
					{
						ClientEvent ce = evm.displayClientEvent();
						if(ce != null)
						{
							System.out.println("Event ID : "+ce.getClientEventId());
							System.out.println("Event Type : "+ce.getEventType());
							System.out.println("Event Location : "+ce.getClientEventLocation());
							System.out.println("Total People Count : "+ce.getClientEventNoOfPeople());
							System.out.println("Total Cost : ");
						}
							
					}
					else if(cliOpr == 5)
					{
						break;
					}
				}
			}
			
			
		}

	}

}