package com.telran.ilCarro.tests;

import com.jayway.restassured.RestAssured;
import com.telran.ilCarro.dto.PutUserDto;
import com.telran.ilCarro.dto.RegistrationRequestDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class RestAssuredTests {

    @BeforeMethod
    public void ensurePrecondition() {

        RestAssured.baseURI = "https://java-3-ilcarro-team-b.herokuapp.com";
    }

    //registration tests
    @Test
    public void registrationPositiveTest(){

        RegistrationRequestDto requestDto = RegistrationRequestDto.builder()
                .first_name("Luca")
                .second_name("Lopa")
                .build();

        String token = "bHUrMUB5eC5jb206bzc0NTIzNFRi";

        int statusCode = RestAssured.given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(requestDto)
                .post("/registration")
                .then()
                .assertThat().statusCode(200)
                .extract().statusCode();

        System.out.println(statusCode);

    }

    @Test
    public void registrationNegativeWithInvalidEmail(){

        RegistrationRequestDto requestDto = RegistrationRequestDto.builder()
                .first_name("Luca")
                .second_name("Lopa")
                .build();

        String token = "bHVAeXhjb206bzc0NTIzNFQ=";

        int statusCode = RestAssured.given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(requestDto)
                .post("/registration")
                .then()
                .assertThat().statusCode(400)
                .extract().statusCode();

        System.out.println(statusCode);

    }

    @Test
    public void registrationNegativeDeletedUser(){

        RegistrationRequestDto requestDto = RegistrationRequestDto.builder()
                .first_name("Luca")
                .second_name("Lopa")
                .build();

        String token = "bHVAeXguY29tOm83NDUyMzRUYg==";

        int statusCode = RestAssured.given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(requestDto)
                .post("/registration")
                .then()
                .assertThat().statusCode(403)
                .extract().statusCode();

        System.out.println(statusCode);

    }

    @Test
    public void registrationNegativeTestExistedUser(){

        RegistrationRequestDto requestDto = RegistrationRequestDto.builder()
                .first_name("Luca")
                .second_name("Lopa")
                .build();

        String token = "bHVAeXguY29tOm83NDUyMzRU";

        int statusCode = RestAssured.given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(requestDto)
                .post("/registration")
                .then()
                .assertThat().statusCode(409)
                .extract().statusCode();

        System.out.println(statusCode);

    }

    // delete tests
    @Test
    public void deleteUserTest(){

        String token = "bHVAeXguY29tOm83NDUyMzRUYg==";

        int status = given().header("Authorization", token)
                .delete("/user")
                .then()
                .assertThat().statusCode(200)
                .extract().statusCode();
        System.out.println(status);
    }

    @Test
    public void deleteUserBadRequestTest(){

        String token = "bmljazBAZ20uY29tOm83NDUyMzRUdA=";

        int status = given().header("Authorization", token)
                .delete("/user")
                .then()
                .assertThat().statusCode(400)
                .extract().statusCode();
        System.out.println(status);
    }

    @Test
    public void deleteUserUnauthorizedUserTest(){

        String token = "bHUrMUB5eC5jb206bzc0NTIzNFRy";

        int status = given().header("Authorization", token)
                .delete("/user")
                .then()
                .assertThat().statusCode(401)
                .extract().statusCode();
        System.out.println(status);
    }

    @Test
    public void deleteUserNotFoundTest(){

        String token = "bHUrMkB5eC5jb206bzc0NTIzNFRi";

        int status = given().header("Authorization", token)
                .delete("/user")
                .then()
                .assertThat().statusCode(404)
                .extract().statusCode();
        System.out.println(status);
    }

    //put tests

    @Test
    public void userUpdatePositiveTest(){

        PutUserDto putUserDto = PutUserDto.builder()
                .first_name("Luca")
                .second_name("Lopa")
                .build();

        String token = "bHUrMUB5eC5jb206bzc0NTIzNFRi";

        String  status = RestAssured.given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(putUserDto)
                .put("/user")
                .then()
                .assertThat().statusCode(200)
                .extract().path("first_name");

        int  statusCode = RestAssured.given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(putUserDto)
                .put("/user")
                .then()
                .assertThat().statusCode(200)
                .extract().statusCode();

        System.out.println(statusCode);
        System.out.println(status);

    }

    @Test
    public void putUserBadRequestNegativeTest(){

        PutUserDto putUserDto = PutUserDto.builder()
                .first_name("Lupa")
                .second_name("Lopa")
                .build();

        String token = "bHVAeXguY29tOm83NDUyMzRiaw==";

        int statusCode = RestAssured.given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(putUserDto)
                .put("/user")
                .then()
                .assertThat().statusCode(400)
                .extract().statusCode();

        System.out.println(statusCode);

    }

    @Test
    public void putUnauthorizedUserNegativeTest(){

        PutUserDto putUserDto = PutUserDto.builder()
                .first_name("Lupa")
                .second_name("Lopa")
                .build();

        String token = "bHUrMUB5eC5jb206bzc0NTIzNFRy";

        int statusCode = RestAssured.given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(putUserDto)
                .put("/user")
                .then()
                .assertThat().statusCode(401)
                .extract().statusCode();

        System.out.println(statusCode);

    }

    @Test
    public void putNonExistentUserNegativeTest(){

        PutUserDto putUserDto = PutUserDto.builder()
                .first_name("Lupa")
                .second_name("Lopa")
                .build();

        String token = "bHUrMkB5eC5jb206bzc0NTIzNFRi";

        int statusCode = RestAssured.given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(putUserDto)
                .put("/user")
                .then()
                .assertThat().statusCode(404)
                .extract().statusCode();

        System.out.println(statusCode);

    }

}

