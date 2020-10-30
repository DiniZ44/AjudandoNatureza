package view;

import javax.swing.JFrame;

public class Tela extends JFrame{
	
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	
	public void initFrame() {
		frame = new JFrame("Projeto MPOO v0.33");
		frame.add(this);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	}

}