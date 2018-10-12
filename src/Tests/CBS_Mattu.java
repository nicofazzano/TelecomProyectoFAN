package Tests;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.BasePage;
import Pages.CBS;
import Pages.ManejoCaja;
import Pages.setConexion;

public class CBS_Mattu extends TestBase {
	
	private WebDriver driver;
	
	//-------------------------------------------------------------------------------------------------
	//@Befor&After
	//@BeforeClass(alwaysRun=true)
	public void readySteady() throws Exception {
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		loginCBS(driver);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	//@AfterClass(alwaysRun=true)
	public void tearDown() {
		driver.close();
	}
	
	//-------------------------------------------------------------------------------------------------
	//TCC =
	
	@Test
	public void cajita() {
		ManejoCaja MN = new ManejoCaja();
		this.driver = setConexion.setupEze();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		MN.ingresarCaja(driver);
		MN.configuracionesIniciales(driver);
		MN.seleccionarOpcionCatalogo(driver, "Cuentas por cobrar");
		MN.abrirCajaRegistradora(driver);
		MN.pagarTC(driver,"20181005000000098056","1000000026310001");
		/*MN.seleccionarOpcionCatalogo(driver, "Cuentas por cobrar");
		MN.cerrarCajaRegistradora(driver);*/
	}
	
	@Test
	public boolean cajeta(WebDriver driver, String prefactura, String cuenta) throws AWTException {
		ManejoCaja mn = new ManejoCaja();
		boolean exito = false;
		//this.driver = setConexion.setupEze();
		abrirPestaniaNueva(driver);
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		sleep(5000);
		try {
			driver.switchTo().window(tabs2.get(1));
			mn.ingresarCaja(driver);
			exito = true;
		}catch(Exception ex1) {
			driver.close();
		    driver.switchTo().window(tabs2.get(0));
		}
		if(exito == true) {
			mn.configuracionesIniciales(driver);
			mn.seleccionarOpcionCatalogo(driver, "Cuentas por cobrar");
			mn.abrirCajaRegistradora(driver);
			//MN.seleccionarOpcionCatalogo(driver, "Cuentas por cobrar");
			mn.pagarEfectivo(driver,prefactura,cuenta);
			//llamar al cerrarcaja registradora
			mn.cerrarPestanias(driver);
			mn.cerrarCajaRegistradora(driver);
			driver.close();
		    driver.switchTo().window(tabs2.get(0));
		}
		return(exito);
	}
	
	@Test
	public void openPage() {
		String sEndPoint = "Pago en Caja";
		String sPaymentChannelID = "1003";
		String sAccountKey = "9900000326610001";
		String sPaymentMethod = "2001";
		String sAmount = "123005600";
		String sInvoiceno = "20180827000000056800";
		String sPaymentSerialNo = ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		String sResponse = cCBS.sCBS_Request_ServicioWeb_Validador(sSCS.callSoapWebService(cCBS.sRequest(sPaymentSerialNo, sPaymentChannelID, sAccountKey, sPaymentMethod, sAmount, sInvoiceno), sEndPoint));
		System.out.println("sResponse: " + sResponse);
	}
	
	public boolean PagoEnCaja(String sPaymentChannelID, String sAccountKey, String sPaymentMethod, String sAmount, String sInvoiceno, WebDriver driver) throws AWTException {
		if(cajeta(driver,sInvoiceno, sAccountKey)==false) {
			String sEndPoint = "Pago en Caja";
			String sPaymentSerialNo = ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
			SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
			CBS cCBS = new CBS();
			String sResponse = cCBS.sCBS_Request_ServicioWeb_Validador(sSCS.callSoapWebService(cCBS.sRequest(sPaymentSerialNo, sPaymentChannelID, sAccountKey, sPaymentMethod, sAmount, sInvoiceno), sEndPoint));
			System.out.println("sResponse: " + sResponse);
			return(cCBS.sCBS_Request_Validador(sResponse));
		}
		return(true);
	}
	
	@Test
	public void openPage2(String sOrder) {
		String sEndPoint = "Pago Simulado";
		//String sOrder = "00080253";
		
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		String sResponse = sSCS.callSoapWebService(cCBS.sRequestByOrder(sOrder), sEndPoint);
		System.out.println("sResponse: " + sResponse);
	}
	
	@Test
	public void ValidarInfoCuenta(String sLinea, String sNombre, String sApellido) {
		String sEndPoint = "Alta de Linea";
		//String sLinea = "";
		String sMessageSeq = "QCI"+ ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		String sImsi = "";
		String sICCD = "";
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		String sResponse = cCBS.sCBS_Request_Validador_Alta_Linea(sSCS.callSoapWebService(cCBS.sRequestByLinea(sLinea, sMessageSeq), sEndPoint), sLinea, sImsi, sICCD, sNombre, sApellido);
		System.out.println("sResponde ="+sResponse);
		Assert.assertTrue(sResponse.equals("true"));
	}
	
	@Test
	public boolean PagaEnCajaTC(String sPaymentChannelID, String sAccountKey, String sPaymentMethod, String sAmount, String sInvoiceno, String sAccountNumber, String sAccountName, String sExpirationDate, String sCVV, String sCardHolderName, String sCardHolderNumber) {
		String sEndPoint = "PagoEnCaja";
		String sMessageSeq = ((new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())).toString()+Integer.toString((int)(Math.random()*1000));
		
		SOAPClientSAAJ sSCS = new SOAPClientSAAJ();
		CBS cCBS = new CBS();
		String sResponse = cCBS.sCBS_TC_Request_Validador(sSCS.callSoapWebService(cCBS.sRequestByTC(sMessageSeq, sPaymentChannelID, sAccountKey, sPaymentMethod, sAmount, sAccountNumber, sAccountName, sExpirationDate, sCVV, sInvoiceno, sCardHolderName, sCardHolderNumber), sEndPoint));
		//cCBS.sCBS_TC_Request_Validador(sResponse);
		System.out.println("sResponde ="+sResponse);
		boolean bAssert = sResponse.equals("true");
		return bAssert;
	}
}