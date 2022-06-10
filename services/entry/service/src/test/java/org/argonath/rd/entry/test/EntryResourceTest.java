package org.argonath.rd.entry.test;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class EntryResourceTest {

    @Test
    public void testGetEntry() {
        given()
                .when().get("/entry/testEntity")
                .then()
                .statusCode(200);
    }

}