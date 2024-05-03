package chrome

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.Keys
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.RemoteWebDriver
import java.time.Duration

class ChromeFindByBrandTest {

    private lateinit var driver: RemoteWebDriver


    @BeforeEach
    fun setUp() {
        driver = ChromeDriver().apply {
            this.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(5))

            this.manage()
                .window()
                .size = Dimension(1800, 900)
        }
    }
    @AfterEach
    fun tearDown() {
        driver.quit()
    }


    @Test
    fun `find by brand name`() {
        driver.also {
            it.get("https://lamoda.ru/")
            it.findElement(By.xpath("//input[@placeholder='Поиск']")).sendKeys("Штаны", Keys.ENTER)
            it.findElement(By.xpath("//span[text()='Бренд']")).also { button ->
                Assertions.assertTrue(button.isEnabled)
                button.click()
            }
            it.findElement(By.xpath("//input[@placeholder='Найдите бренд']")).sendKeys("Sela")
            it.findElement(By.xpath("//span[text()='Sela']")).also { element ->
                Assertions.assertTrue(element.isEnabled)
                element.click()
            }
            it.findElement(By.xpath("//button[text()='Применить']")).also { element ->
                Assertions.assertTrue(element.isEnabled)
                element.click()
            }
            Thread.sleep(1000L)
            it.findElements(By.xpath("//div[@class='x-product-card-description__brand-name _brandName_1rcja_6 x-product-card-description__brand-name_faded']"))
                .forEach { brandName ->
                    Assertions.assertEquals("Sela", brandName.text)
                }
        }
    }
}