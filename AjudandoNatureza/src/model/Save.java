package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Save {
	
	public static void saveGame(String[] valor1, int[] valor2, int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("HelpNature.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i =0; i< valor1.length ; i++) {
			String current = valor1[i];
			current+=":";
			char[] value =Integer.toString(valor2[i]).toCharArray();
			for(int n = 0; n< value.length; n++) {
				value[n]+=encode;
				current+= value[n];
			}
			try {
				write.write(current);
				if(i<valor1.length-1)
					write.newLine();
			} catch (Exception e) {
				
			}
			try {
				write.flush();
				write.close(); 
			} catch (Exception e) {
				
			}
		}
	}

}
