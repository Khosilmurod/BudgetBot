package com.ecma.testdb.entity;

import com.ecma.testdb.entity.template.Defaults;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message extends Defaults {
    private String body;
    private String telegramId;
    private Integer messageId;
}
