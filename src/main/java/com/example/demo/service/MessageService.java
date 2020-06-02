package com.example.demo.service;

import com.cbim.common.vo.MessageEntity;
import com.example.demo.entity.first.MessageBean;
import com.example.demo.repository.first.MessageRepository;
import com.example.demo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;


    public MessageEntity<?> findAll(MessageBean bean) {
        List<MessageBean> all = messageRepository.findAll();
        return ResultUtil.success(all);
    }

    public MessageEntity<?> add(MessageBean bean) {
        messageRepository.save(bean);
        return ResultUtil.success("新增成功");
    }
}
