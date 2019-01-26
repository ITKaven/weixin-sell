package com.kaven.weixinsell.dataobject;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DynamicUpdate
@Data   // get、set、toString、equals 等方法
//@Getter  get 方法
//@Setter  set 方法
//@ToString toString 方法
public class ProductCategory {

    // 类目id
    @Id
    @GeneratedValue
    private Integer categoryId;

    // 类目名称
    private String categoryName;

    // 类目编号
    private Integer categoryType;

    public ProductCategory(String categoryName , Integer categoryType){
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    // Spring Data JPA 必须提供默认构造方法
    public ProductCategory(){}

//    // 类目创建时间
//    private Date createTime;
//
//    // 类目更新时间
//    private Date updateTime;
//
//    public Integer getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Integer categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    public String getCategoryName() {
//        return categoryName;
//    }
//
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }
//
//    public Integer getCategoryType() {
//        return categoryType;
//    }
//
//    public void setCategoryType(Integer categoryType) {
//        this.categoryType = categoryType;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    @Override
//    public String toString() {
//        return "ProductCategory{" +
//                "categoryId=" + categoryId +
//                ", categoryName='" + categoryName + '\'' +
//                ", categoryType=" + categoryType +
//                ", createTime=" + createTime +
//                ", updateTime=" + updateTime +
//                '}';
//    }
}
