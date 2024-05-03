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

class ChromeSearchProductTest {

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
    @ValueSource(strings = ["штаны", "футболка", "носки"])
    fun `search product`(value: String) {
        driver.get("https://lamoda.ru/")
        driver.findElement(By.xpath("//input[@placeholder='Поиск']")).sendKeys(value, Keys.ENTER)
        driver.findElements(By.xpath("//div[@class='_area_552z7_8']")).also {
            Assertions.assertTrue(it.size > 0)
        }
    }
}