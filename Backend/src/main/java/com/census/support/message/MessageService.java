package com.census.support.message;

import com.census.support.ticket.Ticket;
import com.census.support.util.SetAttributeUpdate;
import com.census.support.util.SmsServiceUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;


@Service
public class MessageService {


    @Autowired
    private MessageRepository messageRepository;

    public void sendTicketCreatedMessage(Ticket entity,String message) {
        try {
            // Send request to the API servers over HTTPS
            SmsServiceUtil.doTrustToCertificates();
            String mobile = entity.getDeviceUserPhone();
            String sms_body =message+" " + entity.getCode() + "  .Check ticket status at http://census.com/shared/status?code=" + entity.getCode();
            String charset = "UTF-8";
            String myUrl = String.format("https://wapi.waltonbd.com:444/SMSAPI/public/sms_api?project=BBS&creator=System&mobile="+mobile+"&sms_body=%s",
                    URLEncoder.encode(sms_body, charset));
            String results = SmsServiceUtil.doHttpUrlConnectionAction(myUrl);
            JSONObject jsonObj = new JSONObject(results);
            String status = jsonObj.getString("message");
            if (status.equals("SUCCESS")) {
                Message entityInst = new Message();
                entityInst.setBody(sms_body);
                entityInst.setTicket(entity);
                entityInst.setStatus("SENT");
                entityInst.setReceiver(entity.getDeviceUserPhone());
                SetAttributeUpdate.setSysAttributeForCreateUpdate(entityInst,"Create");
                messageRepository.save(entityInst);
                System.out.println("SMS sent successfully");
            }else {
                Message entityInst = new Message();
                entityInst.setBody(sms_body);
                entityInst.setTicket(entity);
                entityInst.setStatus("FAILED");
                entityInst.setReceiver(entity.getDeviceUserPhone());
                SetAttributeUpdate.setSysAttributeForCreateUpdate(entityInst,"Create");
                messageRepository.save(entityInst);
                System.out.println("SMS sent failed");
            }
        } catch (Exception e) {
            System.out.println("Error - " + e);
        }

    }
}
