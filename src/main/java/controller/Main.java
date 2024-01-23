package controller;

import java.util.List;
import java.util.Scanner;

import dto.Admin;
import dto.Client;
import dto.ClientEvent;
import dto.ClientService;
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
			System.out.println("Press -2- For Client Operation");
			int operationValue = sc.nextInt();
			if(operationValue == 1)
			{
				while(true)
				{
					System.out.println("---------------------------------------------");
					System.out.println("| Press -1- For Create Admin User           |");
					System.out.println("| Press -2- For Save Service To Admin DB    |");
					System.out.println("| Press -3- For Update Service To Admin DB  |");
					System.out.println("| Press -4- For Delete Service To Admin DB  |");
					System.out.println("| Press -5- For Show List Of Services In DB |");
					System.out.println("| Press -6- For Back                        |");
					System.out.println("---------------------------------------------");
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
					System.out.println("\t----------------------------------------------------");
					System.out.println("\t| Press -1- For Create Client User                 |");
					System.out.println("\t| Press -2- For Create ClientEvent With Services   |");
					System.out.println("\t| Press -3- For Show Services                      |");
					System.out.println("\t| Press -4- For Display ClientEvent Details		   |");
					System.out.println("\t| Press -5- For Save(Extra) Service To ClientEvent |");
					System.out.println("\t| Press -6- For Add ClientEvent Without Services   |");
					System.out.println("\t| Press -7- For Remove ClientService In ClientEvent|");
					System.out.println("\t| Press -8- For Back							   |");
					System.out.println("\t----------------------------------------------------");
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
						ClientEvent ce = evm.getClientEvent();
						if(ce != null)
							evm.displayClientEventDetails(ce);
						else
							System.out.println("Some Errors Occured..");		
					}
					else if(cliOpr == 5)
					{
						String s = evm.addClientService();
						System.out.println(s);
					}
					else if(cliOpr == 6)
					{
						String s = evm.addClientEventOnly();
						System.out.println(s);
					}
					else if(cliOpr == 7)
					{
						String s = evm.removeClientService();
						System.out.println(s);
					}
					else if(cliOpr == 8)
					{
						break;
					}
				}
			}
			
			
		}

	}

}
