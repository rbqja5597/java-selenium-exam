package com.sbs.selenium.exam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {

	public static void main(String[] args) {
		DcinsideprintArticle();

	}

	private static void DcinsideprintArticle() {
		Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver.exe");

		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", path.toString());

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함
		// options.setHeadless(true);

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);

		// 빈 탭 생성
		// driver.executeScript("window.open('about:blank','_blank');");

		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		driver.get("https://news.daum.net/society#1");

		Util.sleep(1000);

		List<WebElement> elements = driver.findElements(By.cssSelector(".list_timenews"));

		for (WebElement element : elements) {
			WebElement aElement = element.findElement(By.cssSelector(".tit_timenews > a"));
			String href = aElement.getAttribute("href").trim();
			href = href.split("data-tiara-id=")[1];
			
			String code = href.split("&")[0];
			String title = element.findElements(By.cssSelector(".tit_timenews > a")).get(0).getText().trim();
			String regDate = element.findElements(By.cssSelector(".txt_time")).get(0).getText().trim();
			
			
			DCInsideArticle article = new DCInsideArticle(code, title, regDate);
			System.out.println(article);
		}	

	}

	private static void downloadImage() {

		Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver.exe");

		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", path.toString());

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);

		// 빈 탭 생성
		// driver.executeScript("window.open('about:blank', '_blank');");

		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		driver.get("https://unsplash.com/t/nature");

		File downloadsFolder = new File("downloads");

		if (downloadsFolder.exists() == false) {
			downloadsFolder.mkdir();
		}

		Util.sleep(1000);

		List<WebElement> imgElements = driver
				.findElements(By.cssSelector("[data-test=\"masonry-grid-count-three\"] img"));

		for (WebElement imElement : imgElements) {
			String src = imElement.getAttribute("src");
			System.out.println(src);

			if (src.contains("images.unsplash.com/photo-") == false) {
				continue;
			}

			BufferedImage saveImage = null;

			try {
				saveImage = ImageIO.read(new URL(src));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (saveImage != null) {
				try {
					String fileName = src.split("/")[3];
					fileName = fileName.split("\\?")[0];
					ImageIO.write(saveImage, "jpg", new File(fileName + "jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			System.out.println(src);
		}

	}

}
