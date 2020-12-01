package com.sbs.selenium.exam;

import java.io.File;
import java.nio.Buffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {

	public static void main(String[] args) {
		
		Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver.exe");
		
		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", path.toString());

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps");  // 기본앱 사용안함
		
		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);
		
		// 빈 탭 생성
		driver.executeScript("window.open('about:blank', '_blank');");
		
		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		
		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		driver.get("https://unsplash.com/t/nature");
		
		File downloadsFolder = new File("downloads");
		
		if (downloadsFolder.exists() == false) {
			downloadsFolder.mkdir();
		}

		
		List<WebElement> imgElements = driver.findElements(By.cssSelector("[data-test=\"masonry-grid-count-three\"] img"));
		
		for (WebElement imElement : imgElements) {
			String src = imElement.getAttribute("src");
			
			if (src.contains("images.unsplash.com/photo-") == false) {
				continue;
			}
		}
		
		
		
	}

}
