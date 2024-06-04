package simulation;

import entities.building.Building;
import entities.building.Floor;
import entities.building.elevator.Elevator;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class SimulationRunner {

    boolean exit = false;

    Building building = null;
    int selection;



    public static void main(String[] args) {

        SimulationRunner simulation = new SimulationRunner();
        simulation.run();
    }

    private void run() {
        while(!exit) {
            printAMainMenu();
        }
    }

    private void printAMainMenu() {
        selection = -1;
        printInfo("Select an option!");
        printInfo("1 - create a building");
        if(building != null) {
            printInfo("2 - create agent");
            printInfo("3 - remove agents");
            printInfo("4 - print building schema");
            printInfo("5 - run simulation");
        }
        printInfo("0 - exit");
        printInfo("");
        try {
            selection = Integer.parseInt(printQuestion(">"));
        } catch (NumberFormatException e) {
            printInfo("Invalid selection!");
            selection = -1;
        }

        switch (selection) {

            case 0: {
                exit = true;
                break;
            }

            case 1: {
                printABuildingMenu();
                break;
            }

            case 2: {
                if(building != null) {
                    printAnAgentMenu();
                }
                break;
            }

            case 3: {
                if(building != null) {
                    printAgentRemoveDialog();
                }
                break;
            }

            case 4: {
                if(building != null) {
                    printElevatorSystem();
                }
                break;
            }

            case 5: {
                if(building != null) {
                    printSimulationMenu();
                }
                break;
            }

        }
    }

    private void printABuildingMenu() {
        try {
            int basementFloorsNumber = Integer.parseInt(printQuestion("Basement floors number: "));
            int floorsNumber = Integer.parseInt(printQuestion("Floors number: "));
            int elevatorsNumber = Integer.parseInt(printQuestion("Elevators number:  "));
            building = new Building(basementFloorsNumber * -1, floorsNumber, elevatorsNumber);
            printInfo("");
            printInfo("Building created!");
            printInfo("");
        } catch (Exception e) {
            printInfo("");
            printInfo("Wrong input!");
            printInfo("");
            selection = -1;
        }
    }

    private void printAnAgentMenu() {
        try {
            int startFloor = Integer.parseInt(printQuestion("Starting floor: "));
            int targetFloor = Integer.parseInt(printQuestion("Target floor: "));

            Floor start = Floor.getFloor(building.getFloors(), startFloor);
            Floor target = Floor.getFloor(building.getFloors(), targetFloor);
            building.addAgent(start, target);

            printInfo("");
            printInfo("Agent created!");
            printInfo("");
        } catch (Exception e) {
            printInfo("");
            printInfo("Wrong input!");
            printInfo("");
            selection = -1;
        }
    }

    private void printSimulationMenu() {
        try {
            int iterations = Integer.parseInt(printQuestion("Simulation iterations: "));
            int delay = Integer.parseInt(printQuestion("Simulation delay [ms]: "));

            printElevatorSystem();
            for(int i = 0; i < iterations; i++) {
                building.update();
                Thread.sleep(delay);
                printElevatorSystem();
            }
        } catch (Exception e) {
            printInfo("");
            e.printStackTrace();
            printInfo("Wrong input!");
            printInfo("");
            selection = -1;
        }
    }

    private static void printInfo(String string) {
        System.out.println(string);
    }

    private static String printQuestion(String string) {
        System.out.print(string + " ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void printElevatorSystem() {
        for(Floor floor: building.getFloors())
            System.out.printf(" %2s ", floor.getAgents().size());
        System.out.println();
        for(Elevator elevator: building.getElevatorSystem().getElevators())
            printElevatorShaft(elevator);
        System.out.println("\n");
    }

    private void printAgentRemoveDialog() {
        building.removeAllAgents();
        System.out.println("\nAll agents removed!\n");
    }

    public void printElevatorShaft(Elevator elevator) {
        for(Floor floor: building.getFloors()) {
            if(elevator.getFloor() == floor)
                printElevator(elevator);
            else
                printEmptyFloor();
        }
        for(Floor entry: elevator.getFloorQueue().getFloors())
            System.out.print(" " + entry.getLevel() + ", " + elevator.getFloorQueue().getDirection(entry) + "; ");
        System.out.println();
    }


    public void printElevator(Elevator elevator) {
        System.out.printf("[%2s]", elevator.getAgentCount());
    }

    public void printEmptyFloor() {
        System.out.print("----");
    }

}
