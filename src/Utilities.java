package CS145MP2.src;

import java.util.*;

public class Utilities{

	public static int randInt(int min, int max){
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

}