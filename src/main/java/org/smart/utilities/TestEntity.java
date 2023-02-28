package org.smart.utilities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "test_table")
public class TestEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "test")
    private String string;
}
