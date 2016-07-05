package webdriverrr;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
//import com.mysql.jdbc.PreparedStatement;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

public class prg1 {

	public String tokenvalue = null;

	void reachtatoc(WebDriver driver2) {
		driver2.get("http://10.0.1.86");
		List<WebElement> links1 = driver2.findElements(By.cssSelector("html>body>div>ul>li>ul>li>a"));
		links1.get(1).click();

		List<WebElement> links2 = driver2.findElements(By.cssSelector(".page>a"));
		links2.get(1).click();

	}

	void menu(WebDriver driver2) {
		driver2.get("http://10.0.1.86/tatoc/advanced/hover/menu");
		Actions actions = new Actions(driver2);
		WebElement moveonmenu = driver2.findElement(By.cssSelector(".menutitle"));
		actions.moveToElement(moveonmenu);
		moveonmenu.click();
		List<WebElement> options = driver2.findElements(By.cssSelector(".menuitem"));
		options.get(3).click();
		actions.perform();

	}

	void sql(WebDriver driver2) throws ClassNotFoundException, SQLException {

		driver2.get("http://10.0.1.86/tatoc/advanced/query/gate");
		String dburl = "jdbc:mysql://10.0.1.86/tatoc";
		String username = "tatocuser";
		String password = "tatoc01";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = (Connection) DriverManager.getConnection(dburl, username, password);
		System.out.println("connected");

		String text = driver2.findElement(By.cssSelector("#symboldisplay")).getText();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery("select id from identity where symbol= '" + text + "'");
		int id = 0;
		while (rs.next()) {
			id = rs.getInt(1);
			System.out.print(id);
		}
		rs.close();
		ResultSet rs1 = stm.executeQuery("select name,passkey from credentials where id='" + id + "'");
		while (rs1.next()) {
			String name = rs1.getString(1);
			driver2.findElement(By.cssSelector("#name")).sendKeys(name);
			System.out.println(name);
			String pwd = rs1.getString(2);
			driver2.findElement(By.cssSelector("#passkey")).sendKeys(pwd);
			System.out.print(pwd);

		}
		rs1.close();
		con.close();
		driver2.findElement(By.cssSelector("#submit")).click();

	}

	public void video(WebDriver driver2) {

		JavascriptExecutor js = (JavascriptExecutor) driver2;

		js.executeScript("player.play()");

		System.out.println("runn");
		String getTotalTime = (String) js.executeScript("return player.getTotalTime()");

		long Time = Long.parseLong(getTotalTime);
		try {

			Thread.sleep(Time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<WebElement> links2 = driver2.findElements(By.cssSelector(".page>a"));
		links2.get(0).click();

		System.out.println("hey2");

	}

	

	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		System.out.println("Select your choice ");
		System.out.println(" Press 1 for - Firing Tatoc in FireFox");
		System.out.println(" Press 2 for - Firing Tatoc in Chrome");

		Scanner input = new Scanner(System.in);
		int choice;
		System.out.println("Enter an integer");
		choice = input.nextInt();

		switch (choice) {
		case 1:
			File binaryPath = new File("/home/aakritisrivastava/Desktop/firefox (2)/firefox");
			FirefoxBinary ffbinary = new FirefoxBinary(binaryPath);
			FirefoxProfile ffProfile = new FirefoxProfile();
			WebDriver driver2 = new FirefoxDriver(ffbinary, ffProfile);
			prg1 obj = new prg1();
			 obj.reachtatoc(driver2);
			 obj.menu(driver2);
			 obj.sql(driver2);
			 obj.video(driver2);
			/*try {
				obj.restful(driver2);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			input.close();
			break;

		case 2:
			System.setProperty("webdriver.chrome.driver", "/home/aakritisrivastava/Desktop/chromedriver");
			WebDriver driver = new ChromeDriver();
			prg1 obj2 = new prg1();
			// obj2.reachtatoc(driver);
			// obj2.menu(driver);
			obj2.sql(driver);

			input.close();

			break;

		default:
			choice = 1;
			System.out.println("By default it will run in firefox");
			input.close();
			break;
		}

	}

}
