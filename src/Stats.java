package CS145MP2.src;

import java.awt.*;
import java.awt.image.*;

public class Stats extends GameObject {

	final String assetsPath = "CS145MP2/assets/img/";

	boolean isVisible = false;

	String name;
	int[] x = new int[6];
	int y;
	int stat, hth, tth, th, h, t, o;

	BufferedImage hunThousands, tenThousands, thousands, hundreds, tens, ones;
	BufferedImage[] numbers = new BufferedImage[10];

	public Stats(String name) {
		this.name = name;
		int ctr = 0;
		for (int i = 210; i<=285; i+=15) {
			x[ctr++] = i;
		}

		if (name.equals("health")) y = 407;
		else if (name.equals("money")) y = 445;
		else if (name.equals("defenders")) y = 485;
		else if (name.equals("warriors")) y = 525;
		else y = 564;

		for (int i = 0; i<10; i++) {
			numbers[i] = MarioWindow.getImage(assetsPath + "misc/num-" + i + ".png");
		}

		initialize();
		
	}

	public void initialize() {
		stat = 0;
		o = 0;
		t = 0;
		h = 0;
		th = 0;
		tth = 0;
		hth = 0;
		hunThousands = null;
		tenThousands = null;
		thousands = null;
		hundreds = null;
		tens = null;
		ones = numbers[0];
	}
	
	public void paint(Graphics2D g) {

		if (hunThousands != null && isVisible) g.drawImage(hunThousands,x[0],y,null);
		if (tenThousands != null && isVisible) g.drawImage(tenThousands,x[1],y,null);
		if (thousands != null && isVisible) g.drawImage(thousands,x[2],y,null);
		if (hundreds != null && isVisible) g.drawImage(hundreds,x[3],y,null);
		if (tens != null && isVisible) g.drawImage(tens,x[4],y,null);
		if (ones != null && isVisible) g.drawImage(ones,x[5],y,null);
		
	}
	
	public void run() {
		while(true) {

			hth = stat / 100000;
			tth = (stat % 100000) / 10000;
			th = (stat%10000) / 1000;
			h = (stat%1000) / 100;
			t = (stat%100) / 10;
			o = stat%10;

			ones = numbers[o];
			if (hth == 0 && tth == 0 && th == 0 && h == 0 && t == 0) tens = null;
			else tens = numbers[t];
			if (hth == 0 && tth == 0 && th == 0 && h == 0) hundreds = null;
			else hundreds = numbers[h];
			if (hth == 0 && tth == 0 && th == 0) thousands = null;
			else thousands = numbers[th];
			if (hth == 0 && tth == 0) tenThousands = null;
			else tenThousands = numbers[tth];
			if (hth == 0) hunThousands = null;
			else hunThousands = numbers[hth];

			MarioWindow.delay(100);

		}
	}

	public void set(int num) {
		stat = num;
	}

	public void add(int num) {
		stat+=num;
	}

	public void subtract(int num) {
		stat-=num;
	}

	public void setVisible(boolean vis) {
		isVisible = vis;
	}

}