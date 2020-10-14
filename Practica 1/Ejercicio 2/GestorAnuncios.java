package practica1;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import practica1.Anuncio.Estados;

public class GestorAnuncios {
	
	private static GestorAnuncios instance =null;
	
	private Intereses claseintereses=Intereses.getInstance();
	private GestorContactos clasecontactos=GestorContactos.getInstance();

	private ArrayList <Anuncio> listaAnuncios;
	

	/**
	 * Este método se encarga de crear una instancia en el caso de que no haya una ya creada. Patron de diseño Singleton
	 * @return Instancia única de GestorAnuncios.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	*/
	public static GestorAnuncios getInstance() throws FileNotFoundException, ClassNotFoundException, IOException {
		
		if(instance==null) {
			instance=new GestorAnuncios();
		}
		return instance;
	}
	
	/**
	 * Constructor de la clase GestorContactos.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	*/
	private GestorAnuncios() throws FileNotFoundException, ClassNotFoundException, IOException {
		
		listaAnuncios=new ArrayList<Anuncio>();
		cargarAnuncios();
		//Cargar del .dat.
	}
	
	public ArrayList<Anuncio> getListaAnuncios() {
		return listaAnuncios;
	}

	public void setListaAnuncios(ArrayList<Anuncio> listaAnuncios) {
		this.listaAnuncios = listaAnuncios;
	}

	public void guardarAnuncio() throws FileNotFoundException, IOException {
		
		
		ObjectOutputStream general = new ObjectOutputStream(new FileOutputStream( "general.dat" ));
		ObjectOutputStream tematico = new ObjectOutputStream(new FileOutputStream( "tematico.dat" ));
		ObjectOutputStream flash = new ObjectOutputStream(new FileOutputStream( "flash.dat" ));
		ObjectOutputStream individualizado = new ObjectOutputStream(new FileOutputStream( "individualizado.dat" ));
		String string=new String();
		
		Anuncio auxc=null;
		//Esto habria que hacer algo por que si no se llama practica 1 fallara, no se si el jar hace que siempre se llame practica 1.
		for(int i=0; i<this.listaAnuncios.size();i++) {
			
			auxc=this.listaAnuncios.get(i);
			Class<? extends Anuncio> a=auxc.getClass();
			string=a.toString();
			
			//System.out.println(string);
			if(string.equals("class practica1.AnuncioTematico")) {
				tematico.writeObject(auxc);
			}
			else if(string.equals("class practica1.AnuncioFlash")) {
				flash.writeObject(auxc);
			}
			else if(string.equals("class practica1.AnuncioIndividualizado")) {
				individualizado.writeObject(auxc);
			}
			else if(string.equals("class practica1.AnuncioGeneral")) {
				general.writeObject(auxc);
			}
	        	
			
		}
        
        	general.close();
        	tematico.close();
        	flash.close();
        	individualizado.close();
	}
	
	public void cargarAnuncios() throws FileNotFoundException, IOException, ClassNotFoundException {
		try {
		ObjectInputStream tematico = new ObjectInputStream(new FileInputStream("tematico.dat"));
		ObjectInputStream flash = new ObjectInputStream(new FileInputStream("flash.dat"));
		ObjectInputStream individualizado = new ObjectInputStream(new FileInputStream("individualizado.dat"));
		ObjectInputStream general = new ObjectInputStream(new FileInputStream("general.dat"));
		
		//ArrayList<String> aux=new ArrayList<String>();
		//Esto estaba aqui pero no se porque si falla el programa es esto.
		
		Anuncio clase= null;
		do {
			try {
				 clase = (AnuncioTematico) tematico.readObject(); 
		         } catch (EOFException e) {
		            System.out.println("");
		            System.out.println("Anuncios Tematicos cargados correctamente");
		            break;
		         } 
			
			this.listaAnuncios.add(clase);
		}
		while(clase!=null); 
		tematico.close();
		
		do {
			try {
				 clase = (AnuncioFlash) flash.readObject(); 
		         } catch (EOFException e) {
		            System.out.println("");
		            System.out.println("Anuncios Flash cargados correctamente");
		            break;
		         } 
			
			this.listaAnuncios.add(clase);
		}
		while(clase!=null); 
		flash.close();
		
		do {
			try {
				 clase = (AnuncioIndividualizado) individualizado.readObject(); 
		         } catch (EOFException e) {
		            System.out.println("");
		            System.out.println("Anuncios Individualizados cargados correctamente");
		            break;
		         } 
			
			this.listaAnuncios.add(clase);
		}
		while(clase!=null); 
		individualizado.close();
		
		do {
			try {
				 clase = (AnuncioGeneral) general.readObject(); 
		         } catch (EOFException e) {
		            System.out.println("");
		            System.out.println("Anuncios Generales cargados correctamente");
		            break;
		         } 
			
			this.listaAnuncios.add(clase);
		}
		while(clase!=null); 
		general.close();
		
		}catch(FileNotFoundException e) {
			guardarAnuncio();
		}
		
	}
	
	
	public void addNewAnuncio(Anuncio a) throws FileNotFoundException, IOException {
		
		this.listaAnuncios.add(a);
		guardarAnuncio();
	}
	
	public void publicarAnuncio() throws FileNotFoundException, IOException {
		Scanner sc=new Scanner(System.in);
		Anuncio buscado = null;
		int cont=0;
		for(int i=0;i<this.listaAnuncios.size();i++) {
			if(listaAnuncios.get(i).getEstado().getId()==1) {
				System.out.println("Id: "+listaAnuncios.get(i).getId()+" Titulo: "+ listaAnuncios.get(i).getTitulo());
				cont++;
			}
			
		}
		if(cont==0) {
			System.out.println("No hay anuncios para publicar");
			return ;
		}
		System.out.print("Selecciona el anuncio a publicar: ");
		
		int seleccion=sc.nextInt();
		
		for(int i=0;i<this.listaAnuncios.size();i++) {
			
			if(listaAnuncios.get(i).getId()==seleccion) {
				System.out.println("Anuncio seleccionado");
				buscado=this.listaAnuncios.get(i);
				
			}
		}
		
		Estados estado;
		if(!(buscado.getClass().toString().equals("class practica1.AnuncioFlash"))) {
			estado=Estados.Publicado;
		}
		
		else {
			//Hacer algo para cambiar estados
			Date fechaActual=new Date();
			//Puede ser que este al reves. Camiar < >.
			if( (fechaActual.compareTo(((AnuncioFlash) buscado).getFechaInicio())>0) & (fechaActual.compareTo(((AnuncioFlash) buscado).getFechaFinal())<0) ){
				 estado=Estados.Publicado;
			}
			else {
				 estado=Estados.En_espera;
			}
			
		}
		
		
		try {
		buscado.setEstado(estado);
		guardarAnuncio();
		}catch(NullPointerException e) {
			System.out.println("Anuncio no valido");
		}
		
		
		
	}
	
	public void modificarAnuncio(Anuncio e) {
		
		String string = new String();
		Class<? extends Anuncio> a=e.getClass();
		string=a.toString();
		
		if(e.getEstado().getId()>2) {
			System.out.println("No se puede modificar un anuncio ya publicado/archivado ");
		}
		
		//System.out.println(string);
		if(string.equals("class practica1.AnuncioTematico")) {
			modificarAnuncioTematico(e,claseintereses.getIntereses());
		}
		else if(string.equals("class practica1.AnuncioFlash")) {
			modificarAnuncioFlash(e);
		}
		else if(string.equals("class practica1.AnuncioIndividualizado")) {
			modificarAnuncioIndividualizado(e,clasecontactos.getContactos());
		}
		else if(string.equals("class practica1.AnuncioGeneral")) {
			modificarAnuncioGeneral(e);
		}
		
		
		
	}
	
	public void modificarAnuncioTematico(Anuncio e, ArrayList<String> intereses){
		
		Scanner sc=new Scanner(System.in);
		Scanner sl=new Scanner(System.in);
		
		System.out.println("Que quieres modificar: ");
		System.out.println("1. Titulo ");
		System.out.println("2. Cuerpo ");
		System.out.println("3. Intereses");
		
		int a=sc.nextInt();
		
		if(a==1) {
			
			System.out.print("Introduce el nuevo titulo: ");
			String titulo=new String();
			titulo=sl.nextLine();
			e.setTitulo(titulo);
			
		}
		
		else if(a==2) {
			System.out.print("Introduce el nuevo Cuerpo: ");
			String cuerpo=new String();
			cuerpo=sl.nextLine();
			e.setCuerpo(cuerpo);
		}
		
		else if(a==3) {
			ArrayList<String> actuales=new ArrayList<String>();
			actuales=((AnuncioTematico) e).getIntereses();
			System.out.print("Que desea eliminar(1) o añadir(2) un intereses del anuncio: "); 
			a=sc.nextInt();
			if(a==1) {
				System.out.println("Cual desea eliminar: ");
				Integer cont=0;
				for(String var: actuales) {
					System.out.println(cont.toString()+var);
					cont++;
				}
				a=sc.nextInt();
				
				actuales.remove(a);
				
				((AnuncioTematico) e).setIntereses(actuales);
				
			}
			else if(a==2) {
				
				System.out.println("Cual desea añadir: ");
				Integer cont=0;
				for(String var: intereses) {
					System.out.println(cont.toString()+var);
					cont++;
				}
				a=sc.nextInt();
				
				for(int i=0; i<intereses.size();i++) {
					
					if(i==a) {
						actuales.add(intereses.get(a));
					}
				}
				
				((AnuncioTematico) e).setIntereses(actuales);
				
			}
			
			else {
				System.out.print("Opcion no valida");
			}
		}
		
		else {
			System.out.print("Opcion no valida");
		}
	}
	
	public void modificarAnuncioFlash(Anuncio e){
		
		// Fecha publicacion Fecha archivacion.
		
		Scanner sc=new Scanner(System.in);
		Scanner sl=new Scanner(System.in);
		
		System.out.println("Que quieres modificar: ");
		System.out.println("1. Titulo ");
		System.out.println("2. Cuerpo ");
		System.out.println("3. Fecha Inicio");
		System.out.println("4. Fecha Final");
		
		int a=sc.nextInt();
		
		if(a==1) {
			
			System.out.print("Introduce el nuevo titulo: ");
			String titulo=new String();
			titulo=sl.nextLine();
			e.setTitulo(titulo);
			
		}
		
		else if(a==2) {
			System.out.print("Introduce el nuevo Cuerpo: ");
			String cuerpo=new String();
			cuerpo=sl.nextLine();
			e.setCuerpo(cuerpo);
		}
		
		else if(a==3) {
			
			Date fechaInicio=new Date();
			System.out.print("Introduzca la fecha de inicio(dd/mm/yyyy hh:mm:ss): ");
			String fechain=new String();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			fechain = sc.nextLine();
			try {
				fechaInicio = formatter.parse(fechain);
			} catch (ParseException e1) {
				System.out.println("Error con la fecha");
				e1.printStackTrace();
			}
			((AnuncioFlash) e).setFechaInicio(fechaInicio);
			
		}
		
		else if(a==4) {
			
			Date fechaFinal=new Date();
			System.out.print("Introduzca la fecha final(dd/mm/yyyy hh:mm:ss): ");
			String fechafin=new String();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			fechafin = sc.nextLine();
			try {
				fechaFinal = formatter.parse(fechafin);
			} catch (ParseException e2) {
				System.out.println("Error con la fecha");
				e2.printStackTrace();
			}
			
			((AnuncioFlash) e).setFechaFinal(fechaFinal);
		}
		
		else {
			System.out.print("Opcion no valida");
		}
		
	}

	public void modificarAnuncioIndividualizado(Anuncio e, ArrayList<Contacto> destinatarios){
	
		// Destinatarios
		
		Scanner sc=new Scanner(System.in);
		Scanner sl=new Scanner(System.in);
		
		System.out.println("Que quieres modificar: ");
		System.out.println("1. Titulo ");
		System.out.println("2. Cuerpo ");
		System.out.println("3. Destinatarios");
		
		int a=sc.nextInt();
		
		if(a==1) {
			
			System.out.print("Introduce el nuevo titulo: ");
			String titulo=new String();
			titulo=sl.nextLine();
			e.setTitulo(titulo);
			
		}
		
		else if(a==2) {
			System.out.print("Introduce el nuevo Cuerpo: ");
			String cuerpo=new String();
			cuerpo=sl.nextLine();
			e.setCuerpo(cuerpo);
		}
		
		else if(a==3) {
			ArrayList<Contacto> actuales=new ArrayList<Contacto>();
			actuales=((AnuncioIndividualizado) e).getDestinatarios();
			System.out.print("Que desea eliminar(1) o añadir(2) un destinatario del anuncio: "); 
			a=sc.nextInt();
			if(a==1) {
				System.out.println("Cual desea eliminar: ");
				Integer cont=0;
				for(Contacto var: actuales) {
					System.out.println(cont.toString()+var.getEmail());
					cont++;
				}
				a=sc.nextInt();
				
				actuales.remove(a);
				
				((AnuncioTematico) e).setDestinatarios(actuales);
				
			}
			else if(a==2) {
				
				System.out.println("Cual desea añadir: ");
				Integer cont=0;
				for(Contacto var: destinatarios) {
					System.out.println(cont.toString()+var.getEmail());
					cont++;
				}
				a=sc.nextInt();
				
				for(int i=0; i<destinatarios.size();i++) {
					
					if(i==a) {
						actuales.add(destinatarios.get(a));
					}
				}
				
				((AnuncioIndividualizado) e).setDestinatarios(actuales);
				
			}
			
			else {
				System.out.print("Opcion no valida");
			}
		}
		
		else {
			System.out.print("Opcion no valida");
		}
	}
	
	public void modificarAnuncioGeneral(Anuncio e){
		Scanner sc=new Scanner(System.in);
		Scanner sl=new Scanner(System.in);
		
		System.out.println("Que quieres modificar: ");
		System.out.println("1. Titulo ");
		System.out.println("2. Cuerpo ");
		
		int a=sc.nextInt();
		
		if(a==1) {
			
			System.out.print("Introduce el nuevo titulo: ");
			String titulo=new String();
			titulo=sl.nextLine();
			e.setTitulo(titulo);
			
		}
		
		else if(a==2) {
			System.out.print("Introduce el nuevo Cuerpo: ");
			String cuerpo=new String();
			cuerpo=sl.nextLine();
			e.setCuerpo(cuerpo);
		}
		
		else {
			System.out.print("Opcion no valida");
		}
		
		
	}
	
	public void archivarAnuncio() throws FileNotFoundException, IOException {
		
		Scanner sc=new Scanner(System.in);
		Anuncio buscado = null;
		int cont=0;
		for(int i=0;i<this.listaAnuncios.size();i++) {
			if(listaAnuncios.get(i).getEstado().getId()==3) {
				System.out.println("Id: "+listaAnuncios.get(i).getId()+" Titulo: "+ listaAnuncios.get(i).getTitulo());
				cont++;
			}
			
		}
		
		if(cont==0) {
			System.out.println("No hay anuncios para publicar");
			return ;
		}
			
		System.out.println("Selecciona el anuncio a archivar");
		
		int seleccion=sc.nextInt();
		
		for(int i=0;i<this.listaAnuncios.size();i++) {
			
			if(listaAnuncios.get(i).getId()==seleccion) {
				System.out.println("Anuncio seleccionado");
				buscado=this.listaAnuncios.get(i);
				//Comprobar break
				break;
			}
		}
		Estados estado=Estados.Archivado;
		buscado.setEstado(estado);
		
		guardarAnuncio();
	}
	
	public Anuncio buscarAnuncio() {
		int opcion;
		Scanner sc= new Scanner(System.in);
		Scanner sl= new Scanner(System.in);
		System.out.println("Buscar por: 1. Fecha 2.Interes 3. Propietario 4. Destinatario");
		//SI HAY UN NULLPOINTEREXCEPTION, HACER ANUNCIO AUXILIAR;
		Anuncio e=null;
        opcion = sc.nextInt();
        //No se puede hacer un nextline() despues de un nextint()
        
        if (opcion==1) {
        	String fecha=new String();
			System.out.print("Introduzca la fecha: ");
			fecha = sl.nextLine();
        	e=buscarFecha(fecha);
        }
        
        else if (opcion==2) {
        	String interes=new String();
			System.out.print("Introduzca el interes: ");
			interes = sl.nextLine();
        	e=buscarIntereses(interes,claseintereses.getIntereses());
        }
        
        else if (opcion==3) {
        	String nombre=new String();
			System.out.print("Introduzca el email del propietario: ");
			nombre = sl.nextLine();
        	e=buscarPropietario(nombre);
        }
        
        else if (opcion==4) {
        	String destinatario=new String();
			System.out.print("Introduzca el email del destinatario: ");
			destinatario = sl.nextLine();
        	e=buscarDestinatario(destinatario);
        }
        
        
        else {
        	
        	System.out.println("Opcion no valida");
        	return null;
        }
        
        return e;
 
	}
	
	//EN ESTAS FUNCIONES COMO PUEDE HABER MAS DE UN ANUNCIO QUE CUMPLA LAS CONDICIONES AÑADIR SELECCION DE CUAL ES.
	//Yo creo que en estas funciones deben devolver un solo anuncio, para que se puedan usar en la funcion del main(Mirar como esta hecho)
	
	//Sin comprobar
	public Anuncio buscarFecha(String fecha) {
		ArrayList<Anuncio> anunciosusuario = new ArrayList<Anuncio>();
		Scanner sc = new Scanner(System.in);
		String nuevafecha="01/01/1970";
		System.out.print("Introduzca la fecha de publicacion: ");
		nuevafecha = sc.nextLine();
		Anuncio buscado= null;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		 
		Date dnuevafecha = null;
		try {
			dnuevafecha = formatter.parse(nuevafecha);
		} catch (ParseException e1) {
			
			e1.printStackTrace();
		}
		for(int i=0 ; i<getListaAnuncios().size() ; i++) {
			if(getListaAnuncios().get(i).getFecha() == dnuevafecha) {
				 anunciosusuario.add(getListaAnuncios().get(i));
			}
		}
		
		//Seleccionar anuncio buscado

		for(int i=0;i<anunciosusuario.size();i++) {
			System.out.println("Id: "+anunciosusuario.get(i).getId()+" Titulo: "+ anunciosusuario.get(i).getTitulo());
			
		}
		
		System.out.println("Selecciona el id del anuncio");
			
		
		int seleccion=sc.nextInt();
		
		for(int i=0;i<anunciosusuario.size();i++) {
			
			if(anunciosusuario.get(i).getId()==seleccion) {
				System.out.println("Anuncio seleccionado");
				buscado=anunciosusuario.get(i);
				//Comprobar break
				break;
			}
		}
		

		return buscado;
	}
	
	//Sin implementar
	public Anuncio buscarIntereses(String interes, ArrayList<String> intereses) {
		return null;
		
	}
	
	
	public Anuncio buscarPropietario(String propietario) {
		ArrayList<Anuncio> anunciosusuario = new ArrayList<Anuncio>();
		String email;
		System.out.println("Introduzca el email del propietario:");
		Scanner sc = new Scanner(System.in);
		email = sc.nextLine();
		Anuncio a = null;
		Anuncio buscado=null;
		for(int i=0 ; i<getListaAnuncios().size(); i++) {
			if(getListaAnuncios().get(i).getUsuario().getEmail()==email) {
				
				a=getListaAnuncios().get(i);
				
				anunciosusuario.add(a);
			}
		}
		
		//Aqui seleccion de cual es.
		
		for(int i=0;i<anunciosusuario.size();i++) {
			System.out.println("Id: "+anunciosusuario.get(i).getId()+" Titulo: "+ anunciosusuario.get(i).getTitulo());
			
		}
		
		System.out.println("Selecciona el id del anuncio");
			
		
		int seleccion=sc.nextInt();
		
		for(int i=0;i<anunciosusuario.size();i++) {
			
			if(anunciosusuario.get(i).getId()==seleccion) {
				System.out.println("Anuncio seleccionado");
				buscado=anunciosusuario.get(i);
				//Comprobar break
				break;
			}
		}
		

		return buscado;
		
	}
	

	public Anuncio buscarDestinatario(String email) {
		ArrayList<Anuncio> anunciosusuario = new ArrayList<Anuncio>();
		Anuncio a = null;
		Scanner sc=new Scanner(System.in);
		Anuncio buscado=null;
		
		for(int i=0 ; i<getListaAnuncios().size(); i++) {
			for(int n=0 ; n<getListaAnuncios().get(i).getDestinatarios().size() ; n++) {
				if(getListaAnuncios().get(i).getDestinatarios().get(n).getEmail().equals(email)) {
					
					a=getListaAnuncios().get(i);
					
					anunciosusuario.add(a);
					
					break;
				}
			}
		}
		
		//Seleccion del anuncio buscado
		
		System.out.println("Anuncios encontrados: ");

		for(int i=0;i<anunciosusuario.size();i++) {
			System.out.println("Id: "+anunciosusuario.get(i).getId()+" Titulo: "+ anunciosusuario.get(i).getTitulo());
			
		}
		
		System.out.print("Selecciona el id del anuncio: ");
			
		
		int seleccion=sc.nextInt();
		
		for(int i=0;i<anunciosusuario.size();i++) {
			
			if(anunciosusuario.get(i).getId()==seleccion) {
				System.out.println("Anuncio seleccionado");
				buscado=anunciosusuario.get(i);
				//Comprobar break
				break;
			}
		}
		

		return buscado;
		
	}
	
	public void consultarAnuncio(Anuncio e) {
		
		//Esto mejor en cada clase de Anuncio hacer un metodo toString segun sus atributos y quitar el de la clase Anuncio.
		
		String cadena=new String();
		System.out.println("Id: "+e.getId()+" Titulo: "+ e.getTitulo()+" Cuerpo: "+e.getCuerpo()+" Propietario: "+ e.getUsuario().getEmail());
		System.out.println("Estado: " + e.getEstado().getEstados());
		System.out.println("Destinatarios: ");
		for(int i=0; i<e.getDestinatarios().size();i++) {
			cadena=e.getDestinatarios().get(i).getEmail();
			System.out.println(cadena);
		}
		System.out.println("");
		
	}
	
	public void mostrarAnuncios() {
		
		//Si se joden los ids o algo hacerlo cambiando un auxiliar en vez del genral.
		
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Como quiere que esten ordenados los anuncios: 1. Fecha 2. Propietario");
		int seleccion=sc.nextInt();
		
		if(seleccion==1) {
			setListaAnuncios(ordenarFecha());
		}
		else if(seleccion==2) {
			setListaAnuncios(ordenarPropietario());
		}
		else {
			System.out.println("Opcion no valida");
		}
		
		for(Anuncio a: getListaAnuncios()) {
			System.out.println("");
			consultarAnuncio(a);
		}
	}
	
	public ArrayList<Anuncio> ordenarPropietario() {
		ArrayList<Anuncio> aux= new ArrayList<Anuncio>();
		ArrayList<String> propietarios= new ArrayList<String>();
		ArrayList<Anuncio> ordenado= new ArrayList<Anuncio>();
		
		aux= getListaAnuncios();
		int i=0;
		for(Anuncio a: aux) {
			propietarios.add(a.getUsuario().getNombre());
		}
		Collections.sort(propietarios);
		
		//Revisar
		
		while(aux.size()!=0) {
			for(int j=0; j<getListaAnuncios().size();) {
				//Para depurar :)
				//System.out.println("Valor de i: "+i+"Valor de j: "+j+"Valor de prop: "+propietarios.get(i)+ "Valor de aux: "+aux.get(j).getUsuario().getNombre());
				if(propietarios.get(i).equals(aux.get(j).getUsuario().getNombre())) {
					ordenado.add(aux.get(j));
					
					propietarios.remove(i);
					aux.remove(j);
					j=0;
					
				}
				else {
					j++;
				}
				
			}
		}
		
		return ordenado;
	}
	
	public ArrayList<Anuncio> ordenarFecha() {
		//Esto hay que implementarlo como arriba, si lo de arriba esta bien seria solo cambiar el primer for
		
		ArrayList<Anuncio> aux= new ArrayList<Anuncio>();
		ArrayList<Date> fechas= new ArrayList<Date>();
		ArrayList<Anuncio> ordenado= new ArrayList<Anuncio>();
		
		aux= getListaAnuncios();
		int i=0;
		for(Anuncio a: aux) {
			fechas.add(a.getFecha());
		}
		
		Collections.sort(fechas);
		
		
		//Revisar
		
		while(aux.size()!=0) {
			for(int j=0; j<getListaAnuncios().size();) {
				//Para depurar :)
				//System.out.println("Valor de i: "+i+"Valor de j: "+j+"Valor de prop: "+propietarios.get(i)+ "Valor de aux: "+aux.get(j).getUsuario().getNombre());
				if(fechas.get(i).equals(aux.get(j).getFecha())) {
					ordenado.add(aux.get(j));
					
					fechas.remove(i);
					aux.remove(j);
					j=0;
					
				}
				else {
					j++;
				}
				
			}
		}
		
		return ordenado;
		
		
        
  
		
	}
	

}
