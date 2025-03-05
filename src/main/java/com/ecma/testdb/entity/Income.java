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
public class Income extends Defaults {
    @ManyToOne
    private Users users;
    private Double sum;
    private String forWhat;
    private String description;
    private Boolean finished;

    public Income(Users users, Double sum, String forWhat, String description) {
        this.users = users;
        this.sum = sum;
        this.forWhat = forWhat;
        this.description = description;
    }
}
