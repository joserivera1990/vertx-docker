package com.josriver.vertx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class RestServiceTest {

    private Vertx vertx;

    @Before
    public void setup(TestContext testContext) {
        vertx = Vertx.vertx();

        vertx.deployVerticle(RestService.class.getName(), testContext.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext testContext) {
        vertx.close(testContext.asyncAssertSuccess());
    }

    @Test
    public void givenId_whenReceivedArticle_thenSuccess(TestContext testContext) {
        final Async async = testContext.async();

        vertx.createHttpClient()
                .getNow(8080, "localhost", "/api/vertx/car/model/12345", response -> {
                    response.handler(responseBody -> {
                        testContext.assertTrue(responseBody.toString()
                                .contains("\"model\" : \"12345\""));
                        async.complete();
                    });
                });
    }

}
