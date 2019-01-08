package com.focus.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description： 角色实体
 * @Author：shadow
 * @Date：ceate in 15:58 2018/12/31
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {
    private static final long serialVersionUID = -8083468450100848165L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
