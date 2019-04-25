package com.vincent.teng.projectmybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

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
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 当前商品数量
     */
    private Integer currentProductNum;

    /**
     * 当前已售数量
     */
    private Integer currentSoldNum;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Product{" +
        "id=" + id +
        ", currentProductNum=" + currentProductNum +
        ", currentSoldNum=" + currentSoldNum +
        "}";
    }
}
