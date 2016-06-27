package tatocadv;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.SQLException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

//import Flash.FlashObjectWebDriver;

//import com.mysql.jdbc.PreparedStatement;
import java.sql.PreparedStatement;
//import com.mysql.jdbc.PreparedStatement;
//import com.mysql.jdbc.Statement;
//import com.sun.corba.se.pept.transport.Connection;
import java.sql.Connection;
//import java.sql.Statement;
public class advance {

	public static void main(String[] args) throws  InterruptedException, ClassNotFoundException, SQLException, JSONException, IOException{
		File binaryPath=new File("/home/anuraggarg/Downloads/firefox/firefox");
		FirefoxBinary ffbinary=new FirefoxBinary(binaryPath);
		FirefoxProfile ffprofile=new FirefoxProfile();
		WebDriver driver=new FirefoxDriver(ffbinary,ffprofile);
		driver.get("http://10.0.1.86/tatoc/");
		//driver.findElement(By.linkText("tatoc")).click();
		driver.findElement(By.linkText("Advanced Course")).click();
		Actions actions = new Actions(driver);
		WebElement mainMenu = driver.findElement(By.className("menutitle"));
		actions.moveToElement(mainMenu);

		WebElement subMenu = driver.findElement(By.xpath("html/body/div/div[2]/div[2]/span[5]"));
		actions.moveToElement(subMenu);
		actions.click().build().perform();
		
		/*Connection con =(Connection)DriverManager.getConnection("10.0.1.86/tatoc","tatocuser","tatoc01");
		Class.forName("com.mysql.jdbc.Driver"); 
		Statement stmt = con.createStatement(); */
		 Class.forName("com.mysql.jdbc.Driver");  
		  // Connection con=null;
      //con=DriverManager.getConnection("jdbc:mysql://10.0.1.86/tatoc","tatocuser","tatoc01");  
         
      
      
			String symbol=null, name=null, passkey=null,id=null;
			PreparedStatement stmt=null;
			Connection con=null;
			ResultSet rs=null;
			symbol= driver.findElement(By.cssSelector("#symboldisplay")).getText();

			try{
		 con=DriverManager.getConnection("jdbc:mysql://10.0.1.86:3306/tatoc","tatocuser","tatoc01");
		    stmt= con.prepareStatement("select id from identity where symbol=?;");
			stmt.setString(1, symbol);
			 rs= stmt.executeQuery();
				while( rs.next()){
					id=  rs.getString("id");
				}
				System.out.println(id);
				int identity= Integer.parseInt(id);
				 rs.close();
				stmt.close();	
				stmt= con.prepareStatement("select name,passkey from credentials where id=?;");
				stmt.setInt(1, identity);
				rs= stmt.executeQuery();
				if(((ResultSet) rs).next()){
					name= ((ResultSet) rs).getString("name");
					passkey= ((ResultSet) rs).getString("passkey");
				}
				rs.close();
				stmt.close();
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
	       {
				if(rs!=null){
					rs.close();
				}
				if(stmt!=null){
					stmt.close();
				}
				
				if(con!=null){
					con.close();
				}
			}
			
			driver.findElement(By.cssSelector("#name")).sendKeys(name);
			driver.findElement(By.cssSelector("#passkey")).sendKeys(passkey);
			driver.findElement(By.cssSelector("#submit")).click();
      
      
      
      
      
      
      
			JavascriptExecutor js = (JavascriptExecutor) driver;
		     //play video
			 //js.executeScript("player.play();} document.getElementsByTagName('a')[0].click();");
			 js .executeScript("player.play()");
			 Thread.sleep(25000);
			 js.executeScript("if(played==true)");
			 js.executeScript("document.getElementsByTagName('a')[0].click();");
				  

			   
			 
			//}
	
	
	
	
	String string=driver.findElement(By.id("session_id")).getText();
    string=string.substring(12, string.length());
   
   
    System.out.println(string);
    
    URL url = new URL("http://10.0.1.86/tatoc/advanced/rest/service/token/"+string);
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setRequestMethod("GET");
	conn.setRequestProperty("Accept", "application/json");
	
	BufferedReader in = new BufferedReader(
	        new InputStreamReader(conn.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();
	System.out.println(response.toString());
	String ssss=new String(response);
	
	JSONObject obj=new JSONObject(ssss);
	ssss=(String) obj.get("token");

	System.out.println(ssss);
	
	//post
	
	URL url1 = new URL("http://10.0.1.86/tatoc/advanced/rest/service/register");
	HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
	conn1.setRequestMethod("POST");
	conn1.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

	String urlParameters = "id="+string+"&signature="+ssss+"&allow_access=1";
	
	// Send post request
	conn1.setDoOutput(true);
	DataOutputStream wr = new DataOutputStream(conn1.getOutputStream());
	wr.writeBytes(urlParameters);
	wr.flush();
	wr.close();

	int responseCode = conn1.getResponseCode();
	System.out.println("\nSending 'POST' request to URL : " + url);
	System.out.println("Post parameters : " + urlParameters);
	System.out.println("Response Code : " + responseCode);
	conn1.disconnect();
	driver.findElement(By.cssSelector(".page a")).click();

	 //TASK:5	
	  driver.findElement(By.linkText("Download File")).click();
        Thread.sleep(5000);
        BufferedReader br = null;
        String strng=null, sCurrentLine;
        try 
        {
            int i=0;
            br = new BufferedReader(new FileReader("file_handle_test.dat"));
            while ((sCurrentLine = br.readLine()) != null) 
            {
                if(i==2)
                    strng = sCurrentLine;
                i++;
            }
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        strng = strng.substring(11,strng.length());
        driver.findElement(By.id("signature")).sendKeys(strng);
        driver.findElement(By.className("submit")).click();
			 
      
      
      
      
      
      /*PreparedStatement stmt=null;
          stmt= con.prepareStatement("select id from identity where symbol=?;");   
         //String name = null;
         //String passkey = null;
         int idd=0;
         String symbol1 =driver.findElement(By.id("symboldisplay")).getText();
         ResultSet rs= stmt.executeQuery();
         while(rs.next())
         { 
         rs=stmt.setString(1,symbol1);
         idd=rs.getInt("id");
         //String symbol2=rs.getString("symbol");
         System.out.println(idd);
         //System.out.println(symbol2);
         }*/
        /* ResultSet rs2= stmt.executeQuery("select name, passkey from credentials where id="+idd);
         while(rs.next())
         {
         name=rs2.getString("name");
         passkey=rs2.getString("passkey");
         }
         driver.findElement(By.id("name")).sendKeys(name);
         driver.findElement(By.id("passkey")).sendKeys(passkey);
         driver.findElement(By.id("submit")).click();*/
         
               
             //ps.setString(2,pass);  
   
	

}
}