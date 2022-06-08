package com.census.support.scheduler;


import com.census.support.message.Message;
import com.census.support.message.MessageRepository;
import com.census.support.message.MessageService;
import com.census.support.ticket.Ticket;
import com.census.support.ticket.TicketRepository;
import com.census.support.util.SetAttributeUpdate;
import com.census.support.util.SmsServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URLEncoder;
import java.util.List;

@Configuration
@EnableScheduling
@EnableAsync
@Slf4j
public class SmsScheduler {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageService messageService;
    @Autowired
    TicketRepository ticketRepository;


    @Async
    @Scheduled(fixedDelay = 600000)
    public void scheduleSmsTaskAsync() {
        List<Message> messages = messageRepository.findByStatus("PENDING");
        if (messages.size() > 0) {
            log.info("Scheduler found " + messages.size() + " messages to send");
            for(Message message : messages){
                Ticket entity = ticketRepository.findByCode(message.getTicketCode());
                try {
                    // Send request to the API servers over HTTPS
                    SmsServiceUtil.doTrustToCertificates();
                    String mobile = entity.getDeviceUserPhone();
                    String project = "BBS";
                    String creator = "System";
                    String checkUrl ="http://paipaipos.waltonbd.com:8080/census/#/shared/status?code="+entity.getCode()+" ";
                    String sms_body =message.getBody();
                    String charset = "UTF-8";
                    System.out.println("Sending SMS to: " + mobile);
                    String myUrl = String.format("https://wapi.waltonbd.com:444/SMSAPI/public/sms_api?project=%s&creator=%s&mobile=%s&sms_body=%s",
                            URLEncoder.encode(project, charset),
                            URLEncoder.encode(creator, charset),
                            URLEncoder.encode(mobile, charset),
                            URLEncoder.encode(sms_body, charset));


                    System.out.println(myUrl);
                    String results = SmsServiceUtil.doHttpUrlConnectionAction(myUrl);
                    System.out.println(results);
                    JSONObject jsonObj = new JSONObject(results);
                    String status = jsonObj.getString("message");
                    Message updateMessage = messageRepository.findById(message.getId()).get();
                    if (status.equals("SUCCESS")) {
                        updateMessage.setStatus("SENT");
                      //  SetAttributeUpdate.setSysAttributeForCreateUpdate(updateMessage,"Update");
                        messageRepository.saveAndFlush(updateMessage);
                        System.out.println("SMS sent successfully");
                    }else {
                        updateMessage.setStatus("FAILED");
                      //  SetAttributeUpdate.setSysAttributeForCreateUpdate(updateMessage,"Update");
                        messageRepository.saveAndFlush(updateMessage);
                        // System.out.println("SMS sent failed"+ jsonObj.getString("message"));
                    }
                } catch (Exception e) {
                    System.out.println("Error - " + e.getMessage());
                }
            }
        }
    }
}