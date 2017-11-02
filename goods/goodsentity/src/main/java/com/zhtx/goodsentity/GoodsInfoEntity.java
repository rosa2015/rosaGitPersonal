package com.zhtx.goodsentity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GoodsInfoEntity
{
	@JsonProperty("GoodsID")
	private int goodsID;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("Image")
	private String image;
	@JsonProperty("Images")
	private String images;
	@JsonProperty("Specifications")
	private String specifications;
	@JsonProperty("SCount")
	private String sCount;
	@JsonProperty("Unit")
	private int unit;
	@JsonProperty("Price")
	private double price;
	@JsonProperty("RetailPrice")
	private String retailPrice;
	@JsonProperty("BrandID")
	private int brandID;
	@JsonProperty("BrandName")
	private String brandName;
	@JsonProperty("Barcode")
	private String barcode;
	@JsonProperty("Count")
	private int count;
	@JsonProperty("ShopsID")
	private int shopsID;
	@JsonProperty("ShopName")
	private String shopName;
	@JsonProperty("Headline")
	private String headline;
	@JsonProperty("GoodVersion")
	private int goodVersion;
	@JsonProperty("PropertyType")
	private int propertyType;
	@JsonProperty("PropertyName")
	private String propertyName;
	@JsonProperty("IsReturn")
	private int isReturn;
	@JsonProperty("CompanyName")
	private String companyName;
	@JsonProperty("PublishAddress")
	private String publishAddress;
	@JsonProperty("QualityTime")
	private String qualityTime;
	@JsonProperty("Proxy")
	private String proxy;
	@JsonProperty("SalesPromotion")
	private String salesPromotion;
	@JsonProperty("AreaDetail")
	private String areaDetail;
	/*
	 * ordergoods表中的主键id
	 */
	@JsonProperty("Id")
    private int id;
	/*
	 * 订单字表ID
	 */
	@JsonProperty("OrderChildId")
	private int orderChildId;
	@JsonProperty("SnapDateTime")
	private String snapDateTime;
	public int getGoodsID()
	{
		return goodsID;
	}

	public void setGoodsID(int goodsID)
	{
		this.goodsID = goodsID;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public String getImages()
	{
		return images;
	}

	public void setImages(String images)
	{
		this.images = images;
	}

	public String getSpecifications()
	{
		return specifications;
	}

	public void setSpecifications(String specifications)
	{
		this.specifications = specifications;
	}

	public String getsCount()
	{
		return sCount;
	}

	public void setsCount(String sCount)
	{
		this.sCount = sCount;
	}

	public int getUnit()
	{
		return unit;
	}

	public void setUnit(int unit)
	{
		this.unit = unit;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}
 

	public String getRetailPrice()
	{
		return retailPrice;
	}

	public void setRetailPrice(String retailPrice)
	{
		this.retailPrice = retailPrice;
	}
 

	public int getBrandID()
	{
		return brandID;
	}

	public void setBrandID(int brandID)
	{
		this.brandID = brandID;
	}

	public String getBrandName()
	{
		return brandName;
	}

	public void setBrandName(String brandName)
	{
		this.brandName = brandName;
	}

	public String getBarcode()
	{
		return barcode;
	}

	public void setBarcode(String barcode)
	{
		this.barcode = barcode;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}
 
	public int getShopsID()
	{
		return shopsID;
	}

	public void setShopsID(int shopsID)
	{
		this.shopsID = shopsID;
	}
	public String getHeadline()
	{
		return headline;
	}

	public void setHeadline(String headline)
	{
		this.headline = headline;
	}
 

	public int getGoodVersion()
	{
		return goodVersion;
	}

	public void setGoodVersion(int goodVersion)
	{
		this.goodVersion = goodVersion;
	}

	public String getShopName()
	{
		return shopName;
	}

	public void setShopName(String shopName)
	{
		this.shopName = shopName;
	}

	public int getPropertyType()
	{
		return propertyType;
	}

	public void setPropertyType(int propertyType)
	{
		this.propertyType = propertyType;
	}

	public String getPropertyName()
	{
		return propertyName;
	}

	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}

	public int getIsReturn()
	{
		return isReturn;
	}

	public void setIsReturn(int isReturn)
	{
		this.isReturn = isReturn;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	public String getPublishAddress()
	{
		return publishAddress;
	}

	public void setPublishAddress(String publishAddress)
	{
		this.publishAddress = publishAddress;
	}
	public String getQualityTime()
	{
		return qualityTime;
	}

	public void setQualityTime(String qualityTime)
	{
		this.qualityTime = qualityTime;
	}

	public String getProxy()
	{
		return proxy;
	}

	public void setProxy(String proxy)
	{
		this.proxy = proxy;
	}

	public String getSalesPromotion()
	{
		return salesPromotion;
	}

	public void setSalesPromotion(String salesPromotion)
	{
		this.salesPromotion = salesPromotion;
	}

	public String getAreaDetail()
	{
		return areaDetail;
	}

	public void setAreaDetail(String areaDetail)
	{
		this.areaDetail = areaDetail;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getOrderChildId()
	{
		return orderChildId;
	}

	public void setOrderChildId(int orderChildId)
	{
		this.orderChildId = orderChildId;
	}

	public String getSnapDateTime()
	{
		return snapDateTime;
	}

	public void setSnapDateTime(String snapDateTime)
	{
		this.snapDateTime = snapDateTime;
	} 
}
