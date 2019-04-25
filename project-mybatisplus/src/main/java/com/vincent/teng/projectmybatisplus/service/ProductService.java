package com.vincent.teng.projectmybatisplus.service;

import com.vincent.teng.projectmybatisplus.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tengxiao
 * @since 2019-04-24
 */
public interface ProductService extends IService<Product> {


    void productOccupyOperate(Integer operateType, Long productId, String orderCode);
}
