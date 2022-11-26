package com.n00bc0der.code.Gambler_Dharma_v4.O;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class My_KP {
	
	static String getStarCusps(String[] cusps_star, String planet, HashMap<String, String> planets_star) {
		String result = "";
		for(int i=1; i<=12; i++) {
			if(cusps_star[i].equals(planet)) {
				result += i + " ";
			}
		}
		if(result != "") {
			return result;
		} else {
			for(int i=1; i<=12; i++) {
				if(cusps_star[i].equals(planets_star.get(planet.substring(0, 2)))) {
					result += i + " ";
				}
			}
			return result;
		}
	}

	public static void main(String[] args) throws Exception {

		MyFrame frame1 = new MyFrame();

		String name = frame1.name.getText();
		DateFormat sysDate = new SimpleDateFormat("d-M-yyyy");
		String sdate = sysDate.format(frame1.picker.getDate());
		String date = sdate.split("-")[0];
		String month = sdate.split("-")[1];
		String year = sdate.split("-")[2];
		String hour = frame1.hour.getSelectedItem().toString();
		String minute = frame1.minute.getSelectedItem().toString();
		String second = frame1.second.getSelectedItem().toString();
		String stadium = frame1.stadium.getSelectedItem().toString();
		int duration = Integer.parseInt(frame1.duration.getText()) * 2;
		String moonss = frame1.moonss.getText();

		String sname = null;
		String latitude = null;
		String longitude = null;
		String timezone = null;
		
		String imoon_sub = null;
		String imoon_sub_sub = null;
		String iasc_sign = null;
		int iminute = Integer.parseInt(minute);
		int ihour = Integer.parseInt(hour);
		int loop = 1;
//		int rotate = 1;
		
		String[] cusps_star = new String[13];
		HashMap<String, String> planets_star = new HashMap<String, String>();

//-------------------------------------------------------------------------------------------------------------------------------------------------------------
//												STADIUM DATABASE
//-------------------------------------------------------------------------------------------------------------------------------------------------------------

		if (!stadium.equals("ADD NEW STADIUM")) {
			if (stadium.equals("UPDATE EXISTING TIMEZONE")) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stadiums", "root",
							"root");
					String query = "UPDATE stadiums SET timezone = ? WHERE alias = ? OR name like ?";
					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, frame1.stimezone.getText());
					pst.setString(2, frame1.salias.getText());
					pst.setString(3, frame1.sname.getText());
					pst.executeUpdate();
					System.out.println("\nTimezone for Stadium " + frame1.sname.getText() + "("
							+ frame1.salias.getText() + ") Updated............");
					stadium = frame1.sname.getText();
					con.close();
				} catch (Exception e) {
					System.out.println(
							"\nHuh huh  <(-_-)>  Something went wrong while updating timezone for stadium in database............\n");
					System.out.println(e.toString());
				}
			}
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stadiums", "root", "root");
				String query = "select * from stadiums where name = ? OR alias = ?";
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, stadium);
				pst.setString(2, frame1.salias.getText());
				ResultSet set = pst.executeQuery();
				while (set.next()) {
					sname = set.getString(2);
					latitude = set.getString(3);
					longitude = set.getString(4);
					timezone = set.getString(5);
					System.out.println("\nYou have selected stadium : " + sname);
					System.out.println("Alias : " + set.getString(1));
					System.out.println("Latitude : " + latitude);
					System.out.println("Longitude : " + longitude);
					System.out.println("Timezone : " + timezone + "\n\n");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(
						"\nHuh huh  <(-_-)>  Something went wrong while retrieving your stadium from database....\n");
				System.out.println(e);
			}

		} else {
			try {
				String stadium_alias = frame1.salias.getText();
				sname = frame1.sname.getText();
				latitude = frame1.slatitude.getText();
				longitude = frame1.slongitude.getText();
				timezone = frame1.stimezone.getText();
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stadiums", "root", "root");
				String query = "insert into stadiums(alias,name,latitude,longitude,timezone) values(?,?,?,?,?)";
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, stadium_alias);
				pst.setString(2, sname);
				pst.setString(3, latitude);
				pst.setString(4, longitude);
				pst.setString(5, timezone);
				pst.executeUpdate();
				System.out.println("\nStadium " + sname + " added to database.");
				con.close();
			} catch (Exception e) {
				System.out
						.println("\nHuh huh  <(-_-)> Something went wrong while adding your stadium to database....\n");
				System.out.println(e);
			}
		}

//-------------------------------------------------------------------------------------------------------------------------------------------------------------

		System.out.println("\n\nKP Event Flow Report is getting generated. This may take several minutes. Please be patient");

		PrintStream o = new PrintStream(
				new File("C://Users//yadav//Desktop//Gambler's Dharma//KP MUHURATA//" + name + " " + sdate + ".txt"));
		PrintStream console = System.out;
		System.setOut(o);

		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------------"
						+ "");
		System.out.println("Sports Contest Details :-\n");
		System.out.println("Name : " + name);
		System.out.println("Date : " + date + "-" + month + "-" + year);
		System.out.println("Time : " + hour + ":" + minute + ":" + second);
		System.out.println("Timezone : " + timezone);
		System.out.println("Stadium : " + sname);
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------------"
						+ "\n\n");

		Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

		System.setProperty("webdriver.chrome.driver", "D:\\drivers\\chromedriver.exe"); 
		System.setProperty("webdriver.chrome.silentOutput", "true");
		@SuppressWarnings("rawtypes")
		DriverService.Builder serviceBuilder = new ChromeDriverService.Builder().withSilent(true);
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("user-data-dir=C:\\Users\\yadav\\AppData\\Local\\Google\\Chrome\\User Data");
//		options.addArguments("--headless");
		ChromeDriverService chromeDriverService = (ChromeDriverService) serviceBuilder.build();
		chromeDriverService.sendOutputTo(new OutputStream() {
			@Override
			public void write(int b) {
			}
		});
		WebDriver driver = new ChromeDriver(chromeDriverService);
		WebDriverWait wait = new WebDriverWait(driver,60);

		driver.get("https://www.rahasyavedicastrology.com/rva-software/");
		driver.manage().window().maximize();

		driver.findElement(By.id("m-name")).sendKeys(Keys.chord(Keys.CONTROL, "a"), name);

		Select dates = new Select(driver.findElement(By.id("m-date")));
		dates.selectByValue(date);
		Select months = new Select(driver.findElement(By.id("m-month")));
		months.selectByValue(month);
		Select years = new Select(driver.findElement(By.id("m-year")));
		years.selectByValue(year);

		Select hours = new Select(driver.findElement(By.id("m-hour")));
		hours.selectByValue(hour);
		Select minutes = new Select(driver.findElement(By.id("m-minute")));
		minutes.selectByValue(minute);
		Select seconds = new Select(driver.findElement(By.id("m-seconds")));
		seconds.selectByValue(second);

		driver.findElement(By.id("m-advanced-geo-option")).click();

		driver.findElement(By.id("m-hr-lat")).sendKeys(Keys.chord(Keys.CONTROL, "a"), latitude);
		driver.findElement(By.id("m-hr-lon")).sendKeys(Keys.chord(Keys.CONTROL, "a"), longitude);
		driver.findElement(By.id("m-hr-tzone")).sendKeys(Keys.chord(Keys.CONTROL, "a"), timezone);

		driver.findElement(By.id("m-submit-hr-form")).click();

//-------------------------------------------------------------------------------------------------------------------------------------------------------------

		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------------"
						+ "");
		System.out.println("								The Muhurata Technique :");
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------------------"
						+ "");

		// CUSPAL-SUB-LORDS
		String asc_csl = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[1]/td[6]")))
				.getText();
		String dsc_csl = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[7]/td[6]"))
				.getText();
		String sixth_csl = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[6]/td[6]"))
				.getText();
		String twelfth_csl = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[12]/td[6]"))
				.getText();
		String eleventh_csl = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[11]/td[6]"))
				.getText();
		String fifth_csl = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[5]/td[6]"))
				.getText();
		String tenth_csl = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[10]/td[6]"))
				.getText();
		String fourth_csl = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[4]/td[6]"))
				.getText();
		String third_csl = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[3]/td[6]"))
				.getText();
		String nineth_csl = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[9]/td[6]"))
				.getText();
		String second_csl = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[2]/td[6]"))
				.getText();
		String eighth_csl = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[8]/td[6]"))
				.getText();
		
		// CUSPAL-STAR-LORDS
		String asc_cnl = cusps_star[1] = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[1]/td[5]")))
				.getText();
		String dsc_cnl = cusps_star[7] = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[7]/td[5]"))
				.getText();
		String sixth_cnl = cusps_star[6] = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[6]/td[5]"))
				.getText();
		String twelfth_cnl = cusps_star[12] = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[12]/td[5]"))
				.getText();
		String eleventh_cnl = cusps_star[11] = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[11]/td[5]"))
				.getText();
		String fifth_cnl = cusps_star[5] = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[5]/td[5]"))
				.getText();
		String tenth_cnl = cusps_star[10] = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[10]/td[5]"))
				.getText();
		String fourth_cnl = cusps_star[4] = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[4]/td[5]"))
				.getText();
		String third_cnl = cusps_star[3] = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[3]/td[5]"))
				.getText();
		String nineth_cnl = cusps_star[9] = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[9]/td[5]"))
				.getText();
		String second_cnl = cusps_star[2] = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[2]/td[5]"))
				.getText();
		String eighth_cnl = cusps_star[8] = driver.findElement(By.xpath(
				"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[1]/div/table/tbody/tr[8]/td[5]"))
				.getText();

		Multimap<Integer, String> house_view = ArrayListMultimap.create();
		for (int i = 1; i <= 12; i++) {
			for (int j = 1; j <= 4; j++) {
				String s = driver.findElement(By.xpath(
						"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[2]/div/div[1]/div/table/tbody/tr["
								+ i + "]/td[" + j + "]"))
						.getText();
				if ((s.equals("Uranus")) || (s.equals("Neptune")) || (s.equals("Pluto"))) {
					house_view.put(i, "");
				} else {
					house_view.put(i, s);
				}
			}
		}

		System.out.println("1st CSL - " + asc_csl + " =>  " + Muhurata.Calculate_Muhurata_ASC(house_view, asc_csl));
		System.out.println("7th CSL - " + dsc_csl + " =>  " + Muhurata.Calculate_Muhurata_DSC(house_view, dsc_csl));
		System.out
				.println("\n6th CSL - " + sixth_csl + " =>  " + Muhurata.Calculate_Muhurata_ASC(house_view, sixth_csl));
		System.out.println(
				"12th CSL - " + twelfth_csl + " =>  " + Muhurata.Calculate_Muhurata_DSC(house_view, twelfth_csl));
		System.out.println(
				"\n11th CSL - " + eleventh_csl + " =>  " + Muhurata.Calculate_Muhurata_ASC(house_view, eleventh_csl));
		System.out.println("5th CSL - " + fifth_csl + " =>  " + Muhurata.Calculate_Muhurata_DSC(house_view, fifth_csl));
		System.out.println("\n\nAll Planets Signification Value");
		System.out.println("================================\n");
		System.out.println("Sun =>  " + Muhurata.Calculate_Muhurata_ASC(house_view, "Sun"));
		System.out.println("Moon =>  " + Muhurata.Calculate_Muhurata_ASC(house_view, "Moon"));
		System.out.println("Mars =>  " + Muhurata.Calculate_Muhurata_ASC(house_view, "Mars"));
		System.out.println("Mercury =>  " + Muhurata.Calculate_Muhurata_ASC(house_view, "Mercury"));
		System.out.println("Venus =>  " + Muhurata.Calculate_Muhurata_ASC(house_view, "Venus"));
		System.out.println("Jupiter =>  " + Muhurata.Calculate_Muhurata_ASC(house_view, "Jupiter"));
		System.out.println("Saturn =>  " + Muhurata.Calculate_Muhurata_ASC(house_view, "Saturn"));
		System.out.println("Rahu =>  " + Muhurata.Calculate_Muhurata_ASC(house_view, "Rahu"));
		System.out.println("Ketu =>  " + Muhurata.Calculate_Muhurata_ASC(house_view, "Ketu"));
		
		System.out.println("\n\n------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("CUSPAL-SUB-LORDS");
		System.out.println("=================\n");
		System.out.println("FAVOURABLE FOR ASCENDANT :-");
		System.out.println("FIRST CSL - "+asc_csl);
		System.out.println("SECOND CSL - "+second_csl);
		System.out.println("THIRD CSL - "+third_csl);
		System.out.println("SIXTH CSL - "+sixth_csl);
		System.out.println("TENTH CSL - "+tenth_csl);
		System.out.println("ELEVENTH CSL - "+eleventh_csl);
		System.out.println("\nFAVOURABLE FOR DESCENDANT :-");
		System.out.println("SEVENTH CSL - "+dsc_csl);
		System.out.println("EIGHTH CSL - "+eighth_csl);
		System.out.println("NINETH CSL - "+nineth_csl);
		System.out.println("TWELVETH CSL - "+twelfth_csl);
		System.out.println("FOURTH CSL - "+fourth_csl);
		System.out.println("FIFTH CSL - "+fifth_csl);
		
		System.out.println("\n\nCUSPAL-STAR-LORDS");
		System.out.println("=================\n");
		System.out.println("FAVOURABLE FOR ASCENDANT :-");
		System.out.println("FIRST CSL - "+asc_cnl);
		System.out.println("SECOND CSL - "+second_cnl);
		System.out.println("THIRD CSL - "+third_cnl);
		System.out.println("SIXTH CSL - "+sixth_cnl);
		System.out.println("TENTH CSL - "+tenth_cnl);
		System.out.println("ELEVENTH CSL - "+eleventh_cnl);
		System.out.println("\nFAVOURABLE FOR DESCENDANT :-");
		System.out.println("SEVENTH CSL - "+dsc_cnl);
		System.out.println("EIGHTH CSL - "+eighth_cnl);
		System.out.println("NINETH CSL - "+nineth_cnl);
		System.out.println("TWELVETH CSL - "+twelfth_cnl);
		System.out.println("FOURTH CSL - "+fourth_cnl);
		System.out.println("FIFTH CSL - "+fifth_cnl);
		
		System.out.println("\n\n------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("PLANET  -  STAR  -  SUB");
		for(int i=1; i<=9; i++) {
			String planet = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[2]/div/table/tbody/tr["+i+"]/th")))
					.getText();
			String planet_star = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[2]/div/table/tbody/tr["+i+"]/td[6]")))
					.getText();
			String planet_sub = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[1]/div/div[2]/div/table/tbody/tr["+i+"]/td[7]")))
					.getText();
			System.out.println(planet+"  -  "+planet_star+"  -  "+planet_sub);
			
			planets_star.put(planet.substring(0, 2), planet_star);
		}

//-------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		System.out.println("\n\n------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("								Initial Significators - Planet View :");
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
		
		for(int i=1; i<=9; i++) {
		String planet = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[2]/div/div[2]/div/table/tbody/tr["+i+"]/th")).getText();
		String A = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[2]/div/div[2]/div/table/tbody/tr["+i+"]/td[1]")).getText();
		String B = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[2]/div/div[2]/div/table/tbody/tr["+i+"]/td[2]")).getText();
		String C = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[2]/div/div[2]/div/table/tbody/tr["+i+"]/td[3]")).getText();
		String D = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[4]/div[2]/div[2]/div/div[2]/div/table/tbody/tr["+i+"]/td[4]")).getText();
		System.out.println(planet+"          A [ "+A+" ]          B [ "+B+" ]          C [ "+C+" ]          D [ "+D+" ]");
		}
		
//-------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		System.out.println("\n\n------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("								Ruling Planet Motion :");
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
		
		driver.navigate().to("https://www.rahasyavedicastrology.com/motion-chart/");
		if(Integer.parseInt(date) < 10) { date = "0"+date; } if(Integer.parseInt(month) < 10) { month = "0"+month; }
		if(Integer.parseInt(hour) < 10) { hour = "0"+hour; } if(Integer.parseInt(minute) < 10) { minute = "0"+minute; } if(Integer.parseInt(second) < 10) { second = "0"+second; }
		driver.findElement(By.id("startDate")).sendKeys(Keys.chord(Keys.CONTROL, "a"), date+month+year);
		driver.findElement(By.id("startTime")).sendKeys(Keys.chord(Keys.CONTROL, "a"), hour+minute+second);
		driver.findElement(By.id("advanced-geo-option")).click();

		driver.findElement(By.id("hr-lat")).sendKeys(Keys.chord(Keys.CONTROL, "a"), latitude);
		driver.findElement(By.id("hr-lon")).sendKeys(Keys.chord(Keys.CONTROL, "a"), longitude);
		driver.findElement(By.id("hr-tzone")).sendKeys(Keys.chord(Keys.CONTROL, "a"), timezone);
		
		driver.findElement(By.id("hr-tzone")).sendKeys(Keys.ENTER); Thread.sleep(5000);
		
		String idate = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr[1]/td[1]")).getText();
		String itime = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr[1]/td[2]")).getText();
		imoon_sub = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr[1]/td[9]")).getText();
		imoon_sub_sub = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr[1]/td[10]")).getText();
		iasc_sign = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr[1]/td[3]")).getText();
		System.out.println("Date  |  Time  |  Ascendant  |  Moon Sub  |  Moon Sub Sub  |  Changes");
		System.out.println(idate+"  |  "+itime+"  |  "+iasc_sign+"  |  "+imoon_sub+" - "+My_KP.getStarCusps(cusps_star, imoon_sub, planets_star)+"  |  "+imoon_sub_sub+" - "+My_KP.getStarCusps(cusps_star, imoon_sub_sub, planets_star)+"  |  Initial Transit");
		
		while(loop <= duration) {
		
		for(int i=1; i<=30; i++) {
			
			String cmoon_sub = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr["+i+"]/td[9]")).getText();
			String cmoon_sub_sub = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr["+i+"]/td[10]")).getText();
			String casc_sign = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr["+i+"]/td[3]")).getText();
			
			if(!cmoon_sub.equals(imoon_sub)) {
				String cdate = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr["+i+"]/td[1]")).getText();
				String ctime = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr["+i+"]/td[2]")).getText();
				System.out.println(cdate+"  |  "+ctime+"  |  "+casc_sign+"  |  "+cmoon_sub+" - "+My_KP.getStarCusps(cusps_star, cmoon_sub, planets_star)+"  |  "+cmoon_sub_sub+" - "+My_KP.getStarCusps(cusps_star, cmoon_sub_sub, planets_star)+"  |  "+"********** MOON SUB **********");
				imoon_sub = cmoon_sub;
				imoon_sub_sub = cmoon_sub_sub;
			}
			if(!cmoon_sub_sub.equals(imoon_sub_sub) && moonss.equals("YES")) {
				String cdate = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr["+i+"]/td[1]")).getText();
				String ctime = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr["+i+"]/td[2]")).getText();
				System.out.println(cdate+"  |  "+ctime+"  |  "+casc_sign+"  |  "+cmoon_sub+" - "+My_KP.getStarCusps(cusps_star, cmoon_sub, planets_star)+"  |  "+cmoon_sub_sub+" - "+My_KP.getStarCusps(cusps_star, cmoon_sub_sub, planets_star)+"  |  "+"moon sub sub");
				imoon_sub_sub = cmoon_sub_sub;
			}
//			if(!casc_sign.equals(iasc_sign)) {
//				String cdate = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr["+i+"]/td[1]")).getText();
//				String ctime = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr["+i+"]/td[2]")).getText();
//				rotate = rotate + 1;
//				System.out.println(cdate+"  |  "+ctime+"  |  "+casc_sign+"  |  "+cmoon_sub+"  |  "+cmoon_sub_sub+"  |  "+"ASCENDANT		(Rotate to "+rotate+" house)");
//				iasc_sign = casc_sign;
//			}
			
		}
		
		String shour = null, sminute = null;
		iminute = iminute + 30;
		if(iminute >= 60) {
			iminute = iminute - 60;
			ihour = ihour + 1;
			shour = Integer.toString(ihour);
			sminute = Integer.toString(iminute);
		} else {
			shour = Integer.toString(ihour);
			sminute = Integer.toString(iminute);
		}
		
		if(ihour < 10) { shour = "0"+shour; } if(iminute < 10) { sminute = "0"+sminute; }
		driver.findElement(By.id("startTime")).sendKeys(Keys.chord(Keys.CONTROL, "a"), shour+sminute+second);
		driver.findElement(By.id("hr-tzone")).sendKeys(Keys.ENTER); Thread.sleep(5000);
		loop = loop + 1;
		
	}
		
		System.setOut(console);
		System.out.println("\n\nKP Event Flow Report has been generated successfully. Thank you for your patience");
		driver.quit();
		System.out.print("\n\nPRESS ENTER TO CLOSE THE PROGRAM . . . .");
		Scanner scan = new Scanner(System.in);
	    scan.nextLine();
	    scan.close();
		
//-------------------------------------------------------------------------------------------------------------------------------------------------------------
		
	} // end of main()

} // end of class