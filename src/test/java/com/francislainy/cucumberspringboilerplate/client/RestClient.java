package com.francislainy.cucumberspringboilerplate.client;

import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestClient {

    public RequestSpecification getRequestSpecification() {

        /* Enables printing request as curl under the terminal as per https://github.com/dzieciou/curl-logger */
        Options options = Options.builder()
                .printMultiliner()
                .updateCurl(curl -> curl
                        .removeHeader("Host")
                        .removeHeader("User-Agent")
                        .removeHeader("Connection"))
                .build();

        RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(options);

        return given()
                .config(config)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .urlEncodingEnabled(false)
                .when()
                .log()
                .everything();
    }

}
