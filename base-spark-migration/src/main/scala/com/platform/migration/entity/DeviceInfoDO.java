package com.platform.migration.entity;

/**
 * 设备信息
 */
public class DeviceInfoDO {
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

    /**
     * 系统平台 String Android,IOS
     */
    private String type;
    /**
     * 系统版本 String 5.0.1
     */
    private String osVersion;
    /**
     * 分辨率 String 中间用x表示 w x h
     */
    private String resolution;
    /**
     * 国家／语言 String
     */
    private String language;

    /**
     * 当前连接wifi热点ssid String TP-LINK_xx
     */
    private String ssid;
    /**
     * 当前连接wifi热点bssid String ec:26:ca:39:6e:66
     */
    private String bssid;
    /**
     * 设备名称 String Meizu
     */
    private String deviceName;
    /**
     * 手机型号 String MX4
     */
    private String moduleName;
    /**
     * 移动国家号码 String 由3位数字组成，唯一地识别移动用户所属的国家。我国为460
     */
    private String mcc;
    /**
     * 移动网络码 String 共2位，中国移动TD系统使用00，中国联通GSM系统使用01，中国移动GSM系统使用02，中国电信CDMA系统使用03
     */
    private String mnc;
    /**
     * 数据采集app版本 String 1.1 1.2
     */
    private String version;
    /**
     * 数据采集app发布渠道 String AppStore，应用宝
     */
    private String channel;

    /**
     * 设备码: 可以是imei，guid代表设备唯一码
     */
    private String deviceNum;

    /**
     * 品牌
     */
    private String manufacturer;
    /**
     * 手机厂商
     */
    private String product;
    /**
     * CPU架构信息
     */
    private String cpu;
    /**
     * 处理器核数
     */
    private String cpuInfo;
    /**
     * mac地址
     */
    private String wlanMac;
    /**
     * 蓝牙地址
     */
    private String btMac;
    /**
     * 当前抓取时间
     */
    private String time;
    /**
     * 网络类型
     */

    private String iActiveNetType;
    /**
     * 局域网Ip
     */
    private String ip;
    /**
     * 空闲磁盘空间大小
     */
    private String sdFreeSize;
    /**
     * 总磁盘空间
     */
    private String sdAllSize;
    /**
     * 移动终端类型
     */
    private String phoneType;
    /**
     * 获取手机状态信息
     */
    private String phoneStatus;
    /**
     * cpu序列号
     */
    private String cpuSerialNum;

    /**
     * 运营商
     */
    private String carrier;
    /**
     * 获取手机状态(0：无活动 1：响铃 2：待机)
     */
    private String phoneState;
    /**
     * 手机方位
     */
    private String phoneLocation;
    /**
     * 像素密度
     */
    private String density;
    /**
     * 国际移动用户识别码
     */
    private String imsi;

    /***
     * 包名
     */
    private String appId;

    /***
     * 供应商标示符
     */
    private String openUdId;

    /***
     * 供应商标示符
     */
    private String uuid;

    /***
     * 供应商标示符
     */
    private String idfv;

    /***
     * 设备标识
     */
    private String fcuuid;

    /***
     * 跨app的设备唯一标识
     */
    private String idfa;
    /**
     * 指纹TouchId
     */
    private String hasSupportTouchId;

    /***
     * 安卓串号
     */
    private String serialNo;

    /***
     * 安卓ID
     */
    private String androidId;

    /***
     * 设备朝向，衡、颠倒等方向
     */
    private String orientation;

    /***
     * 是否处于调试进程
     */
    private String hasDebug;

    /***
     * 网络传输速度
     */
    private String linkSpeed;
    /**
     * 外网Ip
     */
    private String realIp;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(String deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(String cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public String getWlanMac() {
        return wlanMac;
    }

    public void setWlanMac(String wlanMac) {
        this.wlanMac = wlanMac;
    }

    public String getBtMac() {
        return btMac;
    }

    public void setBtMac(String btMac) {
        this.btMac = btMac;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getiActiveNetType() {
        return iActiveNetType;
    }

    public void setiActiveNetType(String iActiveNetType) {
        this.iActiveNetType = iActiveNetType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSdFreeSize() {
        return sdFreeSize;
    }

    public void setSdFreeSize(String sdFreeSize) {
        this.sdFreeSize = sdFreeSize;
    }

    public String getSdAllSize() {
        return sdAllSize;
    }

    public void setSdAllSize(String sdAllSize) {
        this.sdAllSize = sdAllSize;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneStatus() {
        return phoneStatus;
    }

    public void setPhoneStatus(String phoneStatus) {
        this.phoneStatus = phoneStatus;
    }

    public String getCpuSerialNum() {
        return cpuSerialNum;
    }

    public void setCpuSerialNum(String cpuSerialNum) {
        this.cpuSerialNum = cpuSerialNum;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getPhoneState() {
        return phoneState;
    }

    public void setPhoneState(String phoneState) {
        this.phoneState = phoneState;
    }

    public String getPhoneLocation() {
        return phoneLocation;
    }

    public void setPhoneLocation(String phoneLocation) {
        this.phoneLocation = phoneLocation;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenUdId() {
        return openUdId;
    }

    public void setOpenUdId(String openUdId) {
        this.openUdId = openUdId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIdfv() {
        return idfv;
    }

    public void setIdfv(String idfv) {
        this.idfv = idfv;
    }

    public String getFcuuid() {
        return fcuuid;
    }

    public void setFcuuid(String fcuuid) {
        this.fcuuid = fcuuid;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getHasSupportTouchId() {
        return hasSupportTouchId;
    }

    public void setHasSupportTouchId(String hasSupportTouchId) {
        this.hasSupportTouchId = hasSupportTouchId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getHasDebug() {
        return hasDebug;
    }

    public void setHasDebug(String hasDebug) {
        this.hasDebug = hasDebug;
    }

    public String getLinkSpeed() {
        return linkSpeed;
    }

    public void setLinkSpeed(String linkSpeed) {
        this.linkSpeed = linkSpeed;
    }

    public String getRealIp() {
        return realIp;
    }

    public void setRealIp(String realIp) {
        this.realIp = realIp;
    }
}
