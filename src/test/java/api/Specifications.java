package api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specifications {

    @Step("Создание RequestSpecification с базовым URI: {url}")
    public static RequestSpecification requestSpec(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .build();
    }

    @Step("Создание ResponseSpecification с ожидаемым статусом 200 OK")
    public static ResponseSpecification responseSpecOK200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    @Step("Создание ResponseSpecification с ожидаемым статусом 400 Bad Request")
    public static ResponseSpecification responseSpecBadRequest400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    @Step("Создание ResponseSpecification с ожидаемым статусом 404 Not Found")
    public static ResponseSpecification responseSpecNotFound404() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }

    @Step("Установка request и response спецификаций для RestAssured")
    public static void installSpecifications(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}