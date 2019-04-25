package com.vincent.teng.projectmybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author tengxiao
 * @since 2019-04-24
 */
@Data
public class TestLock extends Model<TestLock> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer agent;

    private String name;

    private LocalDateTime createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TestLock{" +
        "id=" + id +
        ", agent=" + agent +
        ", name=" + name +
        ", createTime=" + createTime +
        "}";
    }
}
