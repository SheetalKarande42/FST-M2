package liveproject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GitHubProjectTest {
    RequestSpecification requestSpec;
      int keyId;
    @BeforeClass
    public void setUp() {
        //Request specification
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com/user/keys")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "token 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")
                .build();
    }

    @Test(priority=1)
    public void addSSHkeyPost(){
        Map<String,Object> reqBody= new HashMap<>();
        reqBody.put("title","TestAPIKey");
        reqBody.put("key","ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCYwulFoUyF8jusb5YqWXfifFAe7KrXS2uAWZILV2n6H5Co8RMvScqtFbb5RNjliBrGKMuIPJbxn2Pdi8qfQECLrVhGBsvv7U1tVs50ylIRVPuFsAUgJCgHcKyTTO5RPJQrOnOyP5r+nsF53I9D4g2+AYHGdIMe1tOy+tomaXu9s0NU2o9qZLazW0RfsQNNT166Y5t7bRBkGUm03F9d0rQnRf5jJ8KB1w4fpV0tUhKddeBkOcvk2ppmh3A4Qlna/ttJKJJqCwYWVqK8kMl6T/k9csgFSiQboVSAVDth4vud9/6PdZ2s8NDgYrn421YTVs07Japzf2U+HBYCJMxZRTkb");

        Response response= given().spec(requestSpec).body(reqBody).log().all()
                .when().post();
        System.out.println(response.getBody().asPrettyString());
        keyId=response.then().extract().path("id");

        response.then().statusCode(201);
    }

    @Test(priority=2)
    public void getAllSSHkeys(){
     given().spec(requestSpec).pathParam("keyId",keyId).log().all()
                .when().get("/{keyId}")
        .then().statusCode(200);
    }

    @Test(priority=3)
    public void deleteAllSSHkeys(){
         given().spec(requestSpec).pathParam("keyId",keyId).log().all()
                .when().delete("/{keyId}")
                 .then().statusCode(204);
    }
}
