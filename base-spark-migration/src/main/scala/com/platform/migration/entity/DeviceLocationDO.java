package com.platform.migration.entity;

/**
 * 设备地理位置信息
 */
public class DeviceLocationDO {
    /**
     * 创建时间，13位时间戳
     */
    private Long createTime;

    /**
     * 支付宝用户表中的userId
     */
    private String globalUserId;
    /**
     * 身份证
     */
    private String cardId;
    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * "00"-注册 "01"-额度申请 "0201"-支付成功 "0202"-提现成功 "03"-变更手机号 "04"-登录
     */
    private String stage;
    /**
     * 合同号
     */
    private String relId;

    /***
     * 经度
     */
    private String longitude;
    /***
     * 纬度
     */
    private String latitude;
    /***
     * 省份
     */
    private String province;
    /***
     * 城市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /***
     * 街道名
     */
    private String street;
    /***
     * 街道门牌号
     */
    private String streetNumber;
    /**
     * 经度
     */
    private String lbsLongitude;
    /**
     * 纬度
     */
    private String lbsLatitude;
    /**
     * 地址
     */
    private String address;

    /**
     * 城市code
     */
    private String adCode;
    /**
     * 海拔
     */
    private String altitude;
    /**
     * 定位精度
     */
    private String radius;
    /**
     * 是否处于室内定位
     */
    private String indoorLocMode;
    /**
     * 广义上的地理位置
     */
    private String locationDescribe;
    /**
     * 语义化列表
     */
    private String poiList;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getGlobalUserId() {
        return globalUserId;
    }

    public void setGlobalUserId(String globalUserId) {
        this.globalUserId = globalUserId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getLbsLongitude() {
        return lbsLongitude;
    }

    public void setLbsLongitude(String lbsLongitude) {
        this.lbsLongitude = lbsLongitude;
    }

    public String getLbsLatitude() {
        return lbsLatitude;
    }

    public void setLbsLatitude(String lbsLatitude) {
        this.lbsLatitude = lbsLatitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getIndoorLocMode() {
        return indoorLocMode;
    }

    public void setIndoorLocMode(String indoorLocMode) {
        this.indoorLocMode = indoorLocMode;
    }

    public String getLocationDescribe() {
        return locationDescribe;
    }

    public void setLocationDescribe(String locationDescribe) {
        this.locationDescribe = locationDescribe;
    }

    public String getPoiList() {
        return poiList;
    }

    public void setPoiList(String poiList) {
        this.poiList = poiList;
    }
}
