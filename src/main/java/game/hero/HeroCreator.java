package game.hero;

public class HeroCreator {
	public static Hero createHero(int pickNumber){
		switch(pickNumber){
		case 0:
			return new Hero_Base();
		case 1:
			return new Hero_FA();
		default:
			System.out.println("HeroCreator: no Hero for Picknumber: " + pickNumber);
			return new Hero_Base();
		}
	}

}
