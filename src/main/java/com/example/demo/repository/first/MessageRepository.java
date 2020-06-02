package com.example.demo.repository.first;

import com.example.demo.entity.first.MessageBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageBean,Integer> {
}
