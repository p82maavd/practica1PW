package ejercicio1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ejercicio1.Anuncio.Estados;

/**
 * Declaracion de la clase ConcretreFactory.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 * 
 */

public class ConcreteFactory extends AbstractFactory {

	// Implementation of creation methods
	
	@Override
	public AnuncioFlash createAnuncioFlash(Contacto e, int ids){
		ControlDeErrores control=new ControlDeErrores();
		Scanner sc=new Scanner(System.in);
		int id=ids;
		String titulo=new String();
		System.out.print("Titulo del Anuncio: ");
		titulo=sc.nextLine();
		String cuerpo=new String();
		System.out.print("Cuerpo del Anuncio: ");
		cuerpo=sc.nextLine();
		Estados estado=Estados.Editado;
		
		Date fechaInicio=new Date();
		System.out.print("Introduzca la fecha de inicio(dd/mm/yyyy hh:mm:ss): ");
		String fechain=new String();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		int cont=1;
		while(cont!=0) {
			cont=0;
			try {
				fechain = sc.nextLine();
				while(!(control.esFecha(fechain))) {
					System.out.println("Formato de la fecha (dd/mm/yyyy)");
					System.out.print("Vuelva a introducir la fecha: ");
					fechain=sc.nextLine();
				}
				fechaInicio = formatter.parse(fechain);
			} catch (ParseException e1) {
				System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy hh:mm:ss): ");
				
				cont++;
			}
		}
		
		Date fechaFinal=new Date();
		System.out.print("Introduzca la fecha final(dd/mm/yyyy hh:mm:ss): ");
		String fechafin=new String();
		
		cont=1;
		while(cont!=0) {
			cont=0;
			try {
				fechafin = sc.nextLine();
				while(!(control.esFecha(fechafin))) {
					System.out.println("Formato de la fecha (dd/mm/yyyy)");
					System.out.print("Vuelva a introducir la fecha: ");
					fechafin=sc.nextLine();
				}
				fechaFinal = formatter.parse(fechafin);
			} catch (ParseException e2) {
				System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy hh:mm:ss): ");
				
				cont++;
			}
		}
		
		
		AnuncioFlash product = new AnuncioFlash(id, titulo, cuerpo, e, estado, fechaInicio, fechaFinal);
		return product;
	}

	@Override
	public AnuncioIndividualizado createAnuncioIndividualizado(Contacto e, int ids) {
		Scanner sc=new Scanner(System.in);
		int id=ids;
		String titulo=new String();
		System.out.print("Titulo del Anuncio: ");
		titulo=sc.nextLine();
		String cuerpo=new String();
		System.out.print("Cuerpo del Anuncio: ");
		cuerpo=sc.nextLine();
		Estados estado=Estados.Editado;
		
		
		
		AnuncioIndividualizado product = new AnuncioIndividualizado(id, titulo, cuerpo, e, estado);
		return product;
	}
	
	@Override
	public AnuncioTematico createAnuncioTematico(Contacto e, ArrayList <Interes> intereses,int ids) {
		
		ArrayList<Interes> interesesaux=new ArrayList<Interes>();
		Integer neweleccion=0;
		Integer newinteres;
		Scanner sc = new Scanner(System.in);
		Boolean condicion=true;
		while(condicion) {
			
			System.out.println("Seleccione un interes para el anuncio: ");
			
			for (Interes myVar : intereses) {
				System.out.println(myVar.getId()+". "+myVar.getInteres());
				
			}
			
			
			newinteres= sc.nextInt();
			sc.nextLine();
			
			for (int i=1;i<=intereses.size();i++) {
				if(newinteres==i) {
					
					interesesaux.add(intereses.get(i-1));
				}
			}
			
			System.out.println("Desea añadir mas intereses: 1. Si 2. No");
			neweleccion=sc.nextInt();
			sc.nextLine();
			
			if(neweleccion!=1) {
				condicion=false;
			}
		}
		
		int id=ids;
		String titulo=new String();
		System.out.print("Introduzca el titulo del anuncio: ");
		titulo=sc.nextLine();
		String cuerpo=new String();
		System.out.print("Introduzca el cuerpo del anuncio: ");
		cuerpo=sc.nextLine();
		ArrayList<Contacto> destinatarios=new ArrayList <Contacto>();
		Estados estado=Estados.Editado;
		
		
		AnuncioTematico product = new AnuncioTematico( id, titulo, cuerpo, e, interesesaux, estado);
		
		return product;
	}

	@Override
	public AnuncioGeneral createAnuncioGeneral(Contacto e,int ids) {
		
		Scanner sc=new Scanner(System.in);
		int id=ids;
		String titulo=new String();
		System.out.print("Titulo del Anuncio: ");
		titulo=sc.nextLine();
		String cuerpo=new String();
		System.out.print("Cuerpo del Anuncio: ");
		cuerpo=sc.nextLine();
		Estados estado=Estados.Editado;
		
		AnuncioGeneral product = new AnuncioGeneral(id, titulo, cuerpo, e, estado);
		return product;
	}

}