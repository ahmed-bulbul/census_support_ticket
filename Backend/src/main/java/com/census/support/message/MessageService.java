package com.census.support.message;

import com.census.support.helper.response.BaseResponse;
import com.census.support.ticket.Ticket;
import com.census.support.ticket.TicketRepository;
import com.census.support.util.SetAttributeUpdate;
import com.census.support.util.SmsServiceUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MessageService {


    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TicketRepository ticketRepository;


    public void sendTicketCreatedMessage(Ticket entity,String message) {
        try {
            // Send request to the API servers over HTTPS
            SmsServiceUtil.doTrustToCertificates();
            String mobile = entity.getDeviceUserPhone();
            String project = "BBS";
            String creator = "System";
            String sms_body =message + " Ticket ID: " + entity.getCode() + " ";
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
            if (status.equals("SUCCESS")) {
                Message entityInst = new Message();
                entityInst.setBody(sms_body);
                entityInst.setTicket(entity);
                entityInst.setTicketCode(entity.getCode());
                entityInst.setStatus("SENT");
                entityInst.setReceiver(entity.getDeviceUserPhone());
                SetAttributeUpdate.setSysAttributeForCreateUpdate(entityInst,"Create");
                messageRepository.save(entityInst);
                System.out.println("SMS sent successfully");
            }else {
                Message entityInst = new Message();
                entityInst.setBody(sms_body);
                entityInst.setTicket(entity);
                entityInst.setTicketCode(entity.getCode());
                entityInst.setStatus("FAILED");
                entityInst.setReceiver(entity.getDeviceUserPhone());
                SetAttributeUpdate.setSysAttributeForCreateUpdate(entityInst,"Create");
                messageRepository.save(entityInst);
                System.out.println("SMS sent failed"+ jsonObj.getString("message"));
            }
        } catch (Exception e) {
            System.out.println("Error - " + e);
        }

    }


    public ResponseEntity<?> getByTicketId(Long id) {
        try {

            Optional<Ticket> ticket = ticketRepository.findById(id);

            if (ticket.isPresent()) {
                List<MessageDTO> dtoList = messageRepository.findByTicket(ticket).stream().map(MessageDTO::new)
                        .collect(Collectors.toList());
                if (dtoList.size() > 0) {
                    return new ResponseEntity<>(new BaseResponse(true, "Message found successfully", 200, dtoList.toArray()), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new BaseResponse(false, "Message not found", 404), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new BaseResponse(false, "Ticket not found", 404), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, "Something went wrong: " + e.getMessage(), 500), HttpStatus.OK);
        }
    }
}
