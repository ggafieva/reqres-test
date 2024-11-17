package api;

import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String URL = "https://reqres.in";
    static SoftAssert softAssert = new SoftAssert();

    @DataProvider(name = "userData")
    public Object[][] userData() {
        return new Object[][]{
                {0, 7, "michael.lawson@reqres.in", "Michael", "Lawson", "https://reqres.in/img/faces/7-image.jpg"},
                {1, 8, "lindsay.ferguson@reqres.in", "Lindsay", "Ferguson", "https://reqres.in/img/faces/8-image.jpg"},
                {2, 9, "tobias.funke@reqres.in", "Tobias", "Funke", "https://reqres.in/img/faces/9-image.jpg"},
                {3, 10, "byron.fields@reqres.in", "Byron", "Fields", "https://reqres.in/img/faces/10-image.jpg"},
                {4, 11, "george.edwards@reqres.in", "George", "Edwards", "https://reqres.in/img/faces/11-image.jpg"},
                {5, 12, "rachel.howell@reqres.in", "Rachel", "Howell", "https://reqres.in/img/faces/12-image.jpg"}
        };
    }

    @DataProvider(name = "resourceData")
    public Object[][] resourceData() {
        return new Object[][]{
                {0, 1, "cerulean", 2000, "#98B2D1", "15-4020"},
                {1, 2, "fuchsia rose", 2001, "#C74375", "17-2031"},
                {2, 3, "true red", 2002, "#BF1932", "19-1664"},
                {3, 4, "aqua sky", 2003, "#7BC4C4", "14-4811"},
                {4, 5, "tigerlily", 2004, "#E2583E", "17-1456"},
                {5, 6, "blue turquoise", 2005, "#53B0AE", "15-5217"}
        };
    }

    @DataProvider(name = "successRegData")
    public Object[][] successRegData() {
        return new Object[][]{
                {4, "QpwL5tke4Pnpja7X4", "eve.holt@reqres.in", "pistol"}
        };
    }

    @DataProvider(name = "unSuccessRegData")
    public Object[][] unSuccessRegData() {
        return new Object[][]{
                {"sydney@fife", "", "Missing password"}
        };
    }

    @DataProvider(name = "successLoginData")
    public Object[][] successLoginData() {
        return new Object[][]{
                {"eve.holt@reqres.in", "cityslicka", "QpwL5tke4Pnpja7X4"}
        };
    }

    @DataProvider(name = "unSuccessLoginData")
    public Object[][] unSuccessLoginData() {
        return new Object[][]{
                {"peter@klaven", "", "Missing password"}
        };
    }

    @Epic("Reqres")
    @Feature("UserData")
    @Story("userData")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка получения списка пользователей.")
    @Link("https://reqres.in/api/users?page=2")
    @Test(testName = "Проверка получения списка пользователей", dataProvider = "userData")
    public void getUserListTest(int index, Integer id, String email, String first_name, String last_name, String avatar) {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<UserData> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        UserData user = users.get(index);
        softAssert.assertEquals(user.getId(), id);
        softAssert.assertEquals(user.getEmail(), email);
        softAssert.assertEquals(user.getFirst_name(), first_name);
        softAssert.assertEquals(user.getLast_name(), last_name);
        softAssert.assertEquals(user.getAvatar(), avatar);
        softAssert.assertAll();
    }

    @Epic("Reqres")
    @Feature("UserData")
    @Story("userData")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка получения данных одного пользователя.")
    @Link("https://reqres.in/api/users/")
    @Test(testName = "Проверка получения одного пользователя", dataProvider = "userData")
    public void getSingleUserTest(int index, Integer id, String email, String first_name, String last_name, String avatar) {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        UserData user = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/api/users/" + id.toString())
                .then().log().all()
                .extract().body().jsonPath().getObject("data", UserData.class);
        softAssert.assertEquals(user.getId(), id);
        softAssert.assertEquals(user.getEmail(), email);
        softAssert.assertEquals(user.getFirst_name(), first_name);
        softAssert.assertEquals(user.getLast_name(), last_name);
        softAssert.assertEquals(user.getAvatar(), avatar);
        softAssert.assertAll();
    }

    @Epic("Reqres")
    @Feature("UserData")
    @Story("Несуществующий пользователь.")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка получения несуществующего пользователя. Ожидается ошибка 404.")
    @Link("https://reqres.in/api/users/")
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
    @Feature("ResourceData")
    @Story("resourceData")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка получения списка ресурсов.")
    @Link("https://reqres.in/api/unknown")
    @Test(testName = "Проверка получения списка ресурсов", dataProvider = "resourceData")
    public void getResourceListTest(int index, Integer id, String name, Integer year, String color, String pantone_value) {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<ResourceData> resources = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ResourceData.class);
        ResourceData resource = resources.get(index);
        softAssert.assertEquals(resource.getId(), id);
        softAssert.assertEquals(resource.getName(), name);
        softAssert.assertEquals(resource.getYear(), year);
        softAssert.assertEquals(resource.getColor(), color);
        softAssert.assertEquals(resource.getPantone_value(), pantone_value);
        softAssert.assertAll();
    }

    @Epic("Reqres")
    @Feature("ResourceData")
    @Story("resourceData")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка получения одного ресурса.")
    @Link("https://reqres.in/api/unknown/")
    @Test(testName = "Проверка получения одного ресурса", dataProvider = "resourceData")
    public void getSingleResourceTest(int index, Integer id, String name, Integer year, String color, String pantone_value) {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        ResourceData resource = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/api/unknown/" + id.toString())
                .then().log().all()
                .extract().body().jsonPath().getObject("data", ResourceData.class);
        softAssert.assertEquals(resource.getId(), id);
        softAssert.assertEquals(resource.getName(), name);
        softAssert.assertEquals(resource.getYear(), year);
        softAssert.assertEquals(resource.getColor(), color);
        softAssert.assertEquals(resource.getPantone_value(), pantone_value);
        softAssert.assertAll();
    }

    @Epic("Reqres")
    @Feature("ResourceData")
    @Story("Несуществующий ресурс")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка получения несуществующего ресурса. Ожидается ошибка 404.")
    @Link("https://reqres.in/api/unknown/")
    @Test(testName = "Проверка получения несуществующего ресурса")
    public void getSingleResourceNotFoundTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecNotFound404());
        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/api/unknown/23")
                .then().log().all()
                .extract().response();
        softAssert.assertEquals(404, response.getStatusCode());
        softAssert.assertEquals("{}", response.getBody().asString());
        softAssert.assertAll();
    }

    @Epic("Reqres")
    @Feature("SuccessReg")
    @Story("successRegData")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка успешной регистрации с корректными данными.")
    @Link("https://reqres.in/api/register")
    @Test(testName = "Проверка успешной регистрации", dataProvider = "successRegData")
    public void successRegTest(Integer id, String token, String email, String password) {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Register user = new Register(email, password);
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
    @Story("unSuccessRegData")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка ошибки регистрации при отсутствии пароля.")
    @Link("https://reqres.in/api/register")
    @Test(testName = "Проверка неуспешной регистрации при отсутствии пароля", dataProvider = "unSuccessRegData")
    public void unSuccessRegTest(String email, String password, String error) {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecBadRequest400());
        Register user = new Register(email, password);
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
    @Story("successLoginData")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка успешного входа в систему.")
    @Link("https://reqres.in/api/login")
    @Test(testName = "Проверка успешного входа в систему", dataProvider = "successLoginData")
    public void successLoginTest(String email, String password, String token) {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Login user = new Login(email, password);
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
    @Story("unSuccessLoginData")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка ошибки входа при отсутствии пароля.")
    @Link("https://reqres.in/api/login")
    @Test(testName = "Проверка ошибки входа при отсутствии пароля", dataProvider = "unSuccessLoginData")
    public void unSuccessLoginTest(String email, String password, String error) {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecBadRequest400());
        Login user = new Login(email, password);
        UnSuccessLogin unSuccessLogin = given()
                .body(user)
                .when()
                .post("/api/login")
                .then().log().all()
                .extract().as(UnSuccessLogin.class);
        Assert.assertEquals(error, unSuccessLogin.getError());
    }
}