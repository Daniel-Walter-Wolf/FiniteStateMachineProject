import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import model.FSM;

/**
*@author Matthew Goff, Aaron Cass
*/
@RunWith(JUnit4.class)
public class FSMTests
{
    private FSM theStateMachine;
    private FSM anotherStateMachine;

    @Before
    public void setUp()
    {
        theStateMachine = new FSM();
        anotherStateMachine = new FSM();
    }

    @After
    public void tearDown()
    {
        theStateMachine=null;
    }

    @Test
    public void save_load_test()
    {
        theStateMachine.addState("one");
        theStateMachine.addState("two");
        theStateMachine.addState("three");

        theStateMachine.save("aSavedStateMachine");
        anotherStateMachine.load("aSavedStateMachine");

        assertEquals("Saving and loading a state machine does not alter it", theStateMachine.toString(), anotherStateMachine.toString());


    }
}
