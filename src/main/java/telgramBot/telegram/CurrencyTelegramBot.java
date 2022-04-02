package telgramBot.telegram;

import lombok.SneakyThrows;
import notification.finiteStateMachine.StateMachine;
import notification.finiteStateMachine.StateMachineListener;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telgramBot.telegram.command.StartCommand;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    private Map<String, StateMachine> stateMachines;
    private ScheduledExecutorService scheduledExecutorService;
    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    public CurrencyTelegramBot() {

        register(new StartCommand());
        stateMachines = new ConcurrentHashMap<>();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }




    @SneakyThrows
    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasMessage()) {
            String chatId = Long.toString(update.getMessage().getChatId());

            if (!stateMachines.containsKey(chatId)){
                StateMachine fsm = new StateMachine();
                fsm.addListener(new MessageListener(chatId));
                stateMachines.put(chatId, fsm);
            }
            String message = update.getMessage().getText();
            stateMachines.get(chatId).handle(message);

        }
    }

    @Override
    public String getBotToken() {
        return BotConstants.BOT_TOKEN;
    }

    private class MessageListener implements StateMachineListener{
        private String chatId;

        public MessageListener(String chatId){
            this.chatId = chatId;
        }

        @Override
        public void onSwitchedToWaitForMessage() {
            sendText("Send text of notification");

            }

        @Override
        public void onSwitchedToWaitForTime() {
            sendText("Ok, in how many sec remind?");
        }

        @Override
        public void onMessageAndTimeReceived(String message, int time) {
            sendText("Notification created. Sec to notification: " + time);

            scheduledExecutorService.schedule(
                    ()-> sendText(message),
                    time,
                    TimeUnit.SECONDS
            );

        }

        private void sendText(String text){

            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(text);
            sendMessage.setChatId(chatId);

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }
}
