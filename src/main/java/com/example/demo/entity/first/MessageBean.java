package com.example.demo.entity.first;


import com.cbim.common.util.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "message")
@Data
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class MessageBean implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "accept_id")
    private Integer acceptId;//

    @Column(name = "name")
    private String  name;//

    @Column(name = "password")
    private String password;//
}
