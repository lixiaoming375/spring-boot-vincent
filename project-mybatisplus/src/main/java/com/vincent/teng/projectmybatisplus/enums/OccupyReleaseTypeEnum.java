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
public enum OccupyReleaseTypeEnum implements IEnum {


    ORDER_CANCEL(1,"取消订单"),
    PAY_DONE(2,"支付成功");

    private Integer value;
    private String desc;

    OccupyReleaseTypeEnum(Integer value, String desc){
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

    private static Map<Integer, OccupyReleaseTypeEnum> enumsMap=new HashMap<>();
    static {
        for (OccupyReleaseTypeEnum enumObj : OccupyReleaseTypeEnum.values()){
            enumsMap.put(enumObj.value,enumObj);
        }
    }

    public static OccupyReleaseTypeEnum getEnumByValue(Integer value){
        if(value !=null){
            OccupyReleaseTypeEnum enumObj=enumsMap.get(value);
            if(enumObj !=null){
                return enumObj;
            }
        }
        return null;
    }
}
