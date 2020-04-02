package fr.satysko.cernun_ai_proto;

import fr.satysko.cernun_ai_proto.Agents.Creature;
import org.ejml.data.DMatrix2x2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		double[][] board = new double[7][7];

		Creature crea = new Creature();
		crea.setCoordX(6);
		crea.setCoordY(6);

		board[0][0] = 1;
		board[1][0] = -1;
		board[5][2] = -1;
		board[crea.getCoordX()][crea.getCoordY()] = 2;


	}

}
