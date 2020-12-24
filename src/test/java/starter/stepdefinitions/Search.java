package starter.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Step;

import com.microsoft.playwright.*;

import java.nio.file.Paths;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

public class Search {
    Playwright playwright;
    Page page;
    Browser browser;
    BrowserContext context;



    @Step
    @Given("Sergey is on the DuckDuckGo home page")
    public void sergey_is_on_the_DuckDuckGo_home_page() {

        playwright = Playwright.create();
        browser =  playwright.chromium().launch();
        context = browser.newContext(
                new Browser.NewContextOptions().withViewport(800, 600));
        page = context.newPage();
        page.navigate("https://duckduckgo.com/");
    }

    @Step
    @When("he searches for {string}")
    public void he_searches_for(String string) throws Exception{
            page.screenshot(new Page.ScreenshotOptions().withPath(Paths.get("screenshot-chromium"  + ".png")));
            page.fill("input[name=\"q\"]",string);
            page.click("input[id=\"search_button_homepage\"]");
        page.screenshot(new Page.ScreenshotOptions().withPath(Paths.get("screenshot-after_search"  + ".png")));

    }

    @Step
    @Then("all the result titles should contain the word {string}")
    public void all_the_result_titles_should_contain_the_word(String string) throws Exception {
        String result=page.innerText("span[class=\"module__title__link\"]").toString();
        assertThat(result, containsString(string));
        browser.close();
        playwright.close();
    }

}
