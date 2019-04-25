package com.vincent.teng.projectmybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.vincent.teng.projectmybatisplus.enums.OccupyReleaseTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author tengxiao
 * @since 2019-04-24
 */
@Data
public class ProductOccupyDetail extends Model<ProductOccupyDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 交易订单号
     */
    private String orderCode;

    /**
     * 占用数量
     */
    private Integer occupyNum;

    private Long productId;

    /**
     * 占用时间
     */
    private LocalDateTime occuptyTime;

    /**
     * 释放时间
     */
    private LocalDateTime releaseTime;

    private OccupyReleaseTypeEnum releaseReason;

    /**
     * 是否有效 默认1
     */
    private Integer valid;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }



    public static final class ProductOccupyDetailBuilder {
        private Long id;
        private String orderCode;
        private Integer occupyNum;
        private Long productId;
        private LocalDateTime occuptyTime;
        private LocalDateTime releaseTime;
        private OccupyReleaseTypeEnum releaseReason;
        private Integer valid;

        private ProductOccupyDetailBuilder() {
        }

        public static ProductOccupyDetailBuilder aProductOccupyDetail() {
            return new ProductOccupyDetailBuilder();
        }

        public ProductOccupyDetailBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProductOccupyDetailBuilder withOrderCode(String orderCode) {
            this.orderCode = orderCode;
            return this;
        }

        public ProductOccupyDetailBuilder withOccupyNum(Integer occupyNum) {
            this.occupyNum = occupyNum;
            return this;
        }

        public ProductOccupyDetailBuilder withProductId(Long productId) {
            this.productId = productId;
            return this;
        }

        public ProductOccupyDetailBuilder withOccuptyTime(LocalDateTime occuptyTime) {
            this.occuptyTime = occuptyTime;
            return this;
        }

        public ProductOccupyDetailBuilder withReleaseTime(LocalDateTime releaseTime) {
            this.releaseTime = releaseTime;
            return this;
        }

        public ProductOccupyDetailBuilder withReleaseReason(OccupyReleaseTypeEnum releaseReason) {
            this.releaseReason = releaseReason;
            return this;
        }

        public ProductOccupyDetailBuilder withValid(Integer valid) {
            this.valid = valid;
            return this;
        }

        public ProductOccupyDetail build() {
            ProductOccupyDetail productOccupyDetail = new ProductOccupyDetail();
            productOccupyDetail.setId(id);
            productOccupyDetail.setOrderCode(orderCode);
            productOccupyDetail.setOccupyNum(occupyNum);
            productOccupyDetail.setProductId(productId);
            productOccupyDetail.setOccuptyTime(occuptyTime);
            productOccupyDetail.setReleaseTime(releaseTime);
            productOccupyDetail.setReleaseReason(releaseReason);
            productOccupyDetail.setValid(valid);
            return productOccupyDetail;
        }
    }
}
