package com.framework.module.product.domain;

import com.kratos.entity.BaseEntity;
import com.kratos.module.attachment.domain.Attachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

/**
 * 最小库存单位
 * @author tang he
 * @since 1.0.0
 */
@Entity
@ApiModel("最小库存单位")
public class Sku extends BaseEntity {
    @ApiModelProperty(value = "商品规格")
    @ManyToMany
    @JoinTable(name="sku_product_standard",joinColumns={@JoinColumn(name="sku_id")},inverseJoinColumns={@JoinColumn(name="product_standard_id")})
    private List<ProductStandard> productStandards;
    @ApiModelProperty(value = "商品规格条目")
    @ManyToMany
    @JoinTable(name="sku_product_standard_items",joinColumns={@JoinColumn(name="sku_id")},inverseJoinColumns={@JoinColumn(name="product_standard_item_id")})
    private List<ProductStandardItem> productStandardItems;
    @ApiModelProperty(value = "商品")
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    @ApiModelProperty(value = "单价")
    @Column(length = 11, precision = 2)
    private Double price;
    @ApiModelProperty(value = "库存数量")
    private Long stockCount;
    @ApiModelProperty(value = "封面图片")
    @ManyToOne
    private Attachment coverImage;

    public List<ProductStandard> getProductStandards() {
        return productStandards;
    }

    public void setProductStandards(List<ProductStandard> productStandards) {
        this.productStandards = productStandards;
    }

    public List<ProductStandardItem> getProductStandardItems() {
        return productStandardItems;
    }

    public void setProductStandardItems(List<ProductStandardItem> productStandardItems) {
        this.productStandardItems = productStandardItems;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getStockCount() {
        return stockCount;
    }

    public void setStockCount(Long stockCount) {
        this.stockCount = stockCount;
    }

    public Attachment getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Attachment coverImage) {
        this.coverImage = coverImage;
    }
}
