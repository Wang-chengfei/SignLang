package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wcf
 * @since 2022-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Word implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String type;

    private String name;

    private String description;

    private String imgUrl;


}
