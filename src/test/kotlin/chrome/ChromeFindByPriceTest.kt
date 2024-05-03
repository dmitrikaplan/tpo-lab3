package chrome

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.Keys
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.RemoteWebDriver
import java.time.Duration

class ChromeFindByPriceTest {

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


    @ParameterizedTest
    @ValueSource(ints = [1_000, 9_000, 100_000])
    fun `find product by min price`(minPrice: Int){
        driver.also {
            it.get("https://lamoda.ru/")
            it.findElement(By.xpath("//input[@placeholder='Поиск']")).sendKeys("Штаны", Keys.ENTER)
            it.findElement(By.xpath("//span[text()='Цена']")).also { button ->
                Assertions.assertTrue(button.isEnabled)
                button.click()
            }
            it.findElement(By.xpath("//input[@name='minRange']")).sendKeys(minPrice.toString())
            it.findElement(By.xpath("//button[text()='Применить']")).also { button ->
                Assertions.assertTrue(button.isEnabled)
                button.click()
            }
            it.findElements(By.xpath("//span[@class='_price_1rcja_8 x-product-card-description__price-single']"))
                .map{ elements -> elements.text}
                .forEach { price ->
                    price.replace(" ", "").replace("₽", "").toInt().also { priceInt ->
                        Assertions.assertTrue(priceInt >= minPrice)
                    }
                }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [1_000, 100_000])
    fun `find product by max price`(maxPrice: Int){
        driver.also {
            it.get("https://lamoda.ru/")
            it.findElement(By.xpath("//input[@placeholder='Поиск']")).sendKeys("Штаны", Keys.ENTER)
            it.findElement(By.xpath("//span[text()='Цена']")).also { button ->
                Assertions.assertTrue(button.isEnabled)
                button.click()
            }
            it.findElement(By.xpath("//input[@name='maxRange']")).sendKeys(maxPrice.toString())
            it.findElement(By.xpath("//button[text()='Применить']")).also { button ->
                Assertions.assertTrue(button.isEnabled)
                button.click()
            }
            it.findElements(By.xpath("//span[@class='_price_1rcja_8 x-product-card-description__price-single']"))
                .map{ elements -> elements.text}
                .forEach { price ->
                    price.replace(" ", "").replace("₽", "").toInt().also { priceInt ->
                        Assertions.assertTrue(priceInt <= maxPrice)
                    }
                }
        }
    }


    @ParameterizedTest
    @ValueSource(ints = [200_000, 300_000, 400_000])
    fun `checking min and max range `(min: Int){
        driver.also {
            it.get("https://lamoda.ru/")
            it.findElement(By.xpath("//input[@placeholder='Поиск']")).sendKeys("Штаны", Keys.ENTER)
            it.findElement(By.xpath("//span[text()='Цена']")).also { button ->
                Assertions.assertTrue(button.isEnabled)
                button.click()
            }

            it.findElement(By.xpath("//input[@name='minRange']")).sendKeys(min.toString())
            it.findElement(By.xpath("//input[@name='maxRange']")).sendKeys("0")

            it.findElement(By.xpath("//button[text()='Применить']")).also { button ->
                Assertions.assertTrue(button.isEnabled)
            }
        }
    }
}