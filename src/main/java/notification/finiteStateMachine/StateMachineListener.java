package notification.finiteStateMachine;

public interface StateMachineListener {
    void onSwitchedToWaitForMessage();
    void onSwitchedToWaitForTime();
    void onMessageAndTimeReceived ( String message, int time);

}
