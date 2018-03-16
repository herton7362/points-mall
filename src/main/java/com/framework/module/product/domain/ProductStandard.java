package com.framework.module.product.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.OrderBy;

import javax.persistence.*;
import java.util.List;

/**
 * 商品规格
 * @author tang he
 * @since 1.0.0
 */
@Entity
@ApiModel("商品规格")
public class ProductStandard extends BaseEntity {
    @ApiModelProperty(value = "名称")
    @Column(length = 50)
    private String name;
    @ApiModelProperty(value = "备注")
    @Column(length = 100)
    private String remark;
    @ApiModelProperty(value = "规格条目")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productStandard")
    @Where(clause="logically_deleted=0")
    @OrderBy(clause="sort_number asc")
    private List<ProductStandardItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ProductStandardItem> getItems() {
        return items;
    }

    public void setItems(List<ProductStandardItem> items) {
        this.items = items;
    }
}
