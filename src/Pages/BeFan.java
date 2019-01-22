package Pages;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

//import Tests.File;
import Tests.TestBase;

public class BeFan extends BasePage{

	//*********************************CONSTRUCTOR******************************************************//
	static WebDriver driver;
	
	public BeFan(WebDriver driver){
		this.driver = driver;
			PageFactory.initElements(driver, this);
	}
	
	
	//*********************************ELEMENTOS******************************************************//
	
	//Login
	@FindBy(name="username")
	private WebElement user;
	
	@FindBy(name="txtPass")
	private WebElement password;
	
	@FindBy(name="btnIngresar")
	private WebElement IngresarBoton;
	
	//Ventana Importacion
	@FindBy(css=".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-not-empty")
	private WebElement prefijo;
	
	@FindBy(css=".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")
	private WebElement cantidad;
	
	@FindBy(name="btnAgregar")
	private WebElement agregar;
	
	@FindBy(id="fileinput")
	private WebElement adjuntar;
	
	@FindBy(linkText="Aceptar")
	private WebElement aceptar;
	
	//Ventana gestion
	
	@FindBy(css=".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")
	private WebElement Estado;
	
	@FindBy(css=".form-control.ng-pristine.ng-valid.ng-empty.ng-touched")
	private WebElement nombreArchivo;
	
	@FindBy(id="dataPickerDesde")
	private WebElement fechaDesde;

	@FindBy(id="dataPickerHasta")
	private WebElement fechaHasta;
	
	
	//Mensajes
	@FindBy(xpath="//*[@id='NotUpdatedPhoneMessage']/div/p/p[2]/span/strong")
	private WebElement NotUpdatedPhoneMessage;
	
	
	//********************************METODOS*******************************************************//
	
	public static WebDriver initDriver() {
		
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
	    ChromeOptions options = new ChromeOptions();
	    options.addArguments("start-maximized");
	    driver = new ChromeDriver(options);
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    return driver;
	}
	
	public static void irABefan() {
		driver.get("http://befantest.personal.corp/#/signin");
	}
	public void loginBefan(String user, String password) {
		try {
		this.user.sendKeys(user);
		this.password.sendKeys(password);
		IngresarBoton.click();
		}catch(Exception e) {
			System.out.print("No se puede Ingresar en BeFan: ");
			e.printStackTrace();
		}
	}
	
	public void adjuntarArchivoBefan(String FileAddress) {
		adjuntar.sendKeys(FileAddress);
	}
	
	/**
	 * Selecciona una opcion de la lista despegable de SIM
	 * @param action
	 */
	public void opcionDeSim(String opcion) {
		List <WebElement> despegables=driver.findElements(By.className("dropdown-toggle"));
		for(WebElement op:despegables) {
			if(op.getText().toLowerCase().contains("sims"))
				op.click();
			}
		List<WebElement> opciones=driver.findElements(By.className("multi-column-dropdown")).get(1).findElements(By.tagName("li"));
		for(WebElement op:opciones) {
			if(op.getText().toLowerCase().contains(opcion.toLowerCase()))
				op.click();
			}
		
	}
	
	public void selectPrefijo(String prefijo) {
		Select Prefijo=new Select(this.prefijo);
		Prefijo.selectByVisibleText(prefijo);
	}
	
	public void setCantidad(String cantidad) {

		this.cantidad.sendKeys(cantidad);
	}

	//Victor
	//Menu Simcard-Importacion
	public void SISeleccionDeDeposito(String deposito) {
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), deposito);
	}
	
	public void SISeleccionDePrefijo (String prefijo) {
	
		if (driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).isEmpty()) {
			selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-valid.ng-dirty.ng-touched.ng-empty")).get(0), prefijo);
		} else {
		selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(0), prefijo);
		}
	}
	
	public void SISeleccionCantidadDePrefijo (String cantidadPrefijo) {
		
		if (driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).isEmpty()) {
			driver.findElement(By.cssSelector(".text.form-control.ng-valid.ng-dirty.ng-touched.ng-empty")).sendKeys(cantidadPrefijo);
		} else {
			driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys(cantidadPrefijo);
		}
	}
	
	public void SIClickAgregar() {
	driver.findElement(By.name("btnAgregar")).click();
	}
	
	public String SIMensajeModal() {
		String mensaje;
		mensaje = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[1]/div[1]/h3")).getText();
		return mensaje;
	}
	
	public String SIMensajeModalMasDeUnMensaje() {
		String mensaje;
		mensaje = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[1]/div[1]/h3/h2")).getText();
		return mensaje;
	}
	
	public void SIImportarArchivo(String path) {
	WebElement uploadElement = driver.findElement(By.id("fileinput"));
	uploadElement.sendKeys(path);
	}
	
	public void SIClickImportar() {
	driver.findElements(By.cssSelector(".btn.btn-primary")).get(2).click();
	}
	
	public String SICreacionArchivo(String nombreArch, String path, String serial1, String serial2) throws IOException {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
	LocalDateTime now = LocalDateTime.now(); 
	String time = dtf.format(now);
	path = path + "\\" + nombreArch + time + ".txt";
	File archivo = new File(path);
	archivo.createNewFile();
	if (serial2 == "") {
	FileOutputStream outputStream = new FileOutputStream(path);
    byte[] strToBytes = serial1.getBytes();
    outputStream.write(strToBytes);
    outputStream.close();
	}
	else
	{
	 String serialF = serial1 + "\n" + serial2;
		FileOutputStream outputStream = new FileOutputStream(path);
	    byte[] strToBytes = serialF.getBytes();
	    outputStream.write(strToBytes);
	    outputStream.close();
	}
	return path;
	}
	
	public void SIDesRenombreDeArchivo(String nombreArch, String path) {
	String[] parts = nombreArch.split("\\\\");
	String[] partes = parts[2].split("2");
	File archivo = new File(nombreArch);
	String path2 = path + "\\" + partes[0] + ".txt";
	File archivo2 = new File(path2);
	Boolean faio = archivo.renameTo(archivo2);
	}
	
	public void SIClickAceptarImportar() {
	driver.findElement(By.cssSelector(".btn.btn-link")).click();	
	}
	
	//Api BEFAN
	
	public void BefanProceso() throws Exception {

	        CloseableHttpClient httpclient = HttpClients.createDefault();
	/*        try {
	            HttpGet httpGet = new HttpGet("http://httpbin.org/get");
	            CloseableHttpResponse response1 = httpclient.execute(httpGet);
	            // The underlying HTTP connection is still held by the response object
	            // to allow the response content to be streamed directly from the network socket.
	            // In order to ensure correct deallocation of system resources
	            // the user MUST call CloseableHttpResponse#close() from a finally clause.
	            // Please note that if response content is not fully consumed the underlying
	            // connection cannot be safely re-used and will be shut down and discarded
	            // by the connection manager.
	            try {
	                System.out.println(response1.getStatusLine());
	                HttpEntity entity1 = response1.getEntity();
	                // do something useful with the response body
	                // and ensure it is fully consumed
	                EntityUtils.consume(entity1);
	            } finally {
	                response1.close();
	            }
	*/
	        try {
	            HttpPost httpPost = new HttpPost("https://apiu.telecom.com.ar/catalogo/api/ProcesoMasivo/IniciarProceso");
	            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	            httpPost.addHeader("Content-Type", "application/json");
	            httpPost.addHeader("Authorization", "Basic d2VidmFzOndlYnZhcw==");
	            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
	            CloseableHttpResponse response2 = httpclient.execute(httpPost);
	            System.out.println(response2);
	            
	            try {
	                System.out.println(response2.getStatusLine());
	                HttpEntity entity2 = response2.getEntity();
	                // do something useful with the response body
	                // and ensure it is fully consumed
	                EntityUtils.consume(entity2);
	            } finally {
	                response2.close();
	            }
	        } finally {
	            httpclient.close();
	        }
	    }
	
	//Menu Simcard-Gestion
	
	//Estados Procesado, En Proceso, Eliminado y Pendiente
	public void SGSeleccionEstado(String estado){
	selectByText(driver.findElement(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")), estado);
	}
	
	public void SGSeleccionDeposito(String deposito) {
	selectByText(driver.findElements(By.cssSelector(".text.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).get(1), deposito);
	}
	
	public void SGClickBuscar() {
	driver.findElement(By.cssSelector(".btn.btn-primary")).click();
	}
	
	public void SGFechaDesdeAhora() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
		LocalDateTime now = LocalDateTime.now(); 
		String time = dtf.format(now);
		String[] fecha = time.split("/");
		String izquierda = Keys.chord(Keys.ARROW_LEFT);
		String borrar = Keys.chord(Keys.BACK_SPACE);
		driver.findElement(By.id("dataPickerDesde")).click();
		driver.findElement(By.id("dataPickerDesde")).sendKeys(borrar + borrar + borrar + borrar + fecha[2] + izquierda + izquierda + izquierda + izquierda + izquierda + borrar + borrar + fecha[1] + izquierda + izquierda + izquierda + borrar + borrar + fecha[0]);
		sleep(500);		
	}
	
	public boolean SGLeerCampoYValidar(String nombreArch, String[] listaEstados, String[] listaResultados) {
	boolean resultado = false;
	int cont = 0;
	int cont2 = 4;
	int cont3 = 0;
	ArrayList<String> estados = new ArrayList<String>();
	ArrayList<String> resultados = new ArrayList<String>();
	
	List<WebElement> tabla = driver.findElements(By.cssSelector(".ng-binding"));
	String[] parts = nombreArch.split("\\\\");
	String[] partes = parts[2].split("\\.");
	for (WebElement x : tabla) {
		cont = cont + 1;
		if (x.getText().contains(partes[0]) && (listaEstados[0] != "" || listaResultados[0] != "")) {
		driver.findElements(By.cssSelector(".btn.btn-primary.btn-xs")).get((cont - 14) / 8).click();
		sleep(500);
		List<WebElement> tabla2 = driver.findElements(By.cssSelector(".padding-left-15.ng-binding"));
		for (cont2 = 7;cont2 < tabla2.size();cont2 = cont2 + 8) {
			estados.add(driver.findElements(By.cssSelector(".padding-left-15.ng-binding")).get(cont2).getText());
			resultados.add(driver.findElements(By.cssSelector(".padding-left-15")).get(cont2+1).getText());
			
			if (listaEstados[cont2/4-1].equals(estados.get(cont2/4-1))) {
			} else
			{
				cont3 = 1;
			}
			if (listaResultados[cont2/4-1].equals(resultados.get(cont2/4-1))) {
			} else {
				cont3 = 1;
			}
		}
		sleep(1000);
		driver.findElements(By.cssSelector(".btn.btn-link")).get(0).click();
		if (cont3 == 1) {
			resultado = false;
		} else {
			resultado = true;			
		}
		} else {
			if (x.getText().contains(partes[0])) {
				resultado = true;
			}
		}
		}
	return resultado;
	}
	
// Log out de befan
	
	public void LogOutBefan(WebDriver driver) {
		driver.findElements(By.cssSelector(".caret")).get(0).click();
		sleep(500);
		driver.findElement(By.name("salir")).click();
	}
	
	/**
	 * Hace click en un boton segun el Etiqueta del boton.
	 * Sirve para el boton Importar y el boton buscar de la ventana Gestion.
	 */
	public void clickEnBoton(String EtiquetaBoton) {
		List<WebElement> botones=driver.findElements(By.cssSelector(".btn.btn-primary"));
		System.out.println("Numero de Botones: "+botones.size());
	    for(WebElement opcion:botones)
	    	if(opcion.getText().equalsIgnoreCase(EtiquetaBoton))
	    		opcion.click();
	}
	
	public  int numeroDiasEntreDosFechas(Date fecha1, Date fecha2){
		
	     long startTime = fecha1.getTime();
	     long endTime = fecha2.getTime();
	     long diffTime = endTime - startTime;
	     return (int)TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS);
	}
	
	public Date fechaAvanzada() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, +1);
		cal.add(Calendar.MONTH, +1);
		//cal.add(Calendar.MINUTE, +1);
		date = cal.getTime();
		return (date);
	}
	
        
	
	/**Estados Predefinidos:
	 * 
	 * -Pendiente
	 * -Eliminado
	 * -En Proceso
	 * -Procesado.
	 * 
	 * @param estado
	 */
	public void selectEstado(String estado) {
		Select sEstado=new Select(this.Estado);
		sEstado.selectByVisibleText(estado);
	}
	
	public void setNombreArchivo(String nombreArchivo) {
		driver.findElement(By.className("col-lg-3")).findElement(By.tagName("input")).sendKeys(nombreArchivo);
		//this.nombreArchivo.sendKeys(nombreArchivo);
	}
	

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde.clear();
		this.fechaDesde.sendKeys(fechaDesde);
	}

	public void setFechaDesde() {
		this.fechaDesde.clear();
		this.fechaDesde.sendKeys("/00200F");
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta.clear();
		this.fechaHasta.sendKeys(fechaHasta);
	}
	
	public void SeleccionarFechas(String Desde, String Hasta) {
		setFechaDesde(Desde);
		setFechaHasta(Hasta);
	}
	
	public void clickAceptar() {
		this.aceptar.click();
	}
	
	public boolean verificarMensajeExitoso() {
		boolean confirmacion = false;
		for(WebElement x : driver.findElements(By.className("modal-body"))) {
			if(x.getText().toLowerCase().contains("satisfactoriamente")) {
				confirmacion = true;
			}
		}
		return confirmacion;
	}
	
	public void cerrar() {
	driver.findElement(By.xpath("//*[@class='btn btn-primary' and contains(text(), 'CERRAR')]")).click();
	}
	public void eliminar() {
	driver.findElement(By.xpath("//*[@class='btn btn-primary' and contains(text(), 'ELIMINAR')]")).click();
	}
	
	public boolean buscarRegion(String sRegion) {
		driver.findElement(By.xpath("//*[@type='search']")).clear();
		driver.findElement(By.xpath("//*[@type='search']")).sendKeys(sRegion);
		sleep(3000);
		WebElement wBody = driver.findElement(By.className("panel-data"));
		List<WebElement> wList = wBody.findElements(By.xpath("//*[@class='panel-group'] //*[@class='collapsed'] //*[@class='ng-binding']"));
		
		boolean bAssert = true;
		for (WebElement wAux : wList) {
			if (!wAux.getText().toLowerCase().contains(sRegion.toLowerCase())) {
				bAssert = false;
				break;
			}
		}
		
		return bAssert;
	}
	
	public List<String> agregarPrefijos(String sRegion) {
		buscarRegion(sRegion);
		WebElement wBody = driver.findElement(By.className("panel-data"));
		List<WebElement> wList = wBody.findElements(By.xpath("//*[@class='panel-group panel-group-alternative ng-scope']"));
		
		for (WebElement wAux : wList) {
			if (wAux.findElement(By.xpath("//*[@class='collapsed'] //*[@class='ng-binding']")).getText().equalsIgnoreCase(sRegion)) {
				wAux.click();
				wAux.findElement(By.xpath("//*[@class='row ng-scope'] //*[@class='btn btn-link']")).click();
				break;
			}
		}
		sleep(5000);
		List<String> sPrefijos = new ArrayList<String>();
		//List<WebElement> wPrefijos = driver.findElements(By.id("compatibility"));
		sPrefijos.add(driver.findElement(By.xpath("//*[@id='compatibility'][1] //label")).getText());
		System.out.println(driver.findElement(By.xpath("//*[@id='compatibility'][1] //label")).getText());
		driver.findElement(By.xpath("//*[contains(@class,'check-filter')] [1]")).click();
		sPrefijos.add(driver.findElement(By.xpath("//*[@id='compatibility'][2] //label")).getText());
		System.out.println(driver.findElement(By.xpath("//*[@id='compatibility'][2] //label")).getText());
		driver.findElement(By.xpath("//*[@id='compatibility'][2] //label /.. /input")).click();
		driver.findElement(By.xpath("//*[@ng-show='mensajeAgregarCtrl.container.showConfirmation'] //*[@class='btn btn-primary']")).click();
		sleep(3000);
		verificarMensajeExitoso();
		driver.findElement(By.xpath("//*[contains(@ng-show, 'container.showSuccess')] //*[@class='btn btn-primary']")).click();
		driver.navigate().refresh();
		
		buscarRegion(sRegion);
		wBody = driver.findElement(By.className("panel-data"));
		wList = wBody.findElements(By.xpath("//*[@class='panel-group panel-group-alternative ng-scope']"));
		
		for (WebElement wAux2 : wList) {
			if (wAux2.findElement(By.xpath("//*[@class='collapsed'] //*[@class='ng-binding']")).getText().equalsIgnoreCase(sRegion)) {
				wAux2.click();
				break;
			}
		}
		
		return sPrefijos;
	}
	
	public void buscarYAbrirRegion(String sRegion) {
		buscarRegion(sRegion);
		WebElement wBody = driver.findElement(By.className("panel-data"));
		List<WebElement> wList = wBody.findElements(By.xpath("//*[@class='panel-group panel-group-alternative ng-scope']"));
		
		for (WebElement wAux : wList) {
			if (wAux.findElement(By.xpath("//*[@class='collapsed'] //*[@class='ng-binding']")).getText().equalsIgnoreCase(sRegion)) {
				wAux.click();
				wAux.findElement(By.xpath("//*[@class='row ng-scope'] //*[@class='btn btn-link']")).click();
				break;
			}
		}
	}
	public void buscarR (String Region) {
	sleep(5000);
	driver.findElement(By.className("panel-data"));
	List <WebElement> region = driver.findElements(By.xpath("//*[@class='panel-group'] //*[@class='collapsed'] //*[@class='ng-binding']"));
	for(WebElement x : region) {
	if(x.getText().toLowerCase().contains(Region)) {
		System.out.println(x.getText());
		x.click();
		break;
		}
	}	
}
	
	public boolean regiontExists(WebElement element) throws InterruptedException {
	    sleep(2000);
		    try {
 		      boolean isDisplayed = element.getSize().height > 0;
		       return isDisplayed;
		    }   catch (Exception ex) {
		         return false;
		  }
	}
	
	public void region() throws InterruptedException {
		//String Region="";
		if(regiontExists(driver.findElement(By.cssSelector(".alert.alert-dismissable.alert-danger")))) {
			System.out.println(driver.findElement(By.cssSelector(".alert.alert-dismissable.alert-danger")).getText());
			driver.findElement(By.className("pull-right")).findElement(By.cssSelector(".btn.btn-link")).getText().equalsIgnoreCase("Cancelar");
			driver.findElement(By.className("pull-right")).findElement(By.cssSelector(".btn.btn-link")).click();
			}
		else {
			driver.findElement(By.xpath("//*[@class='btn btn-primary' and contains(text(), 'Agregar')]")).click();
			sleep(5000);
			
			Assert.assertTrue(verificarMensajeExitoso());
			driver.findElement(By.xpath("//*[@ng-show='mensajeAgregarRegionCtrl.container.showSuccess']//*[@class='btn btn-primary']")).click();
		}
		//return true;
	
}
}

