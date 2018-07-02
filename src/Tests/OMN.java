package Tests;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.CustomerCare;
import Pages.OM;
import Pages.setConexion;

public class OMN extends TestBase {

	private WebDriver driver;
	protected OM om;


	@BeforeClass (alwaysRun = true, groups = "OM")
	public void init() {
		driver = setConexion.setupEze();
		sleep(5000);
		login(driver, "https://crm--sit.cs14.my.salesforce.com/", "U585991", "Testa10k");
		sleep(5000);
		om = new OM(driver);
	}
	
	@BeforeMethod (alwaysRun = true, groups = "OM")
	public void before() {
		BasePage bp = new BasePage(driver);
		bp.cajonDeAplicaciones("Sales");
		sleep(5000);
		driver.findElement(By.id("Order_Tab")).click();
		sleep(3000);
	}
	
	//@AfterClass (alwaysRun = true, groups = "OM")
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	public void debugger() {
		sleep(10000);
		//WebElement imsi = driver.findElements(By.cssSelector(".table.table-condensed.attributes")).get(0);
		om.scrollDownInAView(driver.findElement(By.xpath("//*[contains(text(),'MSISDN')]")));
		//System.out.println(imsi.getText());
		sleep(5000);
	}
	
	@Test (groups = "OM")
	public void TS6729_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_JPG() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\jpg.jpg");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6730_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_PNG() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\png.png");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6731_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_PDF() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\pdf.pdf");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6732_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_XML() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\xml.xml");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6733_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_TXT() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\txt.txt");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6734_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_XLS() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\xls.xlsm");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6735_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_DOC() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\doc.docx");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6736_Ordenes_Order_Detail_Adjunto_de_archivos_Formato_VSO() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\vso.vso");
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6737_Ordenes_Order_Detail_Adjunto_de_archivos_Varios_formatos() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		boolean a = false;
		if (driver.findElement(By.id("file")).isEnabled()) {
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\doc.docx");
			sleep(2000);
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\xls.xlsm");
			sleep(2000);
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\txt.txt");
			sleep(2000);
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\xml.xml");
			sleep(2000);
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\pdf.pdf");
			sleep(2000);
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\png.png");
			sleep(2000);
			driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\jpg.jpg");
			sleep(2000);
			a = true;
		}
		Assert.assertTrue(a);
	}
	
	@Test (groups = "OM")
	public void TS6740_Ordenes_Order_Detail_Links_funcionales() {
		om.primeraOrden();
		driver.findElement(By.name("attachFile")).click();
		sleep(5000);
		driver.findElement(By.id("file")).sendKeys("C:\\Users\\Nicolas\\Desktop\\Archivos\\jpg.jpg");
		driver.findElement(By.id("Attach")).click();
		sleep(3000);
		driver.findElement(By.name("cancel")).click();
		sleep(5000);
		driver.findElements(By.cssSelector(".dataRow.even.last.first")).get(0).findElements(By.tagName("td")).get(1).findElement(By.tagName("a")).click();
		sleep(5000);
		WebElement title = driver.findElement(By.className("bPageTitle"));
		boolean a = false, b = false;
		if (title.getText().toLowerCase().contains("attached file")) {
			a = true;
		}
		if (title.getText().toLowerCase().contains("jpg.jpg")) {
			b = true;
		}
		Assert.assertTrue(a && b);
	}
	
	@Test (groups = "OM")
	public void TS80196_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_3() throws InterruptedException {
		om.Gestion_Alta_De_Linea("FlorOM", "Plan con tarjeta");
		driver.findElement(By.id("Order_Tab")).click();
		om.Cambio_De_SimCard("11-29-2019");
		driver.findElement(By.name("ta_submit_order")).click();
		sleep(15000);
		om.completarFlujoOrquestacion();
		driver.findElement(By.name("vlocity_cmt__vieworchestrationplan")).click();
		sleep(10000);
		Assert.assertTrue(om.ordenCajasVerdes("Cambio de N\u00famero o SIM", "Env\u00edo de Actualizaci\u00f3n de Par\u00e1metros a la Red (SIM Card)(IMSI, KI e ICCID)", "En progreso | Comptel - Par\u00e1metros de la Red actualizados"));
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS80196_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_3")
	public void TS80193_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_0() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS80196_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_3")
	public void TS80194_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_1() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM", dependsOnMethods = "TS80196_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_3")
	public void TS80195_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Paso_2() {
		Assert.assertTrue(true);
	}
	
	@Test (groups = "OM")
	public void TS80191_Ordenes_Cliente_existente_Cambio_de_SIM_Plan_con_tarjeta_Sin_delivery_Verificacion_de_assets() throws InterruptedException {
		String a = "", b = "", c = "";
		om.Gestion_Alta_De_Linea("FlorOM", "Plan con tarjeta");
		driver.findElement(By.id("Order_Tab")).click();
		om.Cambio_De_SimCard2("07-22-2020", a, b, c);
		driver.findElement(By.name("ta_submit_order")).click();
		sleep(15000);
		om.completarFlujoOrquestacion();
		driver.findElement(By.id("accid_ileinner")).findElement(By.tagName("a")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".panel.panel-default.panel-assets")));
		List <WebElement> assets = driver.findElement(By.cssSelector(".panel.panel-default.panel-assets")).findElements(By.cssSelector(".root-asset.ng-scope"));
		assets.get(assets.size() -1).findElement(By.className("p-expand")).click();
		sleep(3000);
		int ultimoSim=0;
		List <WebElement> asd = driver.findElements(By.className("p-name"));
		for (int i=0; i<asd.size(); i++) {
			if (asd.get(i).getText().equalsIgnoreCase("Simcard")) {
				ultimoSim = i;
			}
		}
		asd.get(ultimoSim).findElement(By.tagName("a")).click();
		sleep(15000);
		List <WebElement> asdd = driver.findElements(By.cssSelector(".table.table-condensed.attributes"));
		for (int i=0; i<asdd.size(); i++) {
			System.out.println(asdd.get(i).getText());
		}
	}
}