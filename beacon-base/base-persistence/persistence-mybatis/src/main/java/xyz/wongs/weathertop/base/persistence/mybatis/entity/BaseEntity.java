package xyz.wongs.weathertop.base.persistence.mybatis.entity;

import com.alibaba.fastjson.annotation.JSONField;
import xyz.wongs.weathertop.base.persistence.mybatis.config.Global;
import xyz.wongs.weathertop.base.persistence.mybatis.stas.Cons;

import java.io.Serializable;

/**
 * @ClassName 获取数据库类型
 * @Description 
 * @author WCNGS@QQ.COM
 * @Github <a>https://github.com/rothschil</a>
 * @date 2019/12/23 15:16
 * @Version 1.0.0
*/
public abstract class BaseEntity<ID extends Serializable> extends IdEntity<ID> {

    @JSONField
    private String dtype;

    public String getDtype() {
        return Global.getConfig(Cons.DB_TYPE);
    }


}
