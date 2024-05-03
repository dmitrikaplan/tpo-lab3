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

class ChromeAddingToCartTest {


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
    fun `adding to cart test`() {
        driver.also {
            it.get("https://lamoda.ru/")
            it.findElement(By.xpath("//input[@placeholder='Поиск']")).sendKeys("Штаны", Keys.ENTER)
            it.findElements(By.xpath("//div[@class='_area_552z7_8']")).first().also { element ->
                Assertions.assertTrue(element.isEnabled)
                element.click()
            }
            it.findElement(By.xpath("//div[@title='Закрыть']")).click()
            it.findElement(By.xpath("//div[text()='Выберите размер']")).click()
            it.findElement(By.xpath("//div[@class='_colspan_8karg_150 ui-product-page-sizes-chooser-item_enabled ui-product-page-sizes-chooser-item'][1]"))
                .click()
            it.findElement(By.xpath("//button[@class='x-button x-button_primaryFilledWeb9131 x-button_32 x-button_intrinsic-width _accept_72lhl_18']"))
                .click()
            it.findElement(By.cssSelector(".x-button_primaryPremium > span")).click()
            it.findElement(By.xpath("//div[@title='Закрыть']")).click()
            it.findElement(By.xpath("//span[text()='Корзина']")).click()
            it.findElements(By.xpath("//div[@class='_root_ko34a_2']")).also { elements ->
                Assertions.assertTrue(elements.size > 0)
            }
        }


    }


}