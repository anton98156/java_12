package lastLesson;

import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class Controller {

    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        System.out.println("\nВведите команду для создания карты:");

        RobotMap map = null;
        while (true) {
            String command = sc.nextLine();
            if (command.startsWith("create-map")) {
                String[] split = command.split(" ");
                String[] arguments = Arrays.copyOfRange(split, 1, split.length); 
                try {
                    map = new RobotMap(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("\nПри создании карты возникло исключение: " + e.getMessage() + "." +
                            " Попробуйте еще раз");
                }
            } else {
                System.out.println("Команда не найдена. Попробуйте еще раз");
            }
        }

        System.out.println("\nИГРАЕМ...");
        
        int count = 0;
        while (count < 10) {
            System.out.println("\nВведите команду:");
            String command = sc.nextLine();
            if (command.startsWith("create-robot")) {
                RobotMap.Robot robot = null;
                String[] split = command.split(" ");
                String[] arguments = Arrays.copyOfRange(split, 1, split.length); 
                try {
                    robot = map.createRobot(new Point(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1])));
                    System.out.println(robot);
                } catch (PositionException e) {
                    System.out.println("\nВо время создания робота возникло исключение: " + e.getMessage() + "." +
                            " Попробуйте еще раз");
                }
            } else if (command.startsWith("move-robot")) {
                String[] split = command.split(" ");
                String[] arguments = Arrays.copyOfRange(split, 1, split.length); 

                UUID id = UUID.fromString(arguments[0]);

                try {
                    RobotMap.Robot robotById = map.getRobotById(id);
                    robotById.move();
                } catch (PositionException e) {
                    System.out.println("\nНе удалось переместить робота: " + e.getMessage());
                }
            } else if (command.startsWith("change-direction")) {
                String[] split = command.split(" ");
                String[] arguments = Arrays.copyOfRange(split, 1, split.length); 

                UUID id = UUID.fromString(arguments[0]);
                RobotMap.Direction direction = RobotMap.Direction.valueOf(arguments[1]);

                // RobotMap.Robot robotById = map.getRobotById(id);
                RobotMap.changeDirection(map, id, direction);
            } else {
                System.out.println("\nКоманда не найдена. Попробуйте еще раз");
            }
            System.out.println("\nКарта: ");
            System.out.println(RobotMap.robots.toString());
            Thread.sleep(1000);
            count++;
        }
        sc.close();
    }
}