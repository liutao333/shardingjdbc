package com.example.demo.ctrl;

import com.cbim.common.permission.SessionInfo;
import com.cbim.common.restctrl.FrontBaseRestCtrl;
import com.cbim.common.vo.MessageEntity;
import com.example.demo.entity.first.MessageBean;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test/message")
public class MessageCtrl extends FrontBaseRestCtrl {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/getList",produces = "application/json",method = RequestMethod.POST )
    public MessageEntity<?> getSiteMessageList(@RequestBody MessageBean bean, HttpServletRequest req){
        return messageService.findAll(bean);
    }

    @RequestMapping(value = "/add",produces = "application/json",method = RequestMethod.POST )
    public MessageEntity<?> add(@RequestBody MessageBean bean, HttpServletRequest req){
        return messageService.add(bean);
    }

}
