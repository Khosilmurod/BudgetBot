package com.ecma.testdb.entity;

import com.ecma.testdb.entity.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role{

    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
