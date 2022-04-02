package notification.finiteStateMachine;

import java.util.Scanner;

class StateMachineTests {
    public static void main(String[] args) {

        StateMachine fsm = new StateMachine();


        Scanner scanner = new Scanner(System.in);
        while (true) {
            String text = scanner.nextLine();
            fsm.handle(text);
        }
    }
}