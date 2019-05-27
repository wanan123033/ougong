package com.ougong.shop.activity.checkin;

import android.util.Log;
import com.ougong.shop.Bean.CateryBean;
import com.ougong.shop.activity.CheckLinData;
import com.ougong.shop.httpmodule.HxBean;
import com.ougong.shop.httpmodule.HxSpaceBean;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckLinUtils {
    private static CheckLinUtils utils;
    CheckLinData data;
    private CheckLinUtils(){
        data = new CheckLinData();
    }
    public static synchronized CheckLinUtils getInstance(){
        if (utils == null){
            utils = new CheckLinUtils();
        }
        return utils;
    }

    /**
     * 设置拎包入住方案名称
     * @param name
     */
    public void setCheckLinName(String name){
        data.checkLinName = name;
    }

    /**
     * 设置拎包入住方案面积
     * @param area
     */
    public void setCheckArea(int area){
        data.area = area;
    }

    /**
     * 设置拎包入住户型
     * @param hxStr
     */
    public void setCheckHxData(String hxStr){
        data.hxStr = hxStr;
    }
    public boolean isNoFirst(){
        return data.hxData == null && data.area == 0 &&data.checkLinName == null;
    }

    /**
     * 设置拎包入住房间名称列表
     * @param hxNames
     */
    public void setCheckHxNames(List<HxSpaceBean> hxNames){
        data.hxNames = hxNames;
    }

    /**
     * 获取拎包入住房间名称列表
     * @return
     */
    public List<HxSpaceBean> getCheckHxNames(){
        return data.hxNames;
    }

    public String getCheckLinName(){
        return data.checkLinName;
    }

    /**
     * 清空配置单
     */
    public void clear(){
        data = null;
        utils = null;
    }

    /**
     * 将产品加入我的配置单中，
     * @param hx 房间信息
     * @param category  分类信息
     * @param itemData  产品信息
     */
    public void addProduct(HxSpaceBean hx, CheckLinData.CategoryBean category, CheckLinData.ProductBean itemData){
        Log.e("TAG","hhhhhhhhhhhhhh");
        for (int i = 0 ; i < data.rooms.size() ; i++){
            if (data.rooms.get(i).roomName.equals(hx.getName())){  //找到房间
                for(int j = 0 ; j < data.rooms.get(i).categoryBeans.size() ; j++){
                    CheckLinData.CategoryBean ca = data.rooms.get(i).categoryBeans.get(j);
                    if (ca.categoryName.equals(category.categoryName)){  //找到了分类
                        //TODO 添加产品
                        for (int m = 0 ; m < ca.productBeans.size() ; m++){
                            if (ca.productBeans.get(m).productId == itemData.productId && ca.productBeans.get(m).specId == itemData.specId){  // 包含该产品时数量+1
                                ca.productBeans.get(m).price += (ca.productBeans.get(m).price / ca.productBeans.get(m).productNum);
                                ca.productBeans.get(m).productNum++;
                                return;
                            }
                        }
                        if (itemData.productNum == 0){
                            itemData.productNum = 1;
                        }
                        ca.productBeans.add(itemData);
                        break;
                    }
                }
                break;
            }
        }
    }

    /**
     * 商品数量+1
     * @param specId  商品的规格ID
     * @param roomName  房间名称
     * @param categoryName  分类名称
     */
    public void productAdd(int specId,String roomName,String categoryName){
        for (int i = 0 ; i < data.rooms.size() ; i++){
            if (data.rooms.get(i).roomName.equals(roomName)){  //找到了房间
                List<CheckLinData.CategoryBean> categoryBeans = data.rooms.get(i).categoryBeans;
                for (int j = 0 ; j < categoryBeans.size() ; j++){
                    if (categoryBeans.get(j).categoryName.equals(categoryName)){   //找到了分类
                        List<CheckLinData.ProductBean> productBeans = categoryBeans.get(j).productBeans;
                        for (int m = 0 ; m < productBeans.size() ; m++){
                            if (productBeans.get(m).specId == specId){                    //找到了商品
                                productBeans.get(m).price += (productBeans.get(m).price / productBeans.get(m).productNum);
                                productBeans.get(m).productNum++;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 商品数量-1
     * @param specId  商品的规格ID
     * @param roomName 房间名称
     * @param categoryName 分类名称
     */
    public void productReduce(int specId,String roomName,String categoryName){
        for (int i = 0 ; i < data.rooms.size() ; i++){
            if (data.rooms.get(i).roomName.equals(roomName)){  //找到了房间
                List<CheckLinData.CategoryBean> categoryBeans = data.rooms.get(i).categoryBeans;
                for (int j = 0 ; j < categoryBeans.size() ; j++){
                    if (categoryBeans.get(j).categoryName.equals(categoryName)){   //找到了分类
                        List<CheckLinData.ProductBean> productBeans = categoryBeans.get(j).productBeans;
                        for (int m = 0 ; m < productBeans.size() ; m++){
                            if (productBeans.get(m).specId == specId){
                                //找到了商品
                                if (productBeans.get(m).productNum > 1) {
                                    productBeans.get(m).price -= (productBeans.get(m).price / productBeans.get(m).productNum);
                                    productBeans.get(m).productNum--;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 将产品从配置单中删除，参数：房间ID 分类ID 产品
     */
    public void deleteProduct(int roomId,int cateId,int specId){
        CheckLinData.ProductBean productBean = null;
        if (data == null || data.rooms == null || data.rooms.isEmpty()){

        }else{
            for (int i = 0 ; i < data.rooms.size() ; i++){
                CheckLinData.RoomData roomData = data.rooms.get(i);
                if (roomData.roomId == roomId){
                    for (CheckLinData.CategoryBean cate : roomData.categoryBeans){
                        if (cate.categoryId == cateId){
                            List<CheckLinData.ProductBean> productBeans = cate.productBeans;
                            for (CheckLinData.ProductBean pro : productBeans){
                                if (pro.specId == specId){
                                    productBean = pro;
                                    break;
                                }
                            }
                            if (productBean != null)
                                cate.productBeans.remove(productBean);
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * 获取配置单内产品详细信息
     */
    public JSONArray getMyProfile(){
        if (data == null || data.rooms == null || data.rooms.isEmpty()){
            return null;
        }else {
            JSONArray array = new JSONArray();
            try {
                for (int i = 0; i < data.rooms.size(); i++) {
                    JSONArray categoryArr = new JSONArray();
                    for (int j = 0; j < data.rooms.get(i).categoryBeans.size(); j++) {
                        CheckLinData.CategoryBean bean = data.rooms.get(i).categoryBeans.get(j);
                        JSONArray products = new JSONArray();
                        for (int m = 0; m < bean.productBeans.size(); m++) {
                            CheckLinData.ProductBean productBean = bean.productBeans.get(m);
                            JSONObject product = new JSONObject();
                            product.put("specId", productBean.specId);
                            product.put("count", productBean.productNum);
                            product.put("name",productBean.productName);
                            product.put("spec",productBean.spec);
                            product.put("color",productBean.color);
                            product.put("price",productBean.price);
                            product.put("headImage",productBean.headImage);
                            product.put("roomName",data.rooms.get(i).roomName);
                            product.put("categoryName",bean.categoryName);
                            product.put("categoryId",bean.categoryId);
                            product.put("roomId",data.rooms.get(i).roomId);
                            products.put(product);
                        }
                        if (products.length() > 0) {
                            JSONObject cateArr = new JSONObject();
                            cateArr.put("categaryName", bean.categoryName);
                            cateArr.put("products", products);
                            categoryArr.put(cateArr);
                        }
                    }
                    if (categoryArr.length() > 0) {
                        JSONObject room = new JSONObject();
                        room.put("roomName", data.rooms.get(i).roomName);
                        room.put("category", categoryArr);
                        array.put(room);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return array;
        }
    }

    /**
     * 创建一个新的配置单   没有任何产品信息
     */
    public void createNewChecklin(){

    }

    /**
     * 根据接口获取的产品的分类信息初始化配置单的原始信息  参数：产品的分类信息列表
     * @param child
     */
    public void initRoomCateAndProduct(CateryBean[] child){
        if (data.rooms == null){
            data.rooms = new ArrayList<>();
        }else {
            data.rooms.clear();
        }
        for (int i = 0 ; i < data.hxNames.size() ; i++){
            CheckLinData.RoomData roomData = new CheckLinData.RoomData();
            roomData.roomName = getCheckHxNames().get(i).getName();
            roomData.roomId = i;
            roomData.categoryBeans = new ArrayList<>();
            for (CateryBean cate : child){
                if (roomData.roomName.contains(cate.getName())){
                    for (int j = 0 ; j < cate.getChild().length ; j++){
                        CheckLinData.CategoryBean bean = new CheckLinData.CategoryBean();
                        CateryBean cateryBean = cate.getChild()[j];
                        bean.categoryName = cateryBean.getName();
                        bean.categoryId = cateryBean.getId();
                        bean.categoryParentId = roomData.roomId;
                        bean.productBeans = new ArrayList<>();
                        roomData.categoryBeans.add(bean);
                    }
                    break;
                }
            }
            data.rooms.add(roomData);
        }
    }

    /**
     * 根据房间列表初始化配置单
     * @param hxSpaceBeans
     */
    public void initRoomCateAndProduct(List<HxSpaceBean> hxSpaceBeans){
        if (data.rooms == null){
            data.rooms = new ArrayList<>();
        }

        for (int i = 0 ; i < hxSpaceBeans.size() ; i++) {
            boolean isContrant = false;
            for (CheckLinData.RoomData roomData : data.rooms) {
                if (roomData.roomName.equals(hxSpaceBeans.get(i).getName())) {
                    isContrant = true;
                    break;
                }
            }
            if (!isContrant){
                CheckLinData.RoomData roomData = new CheckLinData.RoomData();
                roomData.roomName = hxSpaceBeans.get(i).getName();
                roomData.roomId = data.rooms.size();
                roomData.categoryBeans = new ArrayList<>();
                data.rooms.add(roomData);
                data.hxNames.add(hxSpaceBeans.get(i));
                isContrant = false;
            }
        }
    }

    /**
     * 重新初始化房间的分类信息列表
     * @param data
     * @param roomId
     */
    public void resetCateGory(CateryBean[] data,int roomId,String roomName){
        for (CateryBean oneCate : data){
            for (CateryBean twoCate : oneCate.getChild()){
                for (CateryBean threeCate : twoCate.getChild()){
                    for (CheckLinData.RoomData roomData : this.data.rooms){
                        if (threeCate.getEnabless()){
                            CheckLinData.CategoryBean categoryBean = new CheckLinData.CategoryBean();
                            categoryBean.categoryParentId = roomId;
                            categoryBean.categoryName = threeCate.getName();
                            categoryBean.categoryId = threeCate.getId();
                            if (!roomData.categoryBeans.contains(categoryBean)){  //没有该分类
                                categoryBean.productBeans = new ArrayList<>();
                                roomData.categoryBeans.add(categoryBean);
                                roomData.roomId = roomId;
                                roomData.roomName = roomName;
                            }
                        }else {
                            CheckLinData.CategoryBean categoryBean = new CheckLinData.CategoryBean();
                            categoryBean.categoryParentId = roomId;
                            categoryBean.categoryName = threeCate.getName();
                            categoryBean.categoryId = threeCate.getId();
                            if (roomData.categoryBeans.contains(categoryBean)){
                                roomData.categoryBeans.remove(categoryBean);
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * 获取该房间的分类信息
     */
    public List<CheckLinData.CategoryBean> getCateByRoom(String roomName){
        for (int i = 0 ; i < data.rooms.size() ; i++){
            if (data.rooms.get(i).roomName.equals(roomName)){
                return data.rooms.get(i).categoryBeans;
            }
        }
        return null;
    }

    /**
     * 将方案组成RequestBody 提交给后台
     * @return
     */
    public RequestBody getRequestBody(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("area",data.area);
            jsonObject.put("data",getCheckLinJson());
            jsonObject.put("houseTypeName",data.hxStr);
            jsonObject.put("name",data.checkLinName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
    }



    private JSONArray getCheckLinJson(){
        if (data != null) {
            JSONArray array = new JSONArray();
            for (int i = 0; i < data.rooms.size(); i++) {
                JSONObject room = new JSONObject();
                try {
                    room.put("roomName", data.rooms.get(i).roomName);
                    JSONArray products = new JSONArray();
                    for (int j = 0; j < data.rooms.get(i).categoryBeans.size(); j++) {
                        CheckLinData.CategoryBean bean = data.rooms.get(i).categoryBeans.get(j);
                        for (int m = 0; m < bean.productBeans.size(); m++) {
                            CheckLinData.ProductBean productBean = bean.productBeans.get(m);
                            JSONObject product = new JSONObject();
                            product.put("specId", productBean.specId);
                            product.put("count", productBean.productNum);
                            products.put(product);
                        }
                    }
                    if (products.length() != 0) {
                        room.put("products", products);
                        array.put(room);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return array;
        }else {
            return null;
        }
    }

    public void setCheckHx(HxBean hxData) {
        data.hxData = hxData;
    }
    public HxBean getCheckHx(){
        return data.hxData;
    }
}
