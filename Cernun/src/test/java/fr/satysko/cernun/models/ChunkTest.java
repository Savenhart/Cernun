package fr.satysko.cernun.models;

import fr.satysko.cernun.utils.OpenSimplexNoise;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class ChunkTest {

    World w;
    OpenSimplexNoise oNoise;

    @Test
    public void generate() {
        Chunk c = new Chunk(w, 0, 0, oNoise);
        assertEquals(100, c.getCells().size());
    }

    @Before
    public void before(){
        w = new World("Test", 0);
        oNoise = new OpenSimplexNoise(0);
    }
}