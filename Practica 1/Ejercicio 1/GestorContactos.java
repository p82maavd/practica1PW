package practica1entrega;

/**
 * 
 * @author Damian Martinez
 * @author Daniel Ortega
 * Declaracion de la clase GestorContactos
 */


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.EOFException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import practica1entrega.Contacto.Intereses;


public class GestorContactos {
	
	private static GestorContactos instance =null;
	
	private ArrayList <Contacto> listaContactos;

	/**
	 * Este método se encarga de crear una instancia en el caso de que no haya una ya creada. Patron de diseño Singleton
	 * @return Instancia única de GestorContactos.
	*/
	public static GestorContactos getInstance() {
		
		if(instance==null) {
			instance=new GestorContactos();
		}
		return instance;
	}
	
	/**
	 * Constructor de la clase GestorContactos.
	*/
	private GestorContactos() {
		
		this.listaContactos = new ArrayList<Contacto>();
		
	}
		
	//Creo que estaria mejor separarlo en funciones. Si sobra tiempo. Mirar buscarAnuncios o alguno de esos.
	public Contacto buscarContacto()  {

		ArrayList<Contacto> aux=new ArrayList<Contacto>();

		System.out.println("Elige parametro de busqueda: ");
		System.out.println("1. Nombre y Apellidos");
		System.out.println("2. Email");
		System.out.println("3. Intereses");
		System.out.println("4. Fecha de nacimiento");
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca un número entero: ");
		Integer a = sc.nextInt();
		sc.nextLine();
		Contacto buscado=null;

		if(a==1) {

			String nombreaux;
			String apellidosaux;

			int n = 0;
			System.out.print("Introduzca el nombre de la persona a buscar: ");
			nombreaux = sc.nextLine();
			System.out.print("Introduzca sus apellidos: ");
			apellidosaux = sc.nextLine();
			for(int i=0; i<this.listaContactos.size();i++) {
				if(this.listaContactos.get(i).getNombre().equals(nombreaux) && this.listaContactos.get(i).getApellidos().equals(apellidosaux)) {
					n = n + 1;
					aux.add(this.listaContactos.get(i));
				}
			}
			if(n==0) {
				System.out.print("No se ha encontrado ninguna persona que se llame así ");
				return null;
			}

			if(n==1) {
				return aux.get(0);
			}

			for(Integer i=0;i<aux.size();i++) {
				System.out.println(i.toString()+"Nombre: "+aux.get(i).getNombre()+" Email: "+ aux.get(i).getEmail());
			}

			System.out.println("Selecciona el contacto buscado");

			int seleccion2=sc.nextInt();
			sc.nextLine();

			for(int i=0;i<aux.size();i++) {

				if(i==seleccion2) {
					System.out.println("Contacto Seleccionado");
					buscado=aux.get(i);		
					break;
				}
			}

			return buscado;

		}	

		else if(a==2) {
			String emailaux;
			int n = 0;
			System.out.print("Indique el email a buscar: ");
			emailaux = sc.nextLine();
			for(int i=0; i<this.listaContactos.size();i++) {
				if(this.listaContactos.get(i).getEmail().equals(emailaux)) {
					n = n + 1;
					Contacto e=this.listaContactos.get(i);
					return e;
				}
			}
			if(n==0) {
				System.out.print("No se ha encontrado ninguna persona con ese email.");
				return null;
			}

		}

		//Intereses en enum. Actua
		/*
			else if(a==3) {
				Integer cont=0;

				//Imprime todos los intereses

				for(String s: claseintereses.getIntereses()) {
					System.out.println(cont.toString()+s);
					cont++;
				}


				System.out.print("Indique que interes buscar: ");
			    int seleccion = sc.nextInt();
			    sc.nextLine();
			    String interesaux=new String();

				for(int i=0; i<claseintereses.getIntereses().size();i++) {

					if(seleccion==i) {
						interesaux=claseintereses.getIntereses().get(i);		
					}

				}

				//Busca los contactos que tengan el interes seleccionado arriba

				for(Contacto d: this.listaContactos) {

					for(int i=0;i<d.getIntereses().size();i++) {

						if(d.getIntereses().get(i).equals(interesaux)) {	
							aux.add(d);
							break;
						}
					}

				}

				if(aux.size()==0) {
					System.out.println("No existe ningun contacto con dichos intereses");
					return null;
				}

				if(aux.size()==1) {

					return aux.get(0);
				}

				//Imprime todos los contactos con dicho interes.

				for(Integer i=0;i<aux.size();i++) {
					System.out.println(i.toString()+"Nombre: "+aux.get(i).getNombre()+" Email: "+ aux.get(i).getEmail());

				}

				System.out.println("Selecciona el contacto buscado");

				int seleccion2=sc.nextInt();
				sc.nextLine();

				for(int i=0;i<aux.size();i++) {

					if(i==seleccion2) {
						System.out.println("Contacto Seleccionado");
						buscado=aux.get(i);		
						break;
					}
				}
				return buscado;

			}*/

		//Cambiar try catch y actualizar.
		else if(a==4) {
			String fechaaux=new String();
			int n = 0;
			System.out.print("Indique la fecha de nacimiento(dd/mm/yyyy) a buscar: ");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dnuevafecha = new Date();
			int cont=1;
			while(cont!=0) {
				cont=0;
				try {
					fechaaux = sc.nextLine();
					dnuevafecha = formatter.parse(fechaaux);
				} catch (ParseException e1) {
					cont++;
					System.out.print("Fecha mal introducida. Vuelva a introducirla(dd/MM/yyyy): ");
				}
			}
			//Busca los contactos que tengan el interes seleccionado arriba

			for(Contacto d: this.listaContactos) {
				if(d.getFechanacimiento().equals(dnuevafecha)) {
					aux.add(d);			
				}
			}


			if(aux.size()==0) {
				System.out.println("No existe ningun contacto con dicha fecha de nacimiento");
				return null;

			}

			if(aux.size()==1) {
				return aux.get(0);
			}

			//Imprime todos los contactos con dicha fecha.

			for(Integer i=0;i<aux.size();i++) {
				System.out.println(i.toString()+"Nombre: "+aux.get(i).getNombre()+" Email: "+ aux.get(i).getEmail());
			}

			System.out.println("Selecciona el contacto buscado");

			int seleccion2=sc.nextInt();
			sc.nextLine();

			for(int i=0;i<aux.size();i++) {

				if(i==seleccion2) {
					System.out.println("Contacto Seleccionado");
					buscado=aux.get(i);

					break;
				}
			}
			return buscado;

		}

		//sc.close();
		//Try catch en el main.
		return null;
	}
	
	/**
	 * Este método se encarga de dar de alta a un contacto.
	*/

	public void darAlta() throws IOException {
		
			Scanner sc = new Scanner(System.in);
			String nuevonombre;
			
			System.out.print("Introduzca el nuevo nombre: ");
			nuevonombre = sc.nextLine();
			
			
			String nuevoapellido;
			System.out.print("Introduzca el nuevo apellido: ");
			nuevoapellido = sc.nextLine();
			
			Boolean email= true;
			String nuevoemail=new String();
			
			while(email) {
				
				System.out.print("Introduzca el email: ");
				nuevoemail = sc.nextLine();
				
				if(this.listaContactos.size()==0) {
					email=false;
					continue;
				}
				
				for (Contacto myVar : this.listaContactos) {
					if(nuevoemail.equals(myVar.getEmail())) {
							
					}
					else {
						email=false;
					}
				}
				if(email) {
					System.out.println("Dicho email esta ya en uso");
				}
				
			}
			
			String nuevafecha="01/01/1970";
			System.out.print("Introduzca la nueva fecha de nacimiento(dd/mm/yyyy): ");
			nuevafecha = sc.nextLine();
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 
			Date dnuevafecha = null;
			try {
				dnuevafecha = formatter.parse(nuevafecha);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			
			ArrayList<Intereses> intereses=new ArrayList<Intereses>();
			Integer neweleccion=0;
			
			Boolean condicion=true;
			
			while(condicion) {
				
				System.out.println("Seleccione un nuevo interes: ");
				
				for (Intereses myVar : Intereses.values()) {
					System.out.println(myVar.getId()+" "+myVar.getInteres());
				}
				
				int newinteres= sc.nextInt();
				sc.nextLine();
				
				
				for (Intereses myVar : Intereses.values()) {
					if(newinteres==myVar.getId()) {
						intereses.add(myVar);
					}
				}
				
				System.out.println("Desea añadir mas intereses: 1. Si 2. No");
				neweleccion=sc.nextInt();
				sc.nextLine();
				if(neweleccion!=1) {
					condicion=false;
				}
				
			
			}
			
			//NO CERRAR SCANNERS, NO FUNCIONA EL MAIN.
			//sc.close();
			
			Contacto e=new Contacto(nuevonombre,nuevoapellido,dnuevafecha,nuevoemail,intereses);
			
			this.listaContactos.add(e);
			
			this.guardarDatos();
		
	}
	
	/**
	 * Este método se encarga de dar de baja a un contacto.
	 * @param Contacto que desea dar de baja
	*/
	public void darBaja(Contacto e) throws FileNotFoundException, IOException {
		
		for(int i=0; i<this.listaContactos.size();i++) {
			if(e.getEmail().equals(this.listaContactos.get(i).getEmail())) {
				this.listaContactos.remove(i);
			}
		}
		
		this.guardarDatos();
	}
	
	/**
	 * Este método se encarga de actualizar un contacto.
	*/
	public void actualizarContacto(Contacto e) {
		
		
		System.out.println("Que quieres modificar: 1. Nombre 2. Apellidos 3. Email 4. Fecha Nacimiento 5. Intereses");
		Scanner sc = new Scanner(System.in);
		Scanner sl = new Scanner(System.in);
		System.out.print("Introduzca un número entero: ");
		Integer a = sc.nextInt();
		
		
		if(a==1) {
			String nuevonombre;
			System.out.print("Introduzca el nuevo nombre: ");
			nuevonombre = sl.nextLine();
			e.setNombre(nuevonombre);
		}
		
		else if(a==2) {
			String nuevoapellido;
			System.out.print("Introduzca el nuevo apellido: ");
			nuevoapellido = sl.nextLine();
			e.setApellidos(nuevoapellido);
		}
		
		else if(a==3) {
			String nuevoemail;
			System.out.print("Introduzca el nuevo email: ");
			nuevoemail = sl.nextLine();
			e.setEmail(nuevoemail);
		}
		
		else if(a==4) {
			String nuevafecha="01/01/1970";
			System.out.print("Introduzca la nueva fecha de nacimiento(dd/mm/yyyy): ");
			nuevafecha = sl.nextLine();
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 
			Date dnuevafecha = new Date();
			int cont=1;
			while(cont!=0) {
				cont=0;
				try {
					nuevafecha = sc.nextLine();
					dnuevafecha = formatter.parse(nuevafecha);
				} catch (ParseException e1) {
					System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy hh:mm:ss): ");
					cont++;
				}
			}
			
			e.setFechanacimiento(dnuevafecha);
		}
		
		else if(a==5) {
			ArrayList<Intereses> intereses;
			intereses=e.getIntereses();
			
			Integer eleccion;
			System.out.println("¿Desea eliminar algun inter			es? 1.Si 2.No");
			eleccion = sc.nextInt();
			
			
			if(eleccion==1) {
				System.out.println("Actuales Intereses");
				for(int i=1; i<=intereses.size();i++) {
				
					System.out.println(i+intereses.get(i-1).getInteres());
				}
				
				System.out.println("Que interes desea eliminar");
				
				
				Integer linea;
				linea = sc.nextInt();
				intereses.remove(linea-1);
				e.setIntereses(intereses);
			}
			else {
				
				System.out.println("Seleccione un nuevo interes: ");
				int newinteres=0;
				for (Intereses myVar : Intereses.values()) {
					System.out.println(myVar.getId()+" "+myVar.getInteres());
				}
				
				newinteres= sc.nextInt();
				sc.nextLine();
				
				for (Intereses myVar : Intereses.values()) {
					if(newinteres==myVar.getId()) {
						intereses.add(myVar);
						e.setIntereses(intereses);
					}
				}
			}
			
			
		}
		
		//sc.close();
		
	}
	
	/**
	 * Este método se encarga de leer los contactos del fichero.
	 * @return Instancia única de GestorContactos.
	*/
	public void cargarDatos() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream file = new ObjectInputStream(new FileInputStream("fichej1.dat"));
		ArrayList<Intereses> aux=new ArrayList<Intereses>();
		Date auxi= new Date();
		Contacto clase= new Contacto("Auxiliar","Auxiliar",auxi, "auxiliar@hotmail.es",aux);
		
		while(clase!=null) {
			 try {
				 clase = (Contacto) file.readObject(); 
		         } catch (EOFException e) {
		            System.out.println("");
		            System.out.println("Contactos cargados correctamente");
		            break;
		         } 
			
			this.listaContactos.add(clase);        	
		}
		file.close();
		 
		
	}
	
	/**
	 * Este método se encarga de guardar los contactos en el fichero de datos.
	*/
	public void guardarDatos() throws FileNotFoundException, IOException {
		
		ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream( "fichej1.dat" ));
		Date auxi= new Date();
        ArrayList<Intereses> aux=new ArrayList<Intereses>();
		Contacto auxc=new Contacto("Auxiliar","Auxiliar",auxi, "auxiliar@hotmail.es",aux);
		
		for(int i=0; i<this.listaContactos.size();i++) {
			
			auxc=this.listaContactos.get(i);
	        file.writeObject(auxc);
			
		}
        
        file.close();
	}
	
	/**
	 * Este método se encarga de imprimir los datos de todos los contactos.
	*/
	public void imprimirDatos() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		for(int i=0; i<this.listaContactos.size();i++) {
			
			consultarContacto(this.listaContactos.get(i));
		}
	
	}
	
	/**
	 * Este método se encarga de imprimir los datos de un contacto.
	*/
	public void consultarContacto(Contacto e) {
		String cadena=new String();
		System.out.println("Nombre: "+e.getNombre()+" Apellidos: "+ e.getApellidos()+" Email: "+e.getEmail()+" Fecha de Nacimiento: "+ e.getFechanacimiento());
		for(int i=0; i<e.getIntereses().size();i++) {
			cadena=e.getIntereses().get(i).getInteres();
			
			System.out.println(cadena);
		}
	}

}
