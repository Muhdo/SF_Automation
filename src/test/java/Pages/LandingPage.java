package Pages;

import BaseClasses.PageBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.NoSuchElementException;

public class LandingPage extends PageBase {
    public LandingPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@data-test-id= \"characterName\"]")
    private WebElement searchBox;

    @FindBy(xpath = "//p[@class = \"CharacterListItem_name__2IwyZ\"]")
    private List<WebElement> characterNames;

    @FindBy(xpath = "//p[@class = \"CharacterList_message__suJR9 utility_textLight__S707d\"]")
    private WebElement noResults;

    @FindBy(xpath = "//button[@data-test-id = \"list-Favorites\"]")
    private WebElement favoriteBtn;

    @FindBy(xpath = "//p[@class = \"CharacterList_message__suJR9 utility_textLight__S707d\"]")
    private WebElement noFavorite;

    @FindBy(xpath = "//button[@data-test-id= \"list-List\"]")
    private WebElement mainList;

    @FindBy(xpath = "//p[@class = \"CharacterListItem_status__2tROd\"]")
    private WebElement status;

    @FindBy(xpath = "//select[@data-test-id = \"locationList\"]")
    private WebElement location;

    @FindBy(xpath = "//select[@data-test-id = \"locationList\"]//option")
    private WebElement allOptions;


    public void inputCharacterName(String name) {
        enterText(searchBox, name);
        boolean isMatchFound = false;

        for (WebElement character : characterNames) {
            String characters = character.getText();
            //System.out.println(characters);
            if (characters.contains(name)) {
                System.out.println("Match found: " + name);
                isMatchFound = true;
                break;
            }

        }
        Assert.assertTrue(isMatchFound, "The searched item '" + name + "' was not found in the search results.");

    }

    public void clickOnACharacter(String name) {
        boolean invalidCharacter = false;
        for (WebElement element : characterNames) {
            String character = element.getText();
            if (character.equals(name)) {
                element.click();
                invalidCharacter = true;
                break;
            }
        }
        Assert.assertTrue(invalidCharacter, "Character '" + name + "' does not exist or was not found.");
    }

    public void totalNumOfCharacters() {
        int sizes = characterNames.size();
        System.out.println(sizes);

    }

    public void searchForCharacter(String name) {
        enterText(searchBox, name);

    }

    public boolean noResultsIsDisplayed() {
        boolean result = noResults.isDisplayed();
        System.out.println(result);
        return result;
    }

    public void clickFavoriteBtn() {
        favoriteBtn.click();
    }

    public boolean characterIsDisplayed(String name) {
        for (WebElement element : characterNames) {
            String character = element.getText();
            if (character.equals(name)) {
                return element.isDisplayed();
            }
        }
        return false;

    }

    public boolean noFavoriteCharacterIsDisplayed(){
        boolean result = noFavorite.isDisplayed();
        System.out.println(result);
        return result;
    }

    public void clickMainList(){
        mainList.click();
    }

    public void characterStatus(){
        boolean characterStat = false;
        String result = status.getText();
        if(result.equals("Alive") || result.equals("Dead")){
            characterStat = true;
        }
        Assert.assertTrue(characterStat, "The character is neither dead or alive");
    }

    public void clickLocationBtn(){
        location.click();
    }

    public void selectOption(String name){
        Select select = new Select(location);
        select.selectByVisibleText(name);
    }

//    public void verifyCharactersAreDisplayed(){
//        Assert.assertFalse(characterNames.isEmpty(), "No results displayed");
//    }


    public void verifyCharactersAreDisplayed() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(characterNames.isEmpty(), "No characters are displayed.");
        softAssert.assertAll();
    }

    public void clearSearchField(){
        clearInputField(searchBox);
    }



}
