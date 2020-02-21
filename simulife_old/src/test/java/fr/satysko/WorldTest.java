package fr.satysko;

import fr.satysko.dao.Constantes;
import fr.satysko.dao.GenericDAO;
import fr.satysko.models.Cell;
import fr.satysko.models.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WorldTest {

    World w;

    @Test
    public void GenCell(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(i + " " + j + " : ");
                w.genCell(i, j);
            }
        }
        assertEquals(25, w.getCells().size());
    }

    @Before
    public void preTest(){
        w = new World("Cernun", 0);
    }

    @After
    public void postTest(){}
}
