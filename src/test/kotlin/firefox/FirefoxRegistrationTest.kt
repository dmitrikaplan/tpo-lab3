package firefox

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.ElementClickInterceptedException
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.time.Duration

class FirefoxRegistrationTest {

    private lateinit var driver: RemoteWebDriver

    @BeforeEach
    fun setUp() {
        driver = FirefoxOptions().apply {
            this.addPreference("privacy.trackingprotection.enabled", true)
            this.addPreference("privacy.trackingprotection.emailtracking.enabled", true)
            this.addPreference("privacy.trackingprotection.socialtracking.enabled", true)
            this.addPreference("privacy.resistFingerprinting", true)
        }.let { options ->
            FirefoxDriver(options).apply {
                this.manage()
                    .timeouts()
                    .implicitlyWait(Duration.ofSeconds(5))
            }
        }
    }


    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun `registration test`() {
        driver.get("https://www.lamoda.ru/")
        driver.findElement(By.xpath("//button[text() = 'Войти']")).click()
        driver.findElement(By.xpath("//a[text() = 'Зарегистрироваться']")).click()
        driver.findElement(By.xpath("//input[@name='Имя']")).sendKeys("Дмитрий")
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("9198747726")
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("newton044")
        driver.findElement(By.xpath("//input[@name='password_confirmation']")).sendKeys("newton044")
        driver.findElement(By.xpath("(//input[@name='Электронная почта'])[3]")).sendKeys("dmitry@kaplaan.ru")
        driver.findElement(By.xpath("//button[text()='Зарегистрироваться']")).let {
            Assertions.assertTrue(it.isEnabled)
            it.click()
        }
    }


    @Test
    fun `registration test without phone`() {
        driver.get("https://www.lamoda.ru/")
        driver.findElement(By.xpath("//button[text() = 'Войти']")).click()
        driver.findElement(By.xpath("//a[text() = 'Зарегистрироваться']")).click()
        driver.findElement(By.xpath("//input[@name='Имя']")).sendKeys("Дмитрий")
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("newton044")
        driver.findElement(By.xpath("//input[@name='password_confirmation']")).sendKeys("newton044")
        driver.findElement(By.xpath("(//input[@name='Электронная почта'])[3]")).sendKeys("dmitry@kaplaan.ru")
        driver.findElement(By.xpath("//button[text()='Зарегистрироваться']")).let {
            Assertions.assertFalse(it.isEnabled)
            Assertions.assertThrows(ElementClickInterceptedException::class.java){
                it.click()
            }
        }
    }
}