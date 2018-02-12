package cn.saturn.web.controllers.item.dao;

public class ItemModel {
	public static final int quality_white = 1; // 白
    public static final int quality_green = 2; // 绿
    public static final int quality_blue = 3; // 蓝
    public static final int quality_purple = 4; // 紫
    public static final int quality_orange = 5; // 橙

    // 物品类型
    public static final int TYPE_TOKEN = 1; // 代币
    public static final int TYPE_EQUIP = 6; // 装备

    public static final int TYPE_CSB = 2; // 消耗品
    public static final int TYPE_EXP = 8; // 经验药水

    public static final int TYPE_RES = 5; // 材料图子
    public static final int TYPE_TRADE = 0; // 贸易品
    public static final int TYPE_PET = 10; // 宠物卡牌

    public static final int TYPE_HUNT = 11; // 狩猎场
    public static final int TYPE_HUNT_HORN = 12; // 狩猎场 神兽号角

    // 参数
    private int itemId;
    private String itemName;
    private String itemDescription;
    private String itemIcon;
    private int lvRequir; // 等级需求
    private int itemMaxCount;
    private int itemType;
    private int useType;
    private int itemQuality;
    private int itemPrice;
    private String dropLevelList;
    private String dropAreana;
    private String equipCharacterList;
    private String compoundItemList;
    private int useEffectId;

    private int packageDrop;
    private int equipmentProperty;
    private int petId;
    
    private String isPuzzle;
    private String dropArena;

    
    public String getDropArena() {
		return dropArena;
	}

	public void setDropArena(String dropArena) {
		this.dropArena = dropArena;
	}

	public String getIsPuzzle() {
		return isPuzzle;
	}

	public void setIsPuzzle(String isPuzzle) {
		this.isPuzzle = isPuzzle;
	}

	public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemIcon() {
        return itemIcon;
    }

    public int getLvRequir() {
        return lvRequir;
    }

    public int getItemMaxCount() {
        return itemMaxCount;
    }

    public int getItemType() {
        return itemType;
    }

    public int getUseType() {
        return useType;
    }

    public int getItemQuality() {
        return itemQuality;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public String getDropLevelList() {
        return dropLevelList;
    }

    public String getDropAreana() {
        return dropAreana;
    }

    public String getEquipCharacterList() {
        return equipCharacterList;
    }

    public String getCompoundItemList() {
        return compoundItemList;
    }

    public int getUseEffectId() {
        return useEffectId;
    }

    public int getPackageDrop() {
        return packageDrop;
    }

    public int getEquipmentProperty() {
        return equipmentProperty;
    }

    public int getPetId() {
        return petId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }

    public void setLvRequir(int lvRequir) {
        this.lvRequir = lvRequir;
    }

    public void setItemMaxCount(int itemMaxCount) {
        this.itemMaxCount = itemMaxCount;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setUseType(int useType) {
        this.useType = useType;
    }

    public void setItemQuality(int itemQuality) {
        this.itemQuality = itemQuality;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setDropLevelList(String dropLevelList) {
        this.dropLevelList = dropLevelList;
    }

    public void setDropAreana(String dropAreana) {
        this.dropAreana = dropAreana;
    }

    public void setEquipCharacterList(String equipCharacterList) {
        this.equipCharacterList = equipCharacterList;
    }

    public void setCompoundItemList(String compoundItemList) {
        this.compoundItemList = compoundItemList;
    }

    public void setUseEffectId(int useEffectId) {
        this.useEffectId = useEffectId;
    }

    public void setPackageDrop(int packageDrop) {
        this.packageDrop = packageDrop;
    }

    public void setEquipmentProperty(int equipmentProperty) {
        this.equipmentProperty = equipmentProperty;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }
}
