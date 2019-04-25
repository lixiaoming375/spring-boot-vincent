package com.vincent.teng.projectmybatisplus.controller;


import com.vincent.teng.projectmybatisplus.enums.OccupyOperateTypeEnum;
import com.vincent.teng.projectmybatisplus.service.ProductOccupyDetailService;
import com.vincent.teng.projectmybatisplus.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tengxiao
 * @since 2019-04-24
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping(value = "/doUnPay")
    public void doOccupy(){
        Long productId= Long.valueOf(100);
        String orderCode="orderCode-"+UUID.randomUUID().toString();
        productService.productOccupyOperate(OccupyOperateTypeEnum.ORDER_RECEIVE.getValue(),productId,orderCode);
    };

    @PostMapping(value = "/doCannel")
    public void doRelease(){
        Long productId= Long.valueOf(100);
        String orderCode="orderCode-80a00075-537b-449c-ae72-f5efcc67d1ea";
        productService.productOccupyOperate(OccupyOperateTypeEnum.ORDER_CANCEL.getValue(),productId,orderCode);
    };

    /**
     * 扣减
     */
    @PostMapping(value = "/doPayDone")
    public void doDeduct(){
        Long productId= Long.valueOf(100);
        String orderCode="orderCode-001";
        productService.productOccupyOperate(OccupyOperateTypeEnum.PAY_DONE.getValue(),productId,orderCode);
    };
}

