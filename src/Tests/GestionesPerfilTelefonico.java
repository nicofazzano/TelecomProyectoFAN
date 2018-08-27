package Tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import Pages.Accounts;
import Pages.BasePage;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.OM;
import Pages.SalesBase;
import Pages.PagePerfilTelefonico;
import Pages.setConexion;

public class GestionesPerfilTelefonico extends TestBase{

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	List <String> datosOrden =new ArrayList<String>();
	;
	
	
	@BeforeClass
	public void init() {
		driver = setConexion.setupEze();
		sleep(5000);
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		loginTelefonico(driver);
		sleep(22000);
		driver.findElement(By.id("tabBar")).findElement(By.tagName("a")).click();
		sleep(18000);
		SalesBase sb = new SalesBase(driver);
		driver.switchTo().defaultContent();
		sleep(3000);
		goToLeftPanel2(driver, "Inicio");
		sleep(18000);
		try {
			sb.cerrarPestaniaGestion(driver);
		} catch (Exception ex1) {}
		Accounts ac = new Accounts(driver);
		driver.switchTo().frame(ac.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		List <WebElement> frames = driver.findElements(By.tagName("iframe"));
		boolean enc = false;
		int index = 0;
		for(WebElement frame : frames) {
			try {
				//System.out.println("aca");
				driver.switchTo().frame(frame);
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).getText(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.
				driver.findElement(By.cssSelector(".slds-grid.slds-m-bottom_small.slds-wrap.cards-container")).isDisplayed(); //each element is in the same iframe.
				//System.out.println(index); //prints the used index.
				driver.switchTo().frame(ac.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
				enc = true;
				break;
			} catch(NoSuchElementException noSuchElemExcept) {
				index++;
				driver.switchTo().frame(ac.getFrameForElement(driver, By.cssSelector(".hasMotif.homeTab.homepage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
			}
		}
		if(enc == false)
			index = -1;
		try {
			driver.switchTo().frame(frames.get(index));
		} catch(ArrayIndexOutOfBoundsException iobExcept) {
			System.out.println("Elemento no encontrado en ningun frame 2.");			
		}
		List <WebElement> botones = driver.findElements(By.tagName("button"));
		for (WebElement UnB : botones) {
			System.out.println(UnB.getText());
			if(UnB.getText().equalsIgnoreCase("gesti\u00f3n de clientes")) {
				UnB.click();
				break;
			}
		}
		sleep(14000);
	}

	//@AfterMethod
	public void after() {
		SalesBase sb = new SalesBase(driver);
		sb.cerrarPestaniaGestion(driver);
	}

	@AfterClass
	public void quit() throws IOException {
		guardarListaTxt(datosOrden);
		System.out.println("Se supone que guardo");
		//driver.quit();
		sleep(5000);
	}
	
	@Test (groups = {"GestionesPerfilOficina", "Recargas","E2E"}, dataProvider = "PerfilCuentaTomRiddle")  //Error despues de ingresar la tarjeta
	public void TS134332_CRM_Movil_REPRO_Recargas_Telefonico_TC_Callcenter_Financiacion(String cDNI, String cMonto, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular) {
		if(cMonto.length() >= 4) {
			cMonto = cMonto.substring(0, cMonto.length()-1);
		}
		if(cVenceMes.length() >= 2) {
			cVenceMes = cVenceMes.substring(0, cVenceMes.length()-1);
		}
		if(cVenceAno.length() >= 5) {
			cVenceAno = cVenceAno.substring(0, cVenceAno.length()-1);
		}
		if(cCodSeg.length() >= 5) {
			cCodSeg = cCodSeg.substring(0, cCodSeg.length()-1);
		}
		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
		sb.BuscarCuenta("DNI", cDNI);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		cc.irAGestionEnCard("Recarga de cr\u00e9dito");
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(cMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		sleep(15000);
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		sleep(10000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
		sleep(5000);
		selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
		driver.findElement(By.id("CardNumber-0")).sendKeys(cNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), cVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), cVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(cCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), cTipoDNI);
		driver.findElement(By.id("documentNumber-0")).sendKeys(cDNITarjeta);
		driver.findElement(By.id("cardHolder-0")).sendKeys(cTitular);				
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		sleep(20000);
		buscarYClick(driver.findElements(By.id("InvoicePreview_nextBtn")), "equals", "siguiente");
		List <WebElement> exis = driver.findElements(By.id("GeneralMessageDesing"));
		boolean a = false;
		for(WebElement x : exis) {
			if(x.getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito")) {
				a = true;
			}
			Assert.assertTrue(a);
		}
		String orden = cc.obtenerOrdenMontoyTN(driver, "Recarga");
		System.out.println("orden = "+orden);
		datosOrden.add("Recargas" + orden + " de cuenta "+accid+" con DNI: " + cDNI);
		CBS_Mattu invoSer = new CBS_Mattu();
		invoSer.PagoEnCaja("1003", accid, "2001", orden.split("-")[2], orden.split("-")[1]);
		sleep(5000);
		driver.navigate().refresh();
		sleep(10000);
		cc.obtenerOrdenMontoyTN(driver, "Recarga");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Status_ilecell")));
		Assert.assertTrue(driver.findElement(By.id("Status_ilecell")).getText().equalsIgnoreCase("activada"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacioDeCuota","E2E"}, dataProvider="RenovacionCuotaSinSaldo")
	public void TS130067_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Sin_Credito(String sCuenta, String sDNI) {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(10000);
		try {
			driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
			driver.findElement(By.id("combosMegas")).findElements(By.className("slds-checkbox")).get(2).click();
		}
		catch (Exception ex) {
			//Allways Empty
		}
		sleep(2000);
		Assert.assertTrue(driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).findElement(By.className("ng-binding")).findElement(By.tagName("p")).getText().equalsIgnoreCase("saldo insuficiente"));
	}
	
	@Test (groups = {"GestionesPerfilTelefonico", "RenovacionDeCuota"}, dataProvider="RenovacionCuotaConSaldo")
	public void TS_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Con_Credito(String sCuenta, String sDNI, String sLinea) {
		BasePage cambioFrameByID=new BasePage();
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));
		sleep(1000);
		SalesBase sSB = new SalesBase(driver);
		sSB.BuscarCuenta("DNI", sDNI);
		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).findElement(By.tagName("div")).click();
		sleep(20000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		driver.findElement(By.className("card-top")).click();
		sleep(3000);
		CustomerCare cCC = new CustomerCare(driver);
		cCC.irAGestionEnCard("Renovacion de Datos");
		sleep(12000);
		driver.switchTo().frame(cambioFrame(driver, By.id("combosMegas")));
		driver.findElement(By.id("combosMegas")).findElements(By.className("slds-checkbox")).get(0).click();
		sleep(2000);
		cCC.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		sleep(10000);
		List<WebElement> pago = driver.findElement(By.id("PaymentTypeRadio|0")).findElements(By.cssSelector(".slds-radio.ng-scope"));
		for (WebElement UnP : pago) {
			if (UnP.getText().toLowerCase().contains("saldo")){
				UnP.click();
				break;
			}
		}
		
		cCC.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(12000);
		driver.findElement(By.cssSelector(".message.description.ng-binding.ng-scope")).getText().equalsIgnoreCase("la compra se realiz\u00f3 exitosamente");
		cCC.obligarclick(driver.findElement(By.id("AltaHuawei_nextBtn")));
		sleep(12000);
		/*driver.navigate().refresh();
		try {
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.className("story-container")));
			driver.findElement(By.className("story-container")).findElement(By.cssSelector(".slds-text-body.regular.story-title"));
		} catch(Exception ex1) {
			driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.cssSelector(".x-layout-mini.x-layout-mini-west")));
			driver.findElement(By.cssSelector(".x-layout-mini.x-layout-mini-west")).click();
			sleep(4000);*/
		String sOrder = cCC.obtenerOrden(driver, "Reseteo de Cuota");
		System.out.println("Orden"+sOrder);
		datosOrden.add("Operacion: Renovacion Cuota, Orden: "+sOrder+", Cuenta: "+sCuenta+", DNI: "+sDNI+", Linea: "+sLinea);	
		
		System.out.println("Order: " + sOrder + " Fin");
		//Assert.assertTrue(driver.findElement(By.cssSelector(".slds-form-element.vlc-flex.vlc-slds-text-block.vlc-slds-rte.ng-pristine.ng-valid.ng-scope")).getText().contains("La orden se realiz\u00f3 con \u00e9xito!"));
	}
	
	
	@Test (groups= {"GestionesPerfilTelefonico","E2E"},priority=1, dataProvider="VentaPacks")
	public void TS123314_CRM_Movil_REPRO_Venta_de_Pack_40_Pesos_Exclusivo_Para_Vos_Descuento_De_Saldo_Telefonico(String sDNI, String sCuenta, String sNumeroDeCuenta, String sLinea, String sVentaPack){
	SalesBase sale = new SalesBase(driver);
	BasePage cambioFrameByID=new BasePage();
	CustomerCare cCC = new CustomerCare(driver);
	PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
	driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));	
	sleep(8000);
	sale.BuscarCuenta("DNI", sDNI);
	String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
	System.out.println("id "+accid);
	pagePTelefo.buscarAssert();
	pagePTelefo.comprarPack("comprar sms");
	pagePTelefo.agregarPack(sVentaPack);
	pagePTelefo.tipoDePago("descuento de saldo");
	String orden = cc.obtenerOrdenMontoyTN(driver, "Compra de Pack");
	System.out.println("orden = "+orden);
	datosOrden.add("Recargas" + orden + " de cuenta "+accid+" con DNI: " + sDNI);
	CBS_Mattu invoSer = new CBS_Mattu();
	invoSer.PagoEnCaja("1003", accid, "2001", orden.split("-")[2], orden.split("-")[1]);
	sleep(5000);
	driver.navigate().refresh();
	sleep(10000);
	cc.obtenerOrdenMontoyTN(driver, "Compra de Pack");
	sleep(10000);
	driver.switchTo().frame(cambioFrame(driver, By.id("Status_ilecell")));
	Assert.assertTrue(driver.findElement(By.id("Status_ilecell")).getText().equalsIgnoreCase("activada"));
	String sOrder = cCC.obtenerOrden(driver,"Compra de Pack");
	System.out.println("Orden: "+sOrder);
	datosOrden.add("Operacion: Compra de Pack, Orden: "+sOrder+", Cuenta: "+sCuenta+", DNI: "+sDNI+", Linea: "+sLinea);	
	System.out.println("Order: " + sOrder + " Fin");
	}
	
	@Test (groups= {"GestionesPerfilTelefonico","E2E"},priority=1, dataProvider="CambioSimCard")
	public void TSCambioSimCard(String sDNI, String sCuenta, String cBanco, String cTarjeta, String cPromo, String cCuotas, String cNumTarjeta, String cVenceMes, String cVenceAno, String cCodSeg, String cTipoDNI, String cDNITarjeta, String cTitular ){
	SalesBase sale = new SalesBase(driver);
	BasePage cambioFrameByID=new BasePage();
	CustomerCare cCC = new CustomerCare(driver);
	PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
	driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));	
	sleep(8000);
	sale.BuscarCuenta("DNI", sDNI);
	pagePTelefo.buscarAssert();
	cCC.irAGestionEnCard("Cambio SimCard");
	sleep(12000);
	driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("DeliveryMethodSelection")));
	sleep(15000);
	Select metodoEntrega = new Select (driver.findElement(By.id("DeliveryMethodSelection")));
	metodoEntrega.selectByVisibleText("Store Pick Up");
	Select State = new Select (driver.findElement(By.id("PickState")));
	State.selectByVisibleText("Ciudad Aut\u00f3noma de Buenos Aires");
	Select City = new Select (driver.findElement(By.id("PickCity")));
	City.selectByVisibleText("CIUD AUTON D BUENOS AIRES");
	Select Store = new Select (driver.findElement(By.id("Store")));
	Store.selectByVisibleText("Centro de Servicio Santa Fe - Juan de Garay 444");
	driver.findElement(By.id("DeliveryMethodConfiguration_nextBtn")).click();
	sleep(12000);
	cCC.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));	
	sleep(12000);
	buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
	selectByText(driver.findElement(By.id("BankingEntity-0")), cBanco);
	selectByText(driver.findElement(By.id("CardBankingEntity-0")), cTarjeta);
	selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), cPromo);
	sleep(5000);
	selectByText(driver.findElement(By.id("Installment-0")), cCuotas);
	driver.findElement(By.id("CardNumber-0")).sendKeys(cNumTarjeta);
	selectByText(driver.findElement(By.id("expirationMonth-0")), cVenceMes);
	selectByText(driver.findElement(By.id("expirationYear-0")), cVenceAno);
	driver.findElement(By.id("securityCode-0")).sendKeys(cCodSeg);
	selectByText(driver.findElement(By.id("documentType-0")), cTipoDNI);
	driver.findElement(By.id("documentNumber-0")).sendKeys(cDNITarjeta);
	driver.findElement(By.id("cardHolder-0")).sendKeys(cTitular);
	sleep(12000);
	cCC.obligarclick(driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")));
	sleep(15000);
	buscarYClick(driver.findElements(By.id("InvoicePreview_nextBtn")),"equals", "siguiente");	
	sleep(12000);
	String orden = driver.findElement(By.className("top-data")).findElement(By.className("ng-binding")).getText();
	String NCuenta = driver.findElements(By.className("top-data")).get(1).findElements(By.className("ng-binding")).get(3).getText();
	System.out.println("Orden "+orden);
	System.out.println("cuenta "+NCuenta);
	cCC.obligarclick(driver.findElement(By.id("OrderSumary_nextBtn")));
	sleep(15000);
	cCC.obligarclick(driver.findElement(By.id("SaleOrderMessages_nextBtn")));
	driver.navigate().refresh();
	}
}