package com.telran.contacts.tests;

import com.jayway.restassured.RestAssured;
import com.telran.contacts.dto.AuthRequestDto;
import com.telran.contacts.dto.ContactDto;
import com.telran.contacts.dto.GetAllContactsDto;
import com.telran.contacts.dto.LoginRegResponseDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestAssuredTests {

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InBsQGdtYWlsLmNvbSJ9.7FRf92QfOeZJ_ZqDRWsnvHsg_vbs8_Z1jJywJFIiV78";

    @BeforeMethod
    public void ensurePrecondition() {

        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";

    }

    // check wy 404 instead 200
    @Test
    public void loginPositiveTest() {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("pl@gmail.com")
                .password("Hh1234567~")
                .build();

        LoginRegResponseDto responseDto = given()
                .contentType("application/json")
                .body(requestDto)
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(LoginRegResponseDto.class);
        System.out.println(responseDto.getToken());

        //String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InBsQGdtYWlsLmNvbSJ9.7FRf92QfOeZJ_ZqDRWsnvHsg_vbs8_Z1jJywJFIiV78";

        String token2 = given().contentType("application/json")
                .body(requestDto)
                .post()
                .then()
                .assertThat().statusCode(200)
                .body(containsString("token"))
                .body("token", equalTo(token))
                .extract().path("token");

        System.out.println(token2);
    }
    @Test
    public void loginNegativeTestWithInvalidPassword () {

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("pl@gmail.com")
                .password("Hh1234567")
                .build();

        String message = given().contentType("application/json")
                .body(requestDto)
                .post("login")
                .then()
                .assertThat().statusCode(400)
                .extract().path("message");

        System.out.println(message);

    }

    @Test
    public void addNewContactPositive() {

        int i = (int)((System.currentTimeMillis()/1000)%3600);

        ContactDto contactDto = ContactDto.builder()
                .address("Berlin")
                .description("lawyer")
                .email("gh" + i +"@gmail.com")
                .lastName("Braun")
                .name("Phill")
                .phone("12345678" + i)
                .build();

        int id = given().header("Authorization", token)
                .contentType("application/json")
                .body(contactDto)
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");

        System.out.println(id);
    }

    @Test
    public void getAllContactsTest(){

        GetAllContactsDto responseDto = given()
                .header("Authorization",token)
                .get("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().body().as(GetAllContactsDto.class);

        for (ContactDto contactDto: responseDto.getContacts()) {
            System.out.println(contactDto.getId() + "***" + contactDto.getLastName() + "***");
            System.out.println("==================================================");
        }

    }
    @Test
    public void deleteContactTest(){
        String status = given().header("Authorization", token)
                .delete("/contact/5828")
                .then()
                .assertThat().statusCode(200)
                .extract().path("status");
        System.out.println(status);
    }

}
