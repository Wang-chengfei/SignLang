package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.sun.org.apache.xpath.internal.operations.Equals;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PlanWord implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer planId;

    private Integer wordId;

    private Boolean completed;

    private LocalDate studyTime;

    @Override
    public boolean equals(Object obj) {
        //判断是否为同一对象
        if (this == obj)
            return true;
        //判断是否为空
        if (obj == null)
            return false;
        //判断是否属于同一个类
        if (getClass() != obj.getClass())
            return false;
        //如果类型相同，比较内容
        PlanWord planWord = (PlanWord) obj;
        return id == planWord.getId();
    }

}
