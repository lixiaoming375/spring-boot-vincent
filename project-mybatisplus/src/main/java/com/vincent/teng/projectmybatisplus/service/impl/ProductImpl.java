package com.vincent.teng.projectmybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.vincent.teng.projectmybatisplus.entity.Product;
import com.vincent.teng.projectmybatisplus.entity.ProductOccupyDetail;
import com.vincent.teng.projectmybatisplus.enums.OccupyOperateTypeEnum;
import com.vincent.teng.projectmybatisplus.enums.OccupyReleaseTypeEnum;
import com.vincent.teng.projectmybatisplus.mapper.ProductMapper;
import com.vincent.teng.projectmybatisplus.service.ProductOccupyDetailService;
import com.vincent.teng.projectmybatisplus.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vincent.teng.projectmybatisplus.config.CacheService;
import com.vincent.teng.projectmybatisplus.utils.MyConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tengxiao
 * @since 2019-04-24
 */
@Service
public class ProductImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductImpl.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductOccupyDetailService productOccupyDetailService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private TransactionTemplate transactionTemplate;


    @Override
    public  void productOccupyOperate(Integer operateType, Long productId, String orderCode){
        String lockKey =productId.toString();
        String requestId = UUID.randomUUID().toString();
        if (cacheService.tryLock(lockKey, requestId, MyConst.LOCK_KEY_EXPIRE_TIME, MyConst.GET_LOCK_KEY_WAIT_TIME)) {
            transactionTemplate.execute(new TransactionCallback() {
                @Override
                public Object doInTransaction(TransactionStatus transactionStatus) {
                    if(operateType== OccupyOperateTypeEnum.ORDER_RECEIVE.getValue()){
                        NumOperateForUnPayOrder(productId,orderCode);//占用
                    }else if(operateType==OccupyOperateTypeEnum.ORDER_CANCEL.getValue()){
                        NumOperateForCannelOrder(productId,orderCode);//释放
                    }else if(operateType==OccupyOperateTypeEnum.PAY_DONE.getValue()){
                        NumOperateForPayDone(productId,orderCode);//扣减
                    }
                    return null;
                }
            });
            cacheService.releaseLock(lockKey, requestId);
        }
      };


    //占用
    public  void NumOperateForUnPayOrder(Long productId,String orderCode){
//        Product product=productService.getById(productId);
//        product.setCurrentProductNum(product.getCurrentProductNum()-1);//当前商品数量-1
//        productService.updateById(product);
        ProductOccupyDetail  productOccupyDetail=ProductOccupyDetail.ProductOccupyDetailBuilder.aProductOccupyDetail()
                .withProductId(productId)
                .withOrderCode(orderCode)//通过ordercode唯一标记一条占用明细，释放时ordercode查到该记录
                .withOccupyNum(1)
                .withOccuptyTime(LocalDateTime.now())
                .withValid(1)
                .build();
        productOccupyDetailService.save(productOccupyDetail);
    }

    //释放
    public void NumOperateForCannelOrder(Long productId,String orderCode){
        Product product=productService.getById(productId);
        product.setCurrentProductNum(product.getCurrentProductNum()+1);//当前商品数量+1
        productService.updateById(product);

        ProductOccupyDetail  productOccupyDetail=ProductOccupyDetail.ProductOccupyDetailBuilder.aProductOccupyDetail()
                .withReleaseTime(LocalDateTime.now())
                .withReleaseReason(OccupyReleaseTypeEnum.ORDER_CANCEL)
                .withValid(0)
                .build();
        UpdateWrapper qw=new UpdateWrapper();//通过ordercode唯一标记一条占用明细，释放时ordercode查到该记录
        qw.eq("order_code",orderCode);
        productOccupyDetailService.update(productOccupyDetail,qw);
    }

    //扣减
    public void NumOperateForPayDone(Long productId,String orderCode){
        Product product=productService.getById(productId);
        product.setCurrentProductNum(product.getCurrentSoldNum()+1);//当前已售数量+1
        productService.updateById(product);

        ProductOccupyDetail  productOccupyDetail=ProductOccupyDetail.ProductOccupyDetailBuilder.aProductOccupyDetail()
                .withValid(0)
                .withReleaseTime(LocalDateTime.now())
                .withReleaseReason(OccupyReleaseTypeEnum.PAY_DONE)
                .build();
        UpdateWrapper qw=new UpdateWrapper();
        qw.eq("order_code",orderCode);
        productOccupyDetailService.update(productOccupyDetail,qw);
    }


}
