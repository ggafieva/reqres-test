package api;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.restassured.response.Response;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String URL = "https://reqres.in";
    static SoftAssert softAssert = new SoftAssert();

    @Epic("Reqres")
    @Feature("User Management")
    @Description("Проверка наличия аватара и корректности ID у каждого пользователя на второй странице.")
    @Test(testName = "Проверка аватара и ID пользователей")
    public void checkAvatarAndIdTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<UserData> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

        users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));

        softAssert.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x->x.getId().toString()).collect(Collectors.toList());

        for(int i = 0; i < avatars.size(); i++) {
            softAssert.assertTrue(avatars.get(i).contains((ids.get(i))));
        }
        softAssert.assertAll();
    }

    @Epic("Reqres")
    @Feature("UserData")
    @Description("Проверка получения данных одного пользователя по его ID.")
    @Test(testName = "Проверка получения одного пользователя")
    public void getSingleUserTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Integer id = 2;
        UserData user = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/api/users/2")
                .then().log().all()
                .extract().body().jsonPath().getObject("data", UserData.class);
        Assert.assertEquals(id, user.getId());
    }

    @Epic("Reqres")
    @Feature("UserData")
    @Description("Проверка получения несуществующего пользователя. Ожидается ошибка 404.")
    @Test(testName = "Проверка получения несуществующего пользователя")
    public void getSingleUserNotFoundTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecNotFound404());
        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/api/users/23")
                .then().log().all()
                .extract().response();
        softAssert.assertEquals(404, response.getStatusCode());
        softAssert.assertEquals("{}", response.getBody().asString());
        softAssert.assertAll();
    }

    @Epic("Reqres")
    @Feature("SuccessReg")
    @Description("Проверка успешной регистрации с корректными данными.")
    @Test(testName = "Проверка успешной регистрации")
    public void successRegTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);
        softAssert.assertEquals(id, successReg.getId());
        softAssert.assertEquals(token, successReg.getToken());
        softAssert.assertAll();
    }

    @Epic("Reqres")
    @Feature("UnSuccessReg")
    @Description("Проверка ошибки регистрации при отсутствии пароля.")
    @Test(testName = "Проверка неуспешной регистрации")
    public void unSuccessRegTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecBadRequest400());
        String error =  "Missing password";
        Register user = new Register( "sydney@fife", "");
        UnSuccessReg unSuccessReg = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(UnSuccessReg.class);
        Assert.assertEquals(error, unSuccessReg.getError());
    }

    @Epic("Reqres")
    @Feature("SuccessLogin")
    @Description("Проверка успешного входа в систему.")
    @Test(testName = "Проверка успешного входа в систему")
    public void successLoginTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "cityslicka");
        SuccessLogin successLogin = given()
                .body(user)
                .when()
                .post("/api/login")
                .then().log().all()
                .extract().as(SuccessLogin.class);
        Assert.assertEquals(token, successLogin.getToken());
    }

    @Epic("Reqres")
    @Feature("UnSuccessLogin")
    @Description("Проверка ошибки входа при отсутствии пароля.")
    @Test(testName = "Проверка ошибки входа при отсутствии пароля")
    public void unSuccessLoginTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecBadRequest400());
        String error =  "Missing password";
        Register user = new Register( "peter@klaven", "");
        UnSuccessLogin unSuccessLogin = given()
                .body(user)
                .when()
                .post("/api/login")
                .then().log().all()
                .extract().as(UnSuccessLogin.class);
        Assert.assertEquals(error, unSuccessLogin.getError());
    }
}