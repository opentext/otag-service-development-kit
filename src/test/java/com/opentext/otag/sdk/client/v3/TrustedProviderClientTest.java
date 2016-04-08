package com.opentext.otag.sdk.client.v3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opentext.otag.sdk.types.v3.ErrorResponseWrapper;
import com.opentext.otag.sdk.types.v3.TrustedProvider;
import com.opentext.otag.sdk.types.v3.TrustedProviders;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v3.api.error.InternalServerErrorException;
import com.opentext.otag.service.context.AWConfig;
import com.opentext.otag.service.context.AWConfigFactory;
import com.opentext.otag.tomcat.TomcatLifecycleListener;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static com.opentext.otag.sdk.client.v3.AbstractOtagServiceClient.APP_KEY_HEADER;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrustedProviderClientTest {

    public static final String TEST_APP_KEY = "testAppKey";
    public static final String TEST_APP_NAME = "testAppName";
    public static final String OTAG_URL = "https://otag.com:8080";

    /**
     * Provides an example of how you can mock out an SDK client
     * to unit test a service independently .
     */
    @Test
    public void testGetAllProviders() throws APIException, JsonProcessingException {
        TomcatLifecycleListener.setStarted();

        List<TrustedProvider> providers = Arrays.asList(
                new TrustedProvider("name1", "key1", "contactInfo1"),
                new TrustedProvider("name2", "key2", "contactInfo2"));
        // create test string to return as fake response body from HTTP service
        String asJson = new ObjectMapper().writeValueAsString(providers);

        // manually indicate that the container has started to allow the
        // service client to initialise
        TomcatLifecycleListener.setStarted();

        // mock out the web context interaction
        AWConfigFactory configFactoryMock = mockLoadServiceConfig(TEST_APP_KEY, TEST_APP_NAME, OTAG_URL);

        // ... and the HTTP communications
        Response responseMock = mock(Response.class);
        when(responseMock.getStatus()).thenReturn(200);
        when(responseMock.readEntity(String.class)).thenReturn(asJson);
        Client restClientMock = mockHttpInteraction(responseMock, TEST_APP_KEY);
        when(responseMock.readEntity(new GenericType<List<TrustedProvider>>() {})).thenReturn(providers);

        // test the parts you are interested in for your class that uses the client ...

        TrustedProviderClient client = new TrustedProviderClient(restClientMock, configFactoryMock);
        TrustedProviders allProviders = client.getAllProviders();

        assertThat(allProviders.getTrustedProviders().isEmpty()).isFalse();
    }

    // test exception resolution
    @Test
    public void testGetAllProviders_throw500() throws APIException, JsonProcessingException {
        TomcatLifecycleListener.setStarted();

        String errorKey = "ErrorKey";
        String errorMessage = "Error Message";
        ErrorResponseWrapper errorResponseWrapper = new ErrorResponseWrapper(errorKey, errorMessage, 500);
        // create test string to return as fake response body from HTTP service
        String asJson = new ObjectMapper().writeValueAsString(errorResponseWrapper);

        // manually indicate that the container has started to allow the
        // service client to initialise
        TomcatLifecycleListener.setStarted();

        // mock out the web context interaction
        AWConfigFactory configFactoryMock = mockLoadServiceConfig(TEST_APP_KEY, TEST_APP_NAME, OTAG_URL);

        // ... and the HTTP communications
        Response responseMock = mock(Response.class);
        when(responseMock.getStatus()).thenReturn(500);
        when(responseMock.readEntity(String.class)).thenReturn(asJson);
        Client restClientMock = mockHttpInteraction(responseMock, TEST_APP_KEY);

        // test the parts you are interested in for your class that uses the client ...

        TrustedProviderClient client = new TrustedProviderClient(restClientMock, configFactoryMock);
        try {
            client.getAllProviders();
            fail("We expected a 500 Exception");
        } catch (APIException e) {
            if (!(e instanceof InternalServerErrorException))
                fail("We expected a 500 Exception");
            InternalServerErrorException internalServerErr = (InternalServerErrorException) e;
            assertThat(internalServerErr.getCallInfo().getErrorCode()).isEqualTo(errorKey);
            assertThat(internalServerErr.getCallInfo().getErrorMessage()).isEqualTo(errorMessage);
        }
    }

    private AWConfigFactory mockLoadServiceConfig(String testAppKey, String testAppName, String otagUrl) {
        AWConfig configMock = mock(AWConfig.class);
        when(configMock.getAppKey()).thenReturn(testAppKey);
        when(configMock.getAppName()).thenReturn(testAppName);
        when(configMock.getGatewayUrl()).thenReturn(otagUrl);

        AWConfigFactory configFactoryMock = mock(AWConfigFactory.class);
        when(configFactoryMock.getConfig()).thenReturn(configMock);
        return configFactoryMock;
    }

    private Client mockHttpInteraction(Response responseMock, String appKey) {
        Client restClientMock = mock(Client.class);
        WebTarget webTargetMock = mock(WebTarget.class);
        Invocation.Builder builderMock = mock(Invocation.Builder.class);

        when(restClientMock.target(isA(String.class))).thenReturn(webTargetMock);
        when(webTargetMock.path(isA(String.class))).thenReturn(webTargetMock);
        when(webTargetMock.request()).thenReturn(builderMock);
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add(APP_KEY_HEADER, appKey);
        when(builderMock.headers(eq(headers))).thenReturn(builderMock);
        when(builderMock.get()).thenReturn(responseMock);
        return restClientMock;
    }

}