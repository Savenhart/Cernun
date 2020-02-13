package fr.satysko;

import fr.satysko.dao.Constantes;
import fr.satysko.dao.GenericDAO;
import fr.satysko.models.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WorldTest {

    GenericDAO<World> worldDAO;
    World w;

    @Test
    public void GenCell(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                w.genCell(i, j);
            }
        }
        assertEquals(25, w.getCells().size());
    }

    @Before
    public void preTest(){
        worldDAO = new GenericDAO<>(Constantes.PERSISTENCE_UNIT_NAME_TU, World.class);
        w = new World("Babar", 0);
    }

    @After
    public void postTest(){
        worldDAO.close();
    }
}
