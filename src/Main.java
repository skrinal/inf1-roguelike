import util.MenuLogic;

void main() throws IOException, InterruptedException {
    Scanner input = new Scanner(System.in);
    boolean running = true;

    while (running) {
        MenuLogic.printGameMenu();

        if (!input.hasNextInt()) {
            IO.println("Invalid selection. Try again");
            input.next();
            continue;
        }

        int selection = input.nextInt();
        input.nextLine();

        switch (selection) {
            case 1 -> {
                MenuLogic.startGame(input);
                model.Character player = MenuLogic.startGame(input);
                player.displayStats();

            }
            case 2 -> {
                IO.println("Not implemented yet");
            }
            case 3 -> {
                IO.println("Thanks for playing!");
                running = false;
            }
            default -> {
                IO.println("Invalid selection. Try again");
            }
        }
    }
    input.close();
}

