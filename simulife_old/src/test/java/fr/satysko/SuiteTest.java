package fr.satysko;

import fr.satysko.models.Biome;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({WorldTest.class, WorldDaoTest.class, BiomeTest.class})
public class SuiteTest {

}
