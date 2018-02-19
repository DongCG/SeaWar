package src;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;

public class Utils {
	// 图片获取工具
	public static Image getImage(String path) {
		URL url = null;
		Image img = null;
		try {
			url = ClassLoader.getSystemResource(path);
			img = ImageIO.read(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	// 音频获取工具
	public static AudioClip getAudio(String path) {
		URL url = ClassLoader.getSystemResource(path);
		AudioClip clip = Applet.newAudioClip(url);
		return clip;
	}

	// k随机数发生器
	public static int getRand(int k) {
		double random = Math.random();
		int result = (int) (random * k);
		return result;
	}
	
	public static void drawHeros(Graphics g,List<Hero> heros){
		g.setFont(new Font("宋体", Font.BOLD, 20));
		g.setColor(Color.CYAN);
		g.fillRect(150, 100, 470, 400);
		for(int i=0;i<Config.RANKNUM;i++){
			g.setColor(Color.BLACK);
			g.drawString("Top 5",380, 130);
			g.drawString((i+1)+"      "+heros.get(i).toString(),240, 165+70*i);
		}
	}
	
	public static List<Hero> checkRank(Hero hero){
		FileReader fr = null;
		BufferedReader br = null;
		try {
			List<Hero> heros = new ArrayList<Hero>();
			fr = new FileReader("hero.txt");
			br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				Hero h = new Hero(line);
				heros.add(h);
			}
			if(hero.score > heros.get(heros.size()-1).score){
				heros.add(hero);
				Collections.sort(heros);
				heros.remove(Config.RANKNUM);
				return heros;
			}else{
				return null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	//获取时间戳
	public static String getTimeStamp(){
		DateFormat df = new SimpleDateFormat("YY/MM/dd");
		return df.format(new Date());
	}
	


	// 添加新英雄并排序
	public static boolean updateHeros(List<Hero> heros) {
		boolean done = false;
		FileWriter fw = null;
		try {
			fw = new FileWriter("hero.txt");
			for(int i=0;i<Config.RANKNUM;i++){
				fw.write(heros.get(i).toString()+"\r\n");
			}
			done = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return done;
	}
}

/*

200    18/02/18
100    18/02/18
50    18/02/18
50    18/02/18
50    18/02/18


*/
