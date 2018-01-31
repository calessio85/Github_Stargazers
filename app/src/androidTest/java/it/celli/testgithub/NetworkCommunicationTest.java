package it.celli.testgithub;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import it.celli.testgithub.utils.Utils;
import it.celli.testgithub.view.MainActivity;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class NetworkCommunicationTest {

    private static final String TEST_OWNER = "octocat";

    private MockWebServer mockWebServer;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        Utils.BASE_URL = mockWebServer.url("/").toString();
    }

    @Test
    public void test_200ok() throws Exception {
        String fileName = "200_ok_response.json";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(getStringFromFile(InstrumentationRegistry.getContext(), fileName)));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        mActivityRule.getActivity().performSearch();

        Espresso.onView(TestUtils.withRecyclerView(R.id.stargazers_list)
            .atPositionOnView(0, R.id.username))
                .check(matches(withText(TEST_OWNER)));
    }

    @Test
    public void testUrlNotFound() throws Exception {
        mockWebServer.enqueue(new MockResponse()
        .setResponseCode(404));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        mActivityRule.getActivity().performSearch();

        Espresso.onView(withText(Matchers.startsWith("Errore durante"))).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile(Context context, String filePath) throws Exception {
        final InputStream stream = context.getResources().getAssets().open(filePath);

        String ret = convertStreamToString(stream);
        //Make sure you close all streams.
        stream.close();
        return ret;
    }

}
