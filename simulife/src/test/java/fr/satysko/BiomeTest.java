package fr.satysko;

import fr.satysko.models.Biome;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.runners.Parameterized.Parameters;
import static org.junit.runners.Parameterized.Parameter;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BiomeTest {

    @Parameters
    public static Collection<Object> data(){
        Object[][] obj = {
                {-0.5f, 0f, -0.75f, "Banquise"},
                {-0.5f, 0f, 0f, "Ocean"},
                {-0.5f, 0f, 0.75f, "Ocean tropical"},
                {-0.1f, 0f, -0.75f, "Plage glacée"},
                {-0.1f, 0f, 0f, "Plage"},
                {-0.1f, 0f, 0.75f, "Plage tropicale"},
                {-0.1f, 0.4f, -0.75f, "Marais glacé"},
                {-0.1f, 0.4f, 0f, "Marais"},
                {-0.1f, 0.4f, 0.75f, "Marais tropical"},
                {0f, 0.9f, -0.9f, "Inlandsis"},
                {0f, 0.9f, -0.65f, "Inlandsis"},
                {0f, 0.9f, 0f, "Forêt"},
                {0f, 0.9f, 0.65f, "Mangrove"},
                {0f, 0.9f, 0.9f, "Mangrove"},
                {0f, 0.65f, -0.9f, "Permafrost"},
                {0f, 0.65f, -0.65f, "Permafrost"},
                {0f, 0.65f, 0f, "Forêt"},
                {0f, 0.65f, 0.65f, "Forêt tropicale"},
                {0f, 0.65f, 0.9f, "Jungle"},
                {0f, 0f, -0.9f, "Toundra"},
                {0f, 0f, -0.65f, "Taiga"},
                {0f, 0f, 0f, "Plaine"},
                {0f, 0f, 0.65f, "Savane"},
                {0f, 0f, 0.9f, "Savane"},
                {0f, -0.65f, -0.9f, "Désert glacé"},
                {0f, -0.65f, -0.65f, "Steppe"},
                {0f, -0.65f, 0f, "Plaine"},
                {0f, -0.65f, 0.65f, "Pampa"},
                {0f, -0.65f, 0.9f, "Désert"},
                {0f, -0.9f, -0.9f, "Désert glacé"},
                {0f, -0.9f, -0.65f, "Désert glacé"},
                {0f, -0.9f, 0f, "Désert"},
                {0f, -0.9f, 0.65f, "Désert"},
                {0f, -0.9f, 0.9f, "Désert"},
                {0.6f, 0.9f, -0.9f, "Haute forêt enneigée"},
                {0.6f, 0.9f, -0.65f, "Haute forêt enneigée"},
                {0.6f, 0.9f, 0f, "Haute jungle"},
                {0.6f, 0.9f, 0.65f, "Haute jungle"},
                {0.6f, 0.9f, 0.9f, "Plateau volcanique"},
                {0.6f, 0.65f, -0.9f, "Haute forêt enneigée"},
                {0.6f, 0.65f, -0.65f, "Haute forêt enneigée"},
                {0.6f, 0.65f, 0f, "Haute forêt"},
                {0.6f, 0.65f, 0.65f, "Haute jungle"},
                {0.6f, 0.65f, 0.9f, "Plateau volcanique"},
                {0.6f, 0f, -0.9f, "Plateau enneigé"},
                {0.6f, 0f, -0.65f, "Plateau enneigé"},
                {0.6f, 0f, 0f, "Haut plateau"},
                {0.6f, 0f, 0.65f, "Haut plateau"},
                {0.6f, 0f, 0.9f, "Plateau volcanique"},
                {0.6f, -0.65f, -0.9f, "Plateau enneigé"},
                {0.6f, -0.65f, -0.65f, "Plateau enneigé"},
                {0.6f, -0.65f, 0f, "Plateau rocheux"},
                {0.6f, -0.65f, 0.65f, "Plateau rocheux"},
                {0.6f, -0.65f, 0.9f, "Plateau volcanique"},
                {0.6f, -0.9f, -0.9f, "Plateau rocheux enneigé"},
                {0.6f, -0.9f, -0.65f, "Plateau rocheux enneigé"},
                {0.6f, -0.9f, 0f, "Plateau rocheux"},
                {0.6f, -0.9f, 0.65f, "Plateau rocheux"},
                {0.6f, -0.9f, 0.9f, "Plateau volcanique"},
                {0.9f, 0.9f, -0.9f, "Montagne forêt enneigée"},
                {0.9f, 0.9f, -0.65f, "Montagne forêt enneigée"},
                {0.9f, 0.9f, 0f, "Montagne jungle"},
                {0.9f, 0.9f, 0.65f, "Montagne jungle"},
                {0.9f, 0.9f, 0.9f, "Volcan"},
                {0.9f, 0.65f, -0.9f, "Montagne forêt enneigée"},
                {0.9f, 0.65f, -0.65f, "Montagne forêt enneigée"},
                {0.9f, 0.65f, 0f, "Montagne forêt"},
                {0.9f, 0.65f, 0.65f, "Montagne jungle"},
                {0.9f, 0.65f, 0.9f, "Volcan"},
                {0.9f, 0f, -0.9f, "Montagne enneigée"},
                {0.9f, 0f, -0.65f, "Montagne enneigée"},
                {0.9f, 0f, 0f, "Montagne"},
                {0.9f, 0f, 0.65f, "Montagne"},
                {0.9f, 0f, 0.9f, "Volcan"},
                {0.9f, -0.65f, -0.9f, "Montagne enneigée"},
                {0.9f, -0.65f, -0.65f, "Montagne enneigée"},
                {0.9f, -0.65f, 0f, "Montagne rocheuse"},
                {0.9f, -0.65f, 0.65f, "Montagne rocheuse"},
                {0.9f, -0.65f, 0.9f, "Volcan"},
                {0.9f, -0.9f, -0.9f, "Montagne rocheuse enneigée"},
                {0.9f, -0.9f, -0.65f, "Montagne rocheuse enneigée"},
                {0.9f, -0.9f, 0f, "Montagne rocheuse"},
                {0.9f, -0.9f, 0.65f, "Montagne rocheuse"},
                {0.9f, -0.9f, 0.9f, "Volcan"}};
        return Arrays.asList(obj);
    }

    @Parameter(0)
    public float dataE;

    @Parameter(1)
    public float dataH;

    @Parameter(2)
    public float dataT;

    @Parameter(3)
    public String dataOut;

    @Test
    public void ConstructTest(){
        Biome b = new Biome(dataE, dataH, dataT);
        assertEquals(dataOut, b.getBiome());
    }
}