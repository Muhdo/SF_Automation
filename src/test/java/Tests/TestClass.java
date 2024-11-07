package Tests;

import BaseClasses.TestBase;
import Pages.LandingPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestClass extends TestBase {
    LandingPage landingPage;

    public void initializer(){
        landingPage = new LandingPage(driver);
    }

    @Test(priority = 1, description = "Validate that the user can access the url")
    public void ValidateThatUserCanAccessTheURL(){
        initializer();
        driver.get(testData.getProperty("baseUrl"));
        System.out.println(driver.getCurrentUrl());
        Assert.assertEquals(driver.getCurrentUrl(),testData.getProperty("baseUrl"), "The current URL does not match the base URL");
    }

    @Test(priority = 2, description = "Validate that user can search for characters by last name, first name and fullname", dependsOnMethods = "ValidateThatUserCanAccessTheURL")
    public void ValidateThatUserCanSearchForCharacters(){

        landingPage.inputCharacterName(testData.getProperty("firstname"));
        landingPage.characterIsDisplayed(testData.getProperty("firstname"));
        sleep(2);
        landingPage.clearSearchField();
        sleep(3);
        landingPage.inputCharacterName(testData.getProperty("lastname"));
        landingPage.characterIsDisplayed(testData.getProperty("lastname"));
        sleep(2);
        landingPage.clearSearchField();
        sleep(3);
       //search with full name
        landingPage.inputCharacterName(testData.getProperty("searchParameter"));
        landingPage.characterIsDisplayed(testData.getProperty("searchParameter"));
        sleep(2);
        landingPage.clearSearchField();
        sleep(3);

    }

    @Test(priority = 3, description = "Validate that user can add character to favorite list and when user adds character to favorite, the character no longer remains in the main list", dependsOnMethods = "ValidateThatUserCanAccessTheURL")
    public void validateFavoriteCharacterNotInMainList(){
        //the method below landingPage.clickOnACharacter() works in such a way that if an invalid character is passed as a search parameter, an assertion error is thrown and test priority 4 is ignored as it depends on this test(test priority 3)
        landingPage.clickOnACharacter(testData.getProperty("searchParameter"));
        sleep(3);
        landingPage.searchForCharacter(testData.getProperty("searchParameter"));
        Assert.assertTrue(landingPage.noResultsIsDisplayed(),"Character is still displayed in the main list");

    }

    @Test(priority = 4,description = "Validate that when user favorites a character that the character is displayed under the favorite list and when user unfavorite a character, it is no longer displayed in the favorite list", dependsOnMethods = "validateFavoriteCharacterNotInMainList")
    public void ValidateThatCharacterIsAddedToFavoriteList(){
        landingPage.clickFavoriteBtn();
        Assert.assertTrue(landingPage.characterIsDisplayed(testData.getProperty("searchParameter")), "The character is not displayed in the favorite list");
        sleep(3);
        landingPage.clickOnACharacter(testData.getProperty("searchParameter"));
        landingPage.noFavoriteCharacterIsDisplayed();
        sleep(3);
        landingPage.clickMainList();
        landingPage.characterIsDisplayed(testData.getProperty("searchParameter"));
        sleep(3);
        landingPage.clearSearchField();
        sleep(3);
        driver.navigate().refresh();

    }


    @Test(priority = 5, description = "filter search by location", dependsOnMethods = "ValidateThatUserCanAccessTheURL")
    public void ValidateThatUserCanFilterCharacterSearchByLocation(){
        //this checks the filters returning no results
//        landingPage.selectOption("Interdimentional Cable");
//        landingPage.verifyCharactersAreDisplayed();

        //this checks the filters returning results
        //Abadango
        landingPage.selectOption("Interdimentional Cable");
        landingPage.verifyCharactersAreDisplayed();
        sleep(3);
        landingPage.selectOption("All Locations");
        sleep(3);
    }

    @Test(priority = 6, description = "filter search by character name and location", dependsOnMethods = "ValidateThatUserCanAccessTheURL")
    public void ValidateThatUserCanFilterCharacterSearchByNameAndLocation(){
        landingPage.searchForCharacter(testData.getProperty("searchParameter"));
        landingPage.selectOption("Citadel of Ricks");
        landingPage.verifyCharactersAreDisplayed();
        sleep(3);
        landingPage.clearSearchField();
        landingPage.selectOption("All Locations");
        sleep(5);

    }

    @Test(priority = 7, description = "Validate that characters are labelled either dead or alive", dependsOnMethods = "ValidateThatUserCanAccessTheURL" )
    public void ValidateThatCharacterIsLabelledDeadOrAlive(){
        landingPage.searchForCharacter("Abradolf Lincler");
        //Abadango Cluster Princess - alive character
        //Adjudicator Rick - dead character
        //Abradolf Lincler - unknown status
        landingPage.characterStatus();
        landingPage.clearSearchField();
        sleep(5);
    }


}
