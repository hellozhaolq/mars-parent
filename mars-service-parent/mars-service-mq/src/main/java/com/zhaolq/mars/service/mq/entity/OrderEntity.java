package com.zhaolq.mars.service.mq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhaolq.mars.common.valid.group.Edit;
import com.zhaolq.mars.common.valid.group.Remove;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 *
 *
 * @author zhaolq
 * @date 2021/3/10 9:29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MARS_MQ_ORDER")
@KeySequence("SEQ_MQ_ORDER_ID")
@ApiModel(value = "OrderEntity对象", description = "订单信息")
public class OrderEntity extends Model<OrderEntity> {

    @JsonProperty("id")
    @NotNull(groups = {Edit.class, Remove.class}, message = "id缺失")
    @ApiModelProperty(value = "编号")
    @TableId(value = "ID", type = IdType.INPUT)
    private String id;

    private String name;

    private String messageId;

}
