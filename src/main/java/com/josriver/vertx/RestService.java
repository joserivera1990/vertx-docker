package com.josriver.vertx;

import com.josriver.vertx.Model.Car;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class RestService extends AbstractVerticle {
    @Override
    public void start(Future<Void> future) {

        Router router = Router.router(vertx);
        router.get("/api/vertx/car/model/:id")
                .handler(this::getCar);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(config().getInteger("http.port", 8080), result -> {
                    if (result.succeeded()) {
                        future.complete();
                    } else {
                        future.fail(result.cause());
                    }
                });
    }

    private void getCar(RoutingContext routingContext) {
        String id = routingContext.request()
                .getParam("id");
        Car article = new Car(id, 2008);

        routingContext.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(200)
                .end(Json.encodePrettily(article));
    }

}
