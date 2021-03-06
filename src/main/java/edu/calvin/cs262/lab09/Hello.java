package edu.calvin.cs262.lab09;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiIssuer;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod.GET;

@Api(
    name = "question",
    version = "v1",
    namespace =
    @ApiNamespace(
        ownerDomain = "lab09.cs262.calvin.edu",
        ownerName = "lab09.cs262.calvin.edu",
        packagePath = ""
    ),
    issuers = {
        @ApiIssuer(
            name = "firebase",
            issuer = "https://securetoken.google.com/phonic-biplane-221307",
            jwksUri =
                "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system"
                    + ".gserviceaccount.com"
        )
    }
)

/**
 * This class implements a simple hello-world endpoint using Google Endpoints.
 */
public class Hello {

    /**
     * This method returns a simple hello-world message.
     *
     * N.b., a Google Endpoint must return an entity (not, e.g., a String), so the method
     * returns a "hello" person object.
     *
     * @return a hello-world entity in JSON format
     */
    @ApiMethod(httpMethod=GET)
    public Question hello() {
        // 1) create a java calendar instance
        Calendar calendar = Calendar.getInstance();

        // 2) get a java.util.Date from the calendar instance.
        //    this date will represent the current instant, or "now".
        Date now = calendar.getTime();

        // 3) a java current time (now) instance
        Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        return new Question(-1, "Hello, endpoints!", currentTimestamp, 0);
    }

}
