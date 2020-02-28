package fr.satysko;

import fr.satysko.cernun.daos.GenericDAO;
import fr.satysko.cernun.models.World;
import fr.satysko.cernun.services.WorldServices;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class WorldServicesTest {

    private GenericDAO<World> verifDAO;
    private WorldServices services;

    @Test
    public void newEntry(){
        World world = new World("Azolu", 946738152);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                world.genCell(i, j);
            }
        }

        World w = services.create(world);
        assertNotNull(w);
        assertNotEquals(0, w.getId());
    }

    @Test
    public void getEntry(){
        World world = services.find(25);
        assertNotNull(world);
        assertEquals("Azolu", world.getName());
        assertEquals(946738152, world.getSeed());
    }

    @Test
    public void getEntries(){
    }

    @Test
    public void updateEntry(){
    }

    @Before
    public void preTest(){
//        services = new WorldServices(Constantes.PERSISTENCE_UNIT_NAME_TU);
//        verifDAO = new GenericDAO<>(Constantes.PERSISTENCE_UNIT_NAME_TU, World.class);
    }

    @BeforeClass
    public static void preClass(){
    }

}
