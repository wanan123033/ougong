package com.ougong.shop.activity;

import com.ougong.shop.httpmodule.HxBean;
import com.ougong.shop.httpmodule.HxSpaceBean;
import okhttp3.RequestBody;

import java.util.List;
import java.util.Objects;

public class CheckLinData {
    public String checkLinName;   //方案名称
    public String hxStr;  //户型
    public int area;      //面积

    public List<RoomData> rooms;  //房间列表

    public List<HxSpaceBean> hxNames;  //房间名称列表
    public HxBean hxData;

    @Override
    public String toString() {
        return "CheckLinData{" +
                "checkLinName='" + checkLinName + '\'' +
                ", hxStr='" + hxStr + '\'' +
                ", area=" + area +
                ", rooms=" + rooms +
                ", hxNames=" + hxNames +
                '}';
    }

    public static class RoomData{
        public String roomName;  //房间名    例如  客厅  餐厅  卧室等
        public int roomId;       //房间ID
        public int roomParentId;  //房间所属分类的ID  例如：软装家居  个性生活  品牌家具等
        public List<CategoryBean> categoryBeans;  //房间下的分类信息列表   例如  沙发  床等

        @Override
        public Object clone(){
            RoomData data = new RoomData();
            data.roomId = roomId;
            data.roomName = roomName+"";
            data.roomParentId = roomParentId;
            return data;
        }

        @Override
        public String toString() {
            return "RoomData{" +
                    "roomName='" + roomName + '\'' +
                    ", roomId=" + roomId +
                    ", roomParentId=" + roomParentId +
                    ", categoryBeans=" + categoryBeans +
                    '}';
        }
    }

    public static class CategoryBean{
        public String categoryName;  //分类名称
        public int categoryId;       //分类ID
        public int categoryParentId; //分类所属房间ID
        public List<ProductBean> productBeans;  //分类下的产品信息列表
        public boolean enable;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CategoryBean bean = (CategoryBean) o;
            return categoryId == bean.categoryId &&
                    categoryParentId == bean.categoryParentId &&
                    categoryName.equals(bean.categoryName);

        }

        @Override
        public int hashCode() {
            return categoryName.hashCode() + categoryId + categoryParentId;
        }
        public CategoryBean cloneCategory(){
            CategoryBean bean = new CategoryBean();
            bean.categoryName = categoryName+"";
            bean.categoryId = categoryId;
            bean.categoryParentId = categoryParentId;
            return bean;
        }

        @Override
        public String toString() {
            return "CategoryBean{" +
                    "categoryName='" + categoryName + '\'' +
                    ", categoryId=" + categoryId +
                    ", categoryParentId=" + categoryParentId +
                    ", productBeans=" + productBeans +
                    '}';
        }
    }

    public static class ProductBean{
        public String productName;
        public String spec;
        public int specId;
        public int productId;
        public String color;
        public double price;
        public double totalPrice;
        public int productNum;
        public String headImage;
        @Override
        public String toString() {
            return "ProductBean{" +
                    "productName='" + productName + '\'' +
                    ", spec='" + spec + '\'' +
                    ", specId=" + specId +
                    ", productId=" + productId +
                    ", color='" + color + '\'' +
                    ", price=" + price +
                    ", productNum=" + productNum +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductBean that = (ProductBean) o;
            return specId == that.specId &&
                    productId == that.productId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(specId, productId);
        }
    }

}
