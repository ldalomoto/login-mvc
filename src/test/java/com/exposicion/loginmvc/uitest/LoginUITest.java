package com.exposicion.loginmvc.uitest;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.URL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Clase de Pruebas Funcionales (UI) usando Selenium WebDriver. Simula la
 * interacción de un usuario real en el navegador.
 *
 * ESTA PRUEBA CORRESPONDE A LA ETAPA: 'Pruebas UI con Selenium WebDriver'
 */
// Usamos SpringBootTest para obtener la configuración del puerto
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginUITest {

    private WebDriver driver;

    // Inyectamos el puerto que Spring Boot asignó para la prueba (ej: 8081)
    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeAll
    static void setupClass() {
        // Ya no es necesario WebDriverManager.chromedriver().setup() porque usaremos el Grid.
    }

    @BeforeEach
    void setupTest() throws Exception {
        ChromeOptions options = new ChromeOptions();
        // Usamos RemoteWebDriver para conectarnos al Hub de Selenium
        // La URL debe ser accesible desde el agente de Jenkins (ej: localhost:4444)
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        driver.manage().window().maximize();
        baseUrl = "http://localhost:" + port + "/";
    }

    @AfterEach
    void teardown() {
        // Cierra el navegador después de cada prueba
        if (driver != null) {
            driver.quit();
        }
    }

    // --- Prueba 1: Login Exitoso ---
    @Test
    void testLogin_CredencialesValidas_DebeIrAHome() {
        driver.get(baseUrl); // Navega a la URL de la aplicación

        // Ingresa credenciales válidas
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("password123");

        // Hace clic en el botón (Buscado por su etiqueta HTML <button>)
        driver.findElement(By.tagName("button")).click();

        // Verifica que la página de destino es la correcta ("Bienvenido" es el título de home.html)
        String pageTitle = driver.getTitle();
        assertTrue(pageTitle.contains("Bienvenido"),
                "El login exitoso debe llevar a la página de Bienvenida.");
    }

    // --- Prueba 2: Login Fallido ---
    @Test
    void testLogin_CredencialesInvalidas_DebeMostrarMensajeDeError() {
        driver.get(baseUrl); // Navega a la URL de la aplicación

        // Ingresa credenciales inválidas
        driver.findElement(By.name("username")).sendKeys("usuario-malo");
        driver.findElement(By.name("password")).sendKeys("clave-incorrecta");

        // Hace clic en el botón
        driver.findElement(By.tagName("button")).click();

        // Verifica que se mantiene en la página de login y el mensaje de error es visible.
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Credenciales incorrectas"),
                "El login fallido debe mostrar el mensaje de 'Credenciales incorrectas'.");
    }
}
