package fr.satysko;

import fr.satysko.dao.Constantes;
import fr.satysko.dao.GenericDAO;
import fr.satysko.models.World;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.HashSet;

public class WorldDaoTest {

    GenericDAO<World> worldDAO;
    World w;

    @Test
    public void newEntry(){
        worldDAO.saveOrUpdate(w);
        assertNotEquals(0, w.getId());
    }

    @Test
    public void cascadeCellTest(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                w.genCell(i, j);
            }
        }
        worldDAO.saveOrUpdate(w);
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

    @BeforeClass
    public static void preClass(){
        GenericDAO<World> preTestDAO = new GenericDAO<>(Constantes.PERSISTENCE_UNIT_NAME_TU, World.class);

        int tAvant = preTestDAO.findAll().size();

        preTestDAO.saveAll(new HashSet<World>(Arrays.asList(
                new World("Test1", 1),
                new World("Test2", 2),
                new World("Test3", 3),
                new World("Test4", 4),
                new World("Test5", 5),
                new World("Test6", 6)
        )));

        assertEquals(6, preTestDAO.findAll().size() - tAvant);

        preTestDAO.close();
    }

}
