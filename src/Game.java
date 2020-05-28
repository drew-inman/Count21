import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

	private int maxSize = Integer.MAX_VALUE;
	private int currentGameTotal = 0;
	private final int targetNumber = 21;
	private int cpuDifficulty = 6; // How far ahead the CPU looks ahead. >=5 CPU always wins
	private int currentPlayer = 1; // 1 = Human, -1 = CPU. Changing this here does nothing.
	Scanner scanner;
	
	public Game() {
		this.runGame();
	}
	
	private void runGame() {
		
		int userInput = 0;
		System.out.println("Welcome to 21.");
		System.out.println("Players take turns counting upwards in a series of 1-4 until 21."); 
		System.out.println("If 21 is in your series of numbers, you lose!");
		
		while(currentGameTotal < targetNumber) {
			
			scanner = new Scanner(System.in);
			System.out.print("player turn: ");
			
			try {
				userInput = scanner.nextInt();
			} 
			catch (InputMismatchException e) {
				System.out.println("invalid character. try again.");
			}
			
			if(userInput > 0 && userInput <= 4) {
				currentGameTotal += userInput;
				System.out.println("total: " + currentGameTotal);
				if(!checkWinner(currentGameTotal, currentPlayer)) {
					currentPlayer *= -1;
					Node cpuChoice = new Node(cpuDifficulty, currentPlayer, currentGameTotal);
					int bestChoice = maxSize;
					int bestValue = (currentPlayer*-1) * maxSize;
					
					for(int i = 0; i < cpuChoice.children.size(); i++) {
						Node child = cpuChoice.children.get(i);
						int tempValue = minimax(child, cpuDifficulty, currentPlayer*-1);
						if(Math.abs(currentPlayer * maxSize - tempValue) <= Math.abs(currentPlayer * maxSize - bestValue)) {
							bestValue = tempValue;
							bestChoice = i;
						}
						
					}
					bestChoice += 1;
					System.out.printf("CPU turn: %d\n", bestChoice);
//					System.out.printf("Based on score: %d\n", bestValue); // This line here for debugging.
					currentGameTotal += bestChoice;
					checkWinner(currentGameTotal, currentPlayer);
					System.out.println("total: " + currentGameTotal);
					currentPlayer *= -1;
				}
			} else {
				System.out.println("invalid number. try again.");
			}
		}
		scanner.close();
	}
	
	private int minimax(Node node, int depth, int playerNumber) {
		if(depth == 0 || Math.abs(node.value) == maxSize) {
			return node.value;
		}
		
		int bestChoice = maxSize * playerNumber*-1;
		
		for(int i = 0; i < node.children.size(); i++) {
			Node child = node.children.get(i);
			int someValue = minimax(child, depth-1, playerNumber*-1);
			if(Math.abs(maxSize * playerNumber - someValue) < Math.abs(maxSize * playerNumber - bestChoice)) {
				bestChoice = someValue;
			}
		}
		return bestChoice;
	}
	
	private boolean checkWinner(int gameTotal, int playerNumber) {
		if(gameTotal >= targetNumber) {
			if(playerNumber == 1) {
				System.out.println("CPU wins!");
				return true;
				
			} else {
				System.out.println("You win!");
				return false;
			}
		}
		return false;
	}
	class Node {
			
		int depth, playerNumber, total, value;
		ArrayList<Node> children;

		public Node(int depth, int playerNumber, int total) {
			this(depth, playerNumber, total, 0);
		}
		
		public Node(int depth, int playerNumber, int total, int value) {
			this.depth = depth;
			this.playerNumber = playerNumber;
			this.total = total;
			this.value = value;
			children = new ArrayList<Node>();
			createChildren();
		}

		private void createChildren() {
			if(depth >= 0) {
				for(int i = 1; i <=4; i++) {
					int tempValue = total + i;
					children.add(new Node(depth-1, playerNumber*-1, tempValue, getValue(tempValue)));
				}
			}
			
		}

		private int getValue(int tempValue) {
			if(tempValue == 20) {
				return maxSize * playerNumber;
			}
			if (tempValue >= 21) {
				return maxSize * playerNumber*-1;
			}
			return 0;
		}
	}
	public static void main(String[] args) {
		Game game = new Game();
	}
}
