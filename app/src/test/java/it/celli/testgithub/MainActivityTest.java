package it.celli.testgithub;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import it.celli.testgithub.data.Stargazer;
import it.celli.testgithub.presenter.MainPresenter;
import it.celli.testgithub.view.MainActivity;
import it.celli.testgithub.view.MainAdapter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private static final String TEST_OWNER = "owner";
    private static final String TEST_REPO = "repo";

    private MainActivity mainActivity;

    @Mock
    private MainAdapter mainAdapter;

    @Mock
    private MainPresenter mainPresenter;

    private EditText owner;
    private EditText repo;
    private Button search;
    private List<Stargazer> sampleStargazersList;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        owner = mainActivity.findViewById(R.id.owner);
        repo = mainActivity.findViewById(R.id.repo);
        search = mainActivity.findViewById(R.id.search);
    }

    @Test
    public void setOwner_buttonNotVisible() {
        owner.setText(TEST_OWNER);
        assertThat(search.getVisibility(), is(View.INVISIBLE));
    }

    @Test
    public void setRepo_buttonNotVisible() {
        repo.setText(TEST_REPO);
        assertThat(search.getVisibility(), is(View.INVISIBLE));
    }

    @Test
    public void setRepoAndOwner_buttonVisible() {
        owner.setText(TEST_OWNER);
        repo.setText(TEST_REPO);
        assertThat(search.getVisibility(), is(View.VISIBLE));
    }
}
