package controller;

public class Camara {
	public static int x, y;
	
	
	public static int clamp(int Atual, int Min, int Max){
		if(Atual < Min) {
			Atual = Min;
		}
		if(Atual >Max){
			Atual= Max;
			}
		return Atual;
	}

	public static int getX() {
		return x;
	}

	public static void setX(int x) {
		Camara.x = x;
	}

	public static int getY() {
		return y;
	}

	public static void setY(int y) {
		Camara.y = y;
	}
	
}
