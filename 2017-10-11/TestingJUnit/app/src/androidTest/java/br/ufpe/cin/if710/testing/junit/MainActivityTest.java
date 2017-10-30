package br.ufpe.cin.if710.testing.junit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private ListView listView = null;

    @Rule
    public final ActivityTestRule<MainActivity> main = new ActivityTestRule(MainActivity.class, true);

    @Before
    public void inicializaListView() {
        listView = (ListView) main.getActivity().findViewById(R.id.lista);
    }

    @Test
    public void contaElementos() throws Exception {
        assertEquals(8, listView.getAdapter().getCount());
    }
}
