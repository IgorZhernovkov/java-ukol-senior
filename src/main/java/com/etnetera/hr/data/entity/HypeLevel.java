package com.etnetera.hr.data.entity;


import com.etnetera.hr.data.dto.WithId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Entity for a Hype level
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HypeLevel implements WithId<Long> {
    /** Record's ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** Score of hype level */
    @Column(nullable = false)
    private Integer score;

    /** Name of hype level */
    @Column(nullable = false, length = 50)
    private String name;

    public HypeLevel(Integer score, String name) {
        this.score = score;
        this.name = name;
        id = null;
    }
}
