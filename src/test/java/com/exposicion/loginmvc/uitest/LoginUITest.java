package com.exposicion.loginmvc.uitest;

import java.net.URL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value; // Importación necesaria
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginUITest {

    private WebDriver driver;

    @LocalServerPort
    private int port;

    private String baseUrl;
    
    // Inyecta el nombre del contenedor de Jenkins (pasado por Maven)
    @Value("${app.host.name:localhost}") 
    private String appHostName;

    // Inyecta el nombre del servicio del Hub (usamos el nombre del contenedor Docker)
    @Value("${selenium.hub.host:selenium-hub}")
    private String seleniumHubHost;
    
    private String SELENIUM_HUB_URL;

    @BeforeEach
    void setupTest() throws Exception {
        
        // Construye la URL del Hub usando el host inyectado
        SELENIUM_HUB_URL = "http://" + seleniumHubHost + ":4444/wd/hub";
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
        options.setAcceptInsecureCerts(true);

        // Conexión al Selenium Grid
        driver = new RemoteWebDriver(new URL(SELENIUM_HUB_URL), options);

        driver.manage().window().maximize();

        // **CORRECCIÓN CRÍTICA:** Usamos el nombre del contenedor de Jenkins para que el Node pueda acceder a la App.
        baseUrl = "http://" + appHostName + ":" + port + "/";
        
        System.out.println("DEBUG: App Base URL para la prueba UI: " + baseUrl);

    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testLogin_CredencialesValidas_DebeIrAHome() {
        driver.get(baseUrl); // Accede a http://jenkins_practica:PUERTO/

        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.tagName("button")).click();

        String titulo = driver.getTitle();
        assertTrue(titulo.contains("Bienvenido"));
    }

    @Test
    void testLogin_CredencialesInvalidas_DebeMostrarMensajeDeError() {
        driver.get(baseUrl);

        driver.findElement(By.name("username")).sendKeys("usuario-malo");
        driver.findElement(By.name("password")).sendKeys("clave-incorrecta");
        driver.findElement(By.tagName("button")).click();

        String src = driver.getPageSource();
        assertTrue(src.contains("Credenciales incorrectas"));
    }
}