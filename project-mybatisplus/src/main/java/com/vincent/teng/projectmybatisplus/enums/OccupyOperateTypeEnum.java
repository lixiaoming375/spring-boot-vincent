package com.vincent.teng.projectmybatisplus.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/4/1619:43
 */
public enum OccupyOperateTypeEnum implements IEnum {


    ORDER_RECEIVE(1,"待支付"),
    ORDER_CANCEL(2,"取消订单"),
    PAY_DONE(3,"支付成功");


    private Integer value;
    private String desc;

    OccupyOperateTypeEnum(Integer value, String desc){
        this.desc=desc;
        this.value=value;
    }

    @Override
    public Integer getValue(){
        return value;
    }

    @JsonValue
    public String getDesc(){
        return this.desc;
    }

    private static Map<Integer, OccupyOperateTypeEnum> enumsMap=new HashMap<>();
    static {
        for (OccupyOperateTypeEnum enumObj : OccupyOperateTypeEnum.values()){
            enumsMap.put(enumObj.value,enumObj);
        }
    }

    public static OccupyOperateTypeEnum getEnumByValue(Integer value){
        if(value !=null){
            OccupyOperateTypeEnum enumObj=enumsMap.get(value);
            if(enumObj !=null){
                return enumObj;
            }
        }
        return null;
    }
}
