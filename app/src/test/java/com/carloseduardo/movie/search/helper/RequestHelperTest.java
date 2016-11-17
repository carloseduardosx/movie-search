package com.carloseduardo.movie.search.helper;

import com.carloseduardo.movie.search.data.source.constants.API;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(MockitoJUnitRunner.class)
public class RequestHelperTest {

    private String key = "api_key";
    private String value = "9028u0192jr10irnfm2itnfqjrgnq3piugn9";

    @Test
    public void shouldAppendAsFirstParameter() {

        String url = API.ENDPOINT;
        RequestHelper requestHelper = new RequestHelper();
        String urlWithParameter = requestHelper.addParameter(url, key, value);

        assertTrue(urlWithParameter.contains("?"));
    }

    @Test
    public void shouldAppendAsSecondParameter() {

        String url = API.ENDPOINT + "?first_parameter=2j12oije09";
        RequestHelper requestHelper = new RequestHelper();
        String urlWithParameter = requestHelper.addParameter(url, key, value);

        assertTrue(urlWithParameter.contains("&"));
    }

    @Test
    public void shouldAppendAsThirdParameter() {

        int count = 0;
        String url = API.ENDPOINT + "?first_parameter=2j12oije09&second_parameter=2903i19mklm";
        RequestHelper requestHelper = new RequestHelper();
        String urlWithParameter = requestHelper.addParameter(url, key, value);
        Matcher urlMatcher = Pattern.compile("&").matcher(urlWithParameter);

        while (urlMatcher.find()) {
            count++;
        }

        assertTrue(count == 2);
    }
}
