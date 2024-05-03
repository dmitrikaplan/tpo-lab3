package chrome

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.ElementClickInterceptedException
import org.openqa.selenium.firefox.FirefoxDriver as ChromeDriver
import org.openqa.selenium.remote.RemoteWebDriver
import java.time.Duration

class СhromeLoginTest {


    private lateinit var driver: RemoteWebDriver

    @BeforeEach
    fun setUp(){
        driver = ChromeDriver().apply {
                this.manage()
                    .timeouts()
                    .implicitlyWait(Duration.ofSeconds(5))
        }
    }

    @AfterEach
    fun tearDown(){
        driver.quit()
    }

    @Test
    fun `login test`(){
        driver.get("https://lamoda.ru/")
        driver.findElement(By.xpath("//button[text() = 'Войти']")).click()
        driver.findElement(By.xpath("(//input[@name='Электронная почта'])[2]")).sendKeys("dmitry@kaplaan.ru")
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("newton044")
        driver.findElement(By.xpath("(//button[text()='Войти'])[2]")).let {
            Assertions.assertTrue(it.isEnabled)
            it.click()
        }
    }


    @Test
    fun `login test without password`(){
        driver.get("https://lamoda.ru/")
        driver.findElement(By.xpath("//button[text() = 'Войти']")).click()
        driver.findElement(By.xpath("(//input[@name='Электронная почта'])[2]")).sendKeys("dmitry@kaplaan.ru")
        driver.findElement(By.xpath("(//button[text()='Войти'])[2]")).let {
            Assertions.assertFalse(it.isEnabled)
            Assertions.assertThrows(ElementClickInterceptedException::class.java){
                it.click()
            }
        }
    }
}