package com.exposicion.loginmvc.uitest;

import java.net.URL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

// El test asume que la aplicación está corriendo EXTERNAMENTTE en el contenedor 'app-test'

public class LoginUITest {

    private WebDriver driver;
    
    private final int fixedPort = 8082;
    
    private String appHostName;
    private String seleniumHubHost;
    
    private String SELENIUM_HUB_URL;
    private String baseUrl;

    @BeforeEach
    void setupTest() throws Exception {
        
        // 1. OBTENER PROPIEDADES DEL SISTEMA (pasadas por Maven)
        // Utilizamos valores por defecto en caso de que Jenkins no los pase
        appHostName = System.getProperty("app.host.name", "app-test");
        seleniumHubHost = System.getProperty("selenium.hub.host", "selenium-hub");

        // 2. Configurar la URL del Hub de Selenium
        SELENIUM_HUB_URL = "http://" + seleniumHubHost + ":4444/wd/hub";
        
        // 3. Configurar el WebDriver para Chrome Headless
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
        options.setAcceptInsecureCerts(true);

        // 4. Inicializar RemoteWebDriver con reintentos
        int maxAttempts = 5; 
        int currentAttempt = 0;
        Exception lastException = null;

        while (currentAttempt < maxAttempts) {
            try {
                driver = new RemoteWebDriver(new URL(SELENIUM_HUB_URL), options);
                break; // Conexión exitosa
            } catch (Exception e) {
                lastException = e;
                currentAttempt++;
                // Imprimir advertencia con System.err para visibilidad
                System.err.println("ADVERTENCIA: Falló conexión con Selenium Hub. Intento " + currentAttempt + "/" + maxAttempts + ". Esperando 3 segundos...");
                Thread.sleep(3000);
            }
        }

        if (driver == null) {
            throw new RuntimeException("Fallo al inicializar RemoteWebDriver después de " + maxAttempts + " intentos. Revisar que Selenium Grid esté levantado.", lastException);
        }

        driver.manage().window().maximize();

        // 5. Configurar la Base URL de la aplicación (usando el nombre de host de Docker)
        baseUrl = "http://" + appHostName + ":" + fixedPort + "/";
        
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
        driver.get(baseUrl);
        // Lógica de interacción para credenciales válidas
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.tagName("button")).click();

        String titulo = driver.getTitle();
        assertTrue(titulo.contains("Bienvenido"), "Debe redirigir a la página de bienvenida después del login exitoso.");
    }

    @Test
    void testLogin_CredencialesInvalidas_DebeMostrarMensajeDeError() {
        driver.get(baseUrl);
        // Lógica de interacción para credenciales inválidas
        driver.findElement(By.name("username")).sendKeys("usuario-malo");
        driver.findElement(By.name("password")).sendKeys("clave-incorrecta");
        driver.findElement(By.tagName("button")).click();

        String src = driver.getPageSource();
        assertTrue(src.contains("Credenciales incorrectas"), "Debe mostrar un mensaje de error por credenciales inválidas.");
    }
    
}