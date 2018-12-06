package org.stocksrin.common.model;

public class NSEIndice
{
  private String date;
  private String change;
  private String imgFileName;
  private String name;
  private String pChange;
  private String lastPrice;
  
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
  
  public String getChange()
  {
    return this.change;
  }
  
  public void setChange(String change)
  {
    this.change = change;
  }
  
  public String getImgFileName()
  {
    return this.imgFileName;
  }
  
  public void setImgFileName(String imgFileName)
  {
    this.imgFileName = imgFileName;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getpChange()
  {
    return this.pChange;
  }
  
  public void setpChange(String pChange)
  {
    this.pChange = pChange;
  }
  
  public String getLastPrice()
  {
    return this.lastPrice.replace("", "");
  }
  
  public void setLastPrice(String lastPrice)
  {
    this.lastPrice = lastPrice;
  }
  
  public String toCsv()
  {
    return this.date + "," + this.change + "," + this.pChange + "," + this.lastPrice;
  }
  
  public String toString()
  {
    return "NSEIndice [date=" + this.date + ", change=" + this.change + ", imgFileName=" + this.imgFileName + ", name=" + this.name + ", pChange=" + this.pChange + ", lastPrice=" + this.lastPrice + "]";
  }
}
