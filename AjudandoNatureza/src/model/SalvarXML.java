package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
public class SalvarXML {
	
	private List<Player> players;
	private List<Player2> players2;
	private XStream xStream;
	private File file;
	private File file2;
	
	public SalvarXML() {
		xStream = new XStream(new Dom4JDriver());
		xStream.alias("Player", Player.class);
		xStream.alias("Player", Player2.class);
		
		file = new File("res/player.xml");
		file2 = new File("res/player2.xml");
		players = new ArrayList<>();
		players2 =  new ArrayList<>();
	}
	
public void salvar(Player player) {
		
		players.add(player);

		try {
			if (!file.exists())
				file.createNewFile();
			else {
				file.delete();
				file.createNewFile();
			}
			
			OutputStream stream = new FileOutputStream(file);
			xStream.toXML(players, stream);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
public void salvar2(Player2 player2) {
	
	players2.add(player2);

	try {
		if (!file2.exists())
			file2.createNewFile();
		else {
			file2.delete();
			file2.createNewFile();
		}
		
		OutputStream stream = new FileOutputStream(file2);
		xStream.toXML(players, stream);
		
	} catch (IOException e) {
		e.printStackTrace();
	}

}
	
}
