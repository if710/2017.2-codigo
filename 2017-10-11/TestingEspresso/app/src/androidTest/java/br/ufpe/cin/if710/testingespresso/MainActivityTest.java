package br.ufpe.cin.if710.testingespresso;

/*

  1. Encontrar (pegar referencia de) widgets que deseja examinar ou manipular
  2. Executar ações nos widgets quando necessário (e possível)
  3. Checar widgets para observar se estão em determinado estado

 */

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.runner.RunWith;

import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import junit.framework.Assert;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onData;

//Normalmente pegamos o objeto usando um método como onView, importado estaticamente abaixo
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public final ActivityTestRule<MainActivity> main = new ActivityTestRule(MainActivity.class, true);

    @Test
    public void contaPessoas() {
        onView(withId(R.id.lista)).check(new AdapterCountAssertion(13));
    }

    @Test
    public void eventosDeTecla() {
        onView(withId(R.id.lista)).perform(
                pressKey(KeyEvent.KEYCODE_DPAD_DOWN),
                pressKey(KeyEvent.KEYCODE_DPAD_DOWN),
                pressKey(KeyEvent.KEYCODE_DPAD_DOWN),
                pressKey(KeyEvent.KEYCODE_DPAD_DOWN)
        )
        .check(new ListSelectionAssertion(3));
    }

    @Test
    public void ultimoElemento() {
        onData(anything())
                .inAdapterView(withId(R.id.lista))
                .atPosition(12)
                .check(matches(withText("Gustavo")));
    }

    @Test
    public void clicarElemento() {
        onData(anything())
                .inAdapterView(withId(R.id.lista))
                .atPosition(10)
                .perform(click());

        onView(withId(R.id.pessoa_Nome)).check(matches(withText("Alexandre")));
        onView(withId(R.id.pessoa_Login)).check(matches(withText("acm")));
    }

    static class AdapterCountAssertion implements ViewAssertion {
        private final int count;

        AdapterCountAssertion(int count) {
            this.count=count;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            Assert.assertTrue(view instanceof AdapterView);
            Assert.assertEquals(count, ((AdapterView)view).getAdapter().getCount());
        }
    }

    static class ListSelectionAssertion implements ViewAssertion {
        private final int position;

        ListSelectionAssertion(int position) {
            this.position=position;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            Assert.assertTrue(view instanceof ListView);
            Assert.assertEquals(position, ((ListView)view).getSelectedItemPosition());
        }
    }


}
