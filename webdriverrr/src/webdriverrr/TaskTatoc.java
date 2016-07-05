package webdriverrr;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import java.util.List;
import java.io.File ;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import java.util.Scanner;
 
public class TaskTatoc  {
	
	void reachtatoc(WebDriver driver2)
	{
		driver2.get("http://10.0.1.86");
		List<WebElement> links1=driver2.findElements(By.cssSelector("html>body>div>ul>li>ul>li>a"));	
		links1.get(1).click();
		
		List<WebElement> links2=driver2.findElements(By.cssSelector(".page>a"));	
		links2.get(0).click();
		
	}

	void greenbox(WebDriver driver2 )
	{
		//green box
		
		WebElement element = driver2.findElement(By.cssSelector(".greenbox"));
		element.click();
	}



	void repaint (WebDriver driver2)
	{
		//repainting 
		driver2.switchTo().frame("main");
		WebElement box1 = driver2.findElement(By.cssSelector("#answer"));
		String color = box1.getAttribute("class");
		String color2 ; 
		List<WebElement> links=driver2.findElements(By.cssSelector("html>body>center>a"));
		do{
			links.get(0).click();
			driver2.switchTo().frame("child");
			WebElement box2 = driver2.findElement(By.cssSelector("#answer"));
			color2 =  box2.getAttribute("class");
			driver2.switchTo().defaultContent();
			driver2.switchTo().frame("main");
		} while (!color.equals(color2));
		links=driver2.findElements(By.cssSelector("html>body>center>a"));
		links.get(1).click();

	}


	void drag(WebDriver driver2)
	{
		//Dragging 


		WebElement dragobj = driver2.findElement(By.cssSelector("#dragbox"));
		WebElement dropobj = driver2.findElement(By.cssSelector("#dropbox"));
		Point obj= driver2.findElement(By.cssSelector("#dragbox")).getLocation();
	    System.out.println("dragbox"+obj);
		int xdragbox = obj.getX();
		int ydragbox = obj.getY();
		
		Point obj1= driver2.findElement(By.cssSelector("#dropbox")).getLocation();
		System.out.println("dropbox"+obj1);
		int xdropbox = obj1.getX();
		int ydropbox = obj1.getY();
	
		
		//System.out.println( driver2.findElement(By.cssSelector("#dropbox")).getLocation());
		(new Actions(driver2)).dragAndDrop(dragobj, dropobj).perform();
		Point newobj= driver2.findElement(By.cssSelector("#dragbox")).getLocation();
		
		int nxdragbox = newobj.getX();
		int nydragbox = newobj.getY();
		

		if (nydragbox < ydragbox && nxdragbox> xdragbox)
		{
			System.out.println("dragged and dropped ");
		}
		
		WebElement proceed = driver2.findElement(By.cssSelector(".page>a"));
		proceed.click();
		

	}


	void popup(WebDriver driver2)
	{
		// Launching POp Up Windows 
		List<WebElement> poplinks=driver2.findElements(By.cssSelector(".page>a"));	
		poplinks.get(0).click();
		try {
    	Thread.sleep(2000);
    	} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    }
  
		System.out.println("handle " + driver2.getWindowHandle());
		 
		for (String handle : driver2.getWindowHandles()) {
	    driver2.switchTo().window(handle);
	    System.out.println(handle);
		}
 
		WebElement name = driver2.findElement(By.cssSelector("#name"));
		name.sendKeys("Aakriti");
		WebElement submit = driver2.findElement(By.cssSelector("#submit"));
		submit.click();
		for (String handle : driver2.getWindowHandles()) {
			driver2.switchTo().window(handle);
		}
		poplinks=driver2.findElements(By.cssSelector(".page>a"));
		poplinks.get(1).click();
		}



	void token(WebDriver driver2)
	{
	
		// Token generation 

		List<WebElement> tokenlinks=driver2.findElements(By.cssSelector(".page>a"));	
		tokenlinks.get(0).click();
		WebElement token = driver2.findElement(By.cssSelector("#token"));
		String cookieval = token.getText();
		String cookieval2 = cookieval.substring(7) ;
		System.out.println("*************Cookie*******"+cookieval2);
		Cookie cookie = new Cookie("Token",cookieval2);
		driver2.manage().addCookie(cookie);
		driver2.findElement(By.linkText("Proceed")).click();
	}


	public static void main(String[] args) {

		
        System.out.println("Select your choice ");
        System.out.println(" Press 1 for - Firing Tatoc in FireFox");
        System.out.println(" Press 2 for - Firing Tatoc in Chrome");
        Scanner in = new Scanner(System.in);
        int choice;
        System.out.println("Enter an integer");
        choice = in.nextInt();
        
        switch (choice) {
        case 1 : 	File binaryPath=new File("/home/aakritisrivastava/Desktop/firefox (2)/firefox");
					FirefoxBinary ffbinary= new FirefoxBinary(binaryPath);
					FirefoxProfile ffProfile=new FirefoxProfile();
					WebDriver driver2 = new FirefoxDriver(ffbinary,ffProfile);
					TaskTatoc obj = new TaskTatoc();
					obj.reachtatoc(driver2);
					obj.greenbox(driver2);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					obj.repaint(driver2);
					obj.drag(driver2);
					obj.popup(driver2);	
			        obj.token(driver2);
			        driver2.quit();
			      
			       break ;
			 
			       
        case 2 :         System.setProperty("webdriver.chrome.driver", "/home/aakritisrivastava/Desktop/chromedriver");
                         WebDriver driver = new ChromeDriver();
        	             TaskTatoc obj2 = new TaskTatoc();
        	             obj2.reachtatoc(driver);
		                 obj2.greenbox(driver);
		                 try {
			             Thread.sleep(2000);
		                 } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			            e.printStackTrace();
		                 }
		                 obj2.repaint(driver);
		                 obj2.drag(driver);
		                 obj2.popup(driver);	
		                 obj2.token(driver);
		                 driver.quit();
                         break ;
			       
			       
			       
        
        default: choice = 1;
                 System.out.println("By default it will run in firefox");
        break;
			      
        }  
}

}









