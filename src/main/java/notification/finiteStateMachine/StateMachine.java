package notification.finiteStateMachine;

import java.util.ArrayList;
import java.util.List;

public class StateMachine {
    private State state = State.idle;
    private String message;
    private int time;
    private List<StateMachineListener> listeners = new ArrayList<>();

    public void addListener(StateMachineListener listener){
        listeners.add(listener);
    }
    public void handle (String text){

        if (text.equals("Create notification")){
            onCreateNotificationPressed();
            return;
        }
        onTextReceived(text);
        try {
            int number = Integer.parseInt(text);
            onNumberReceived(number);
        }catch (Exception exception){
        }

    }

    private void onCreateNotificationPressed(){
      System.out.println("onCreateNotificationPressed");

      switchState(State.waitForMessage);

        for(StateMachineListener listener : listeners){
            listener.onSwitchedToWaitForMessage();
        }
    }

    private void onTextReceived(String text){
        System.out.println("onTextReceived");
        if (state == State.waitForMessage) {
            message = text;
            switchState(State.waiteForNumber);

            for(StateMachineListener listener : listeners){
                listener.onSwitchedToWaitForTime();
            }
        }
    }

    private void onNumberReceived(int number) {
        System.out.println("onNumberReceived");
        if (state == State.waiteForNumber) {
            this.time = number;
            for(StateMachineListener listener: listeners){
                listener.onMessageAndTimeReceived(message,time);
            }
            //create notification
        }
    }

    private void switchState(State newState){
        System.out.println("State is changed from " + state + " to - "+ newState);
        this.state = newState;

    }
}

