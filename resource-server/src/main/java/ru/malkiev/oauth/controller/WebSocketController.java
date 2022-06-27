package ru.malkiev.oauth.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import ru.malkiev.oauth.model.Message;
import ru.malkiev.oauth.model.OutputMessage;

@Controller
public class WebSocketController {

  @MessageMapping("/chat")
  @SendToUser(destinations = "/queue/user")
  public OutputMessage testMessage(Principal principal, Message message) {
    String time = new SimpleDateFormat("HH:mm").format(new Date());
    System.out.println(principal);
    return new OutputMessage(message.getFrom(), message.getText(), time);
  }

}
