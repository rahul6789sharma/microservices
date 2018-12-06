package org.stocksrin.common.model;

public class FIIDIIDataModle
{
  private String date;
  private String fii_BuyValue;
  private String fii_SellValue;
  private String fii_net;
  private String dii_BuyValue;
  private String dii_SellValue;
  private String dii_net;
  private String niftyprice;
  private String niftyChange;
  private String stocks_Advance;
  private String stocks_Decline;
  
  public String toCsv()
  {
    return this.date + "," + this.fii_BuyValue + "," + this.fii_SellValue + "," + this.fii_net + "," + this.dii_BuyValue + "," + this.dii_SellValue + "," + this.dii_net + "," + this.niftyprice + "," + this.niftyChange + "," + this.stocks_Advance + "," + this.stocks_Decline;
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
  
  public String getFii_BuyValue()
  {
    return this.fii_BuyValue;
  }
  
  public void setFii_BuyValue(String fii_BuyValue)
  {
    this.fii_BuyValue = fii_BuyValue;
  }
  
  public String getFii_SellValue()
  {
    return this.fii_SellValue;
  }
  
  public void setFii_SellValue(String fii_SellValue)
  {
    this.fii_SellValue = fii_SellValue;
  }
  
  public String getFii_net()
  {
    return this.fii_net;
  }
  
  public void setFii_net(String fii_net)
  {
    this.fii_net = fii_net;
  }
  
  public String getDii_BuyValue()
  {
    return this.dii_BuyValue;
  }
  
  public void setDii_BuyValue(String dii_BuyValue)
  {
    this.dii_BuyValue = dii_BuyValue;
  }
  
  public String getDii_SellValue()
  {
    return this.dii_SellValue;
  }
  
  public void setDii_SellValue(String dii_SellValue)
  {
    this.dii_SellValue = dii_SellValue;
  }
  
  public String getDii_net()
  {
    return this.dii_net;
  }
  
  public void setDii_net(String dii_net)
  {
    this.dii_net = dii_net;
  }
  
  public String getNiftyChange()
  {
    return this.niftyChange;
  }
  
  public void setNiftyChange(String niftyChange)
  {
    this.niftyChange = niftyChange;
  }
  
  public String getStocks_Advance()
  {
    return this.stocks_Advance;
  }
  
  public void setStocks_Advance(String stocks_Advance)
  {
    this.stocks_Advance = stocks_Advance;
  }
  
  public String getStocks_Decline()
  {
    return this.stocks_Decline;
  }
  
  public void setStocks_Decline(String stocks_Decline)
  {
    this.stocks_Decline = stocks_Decline;
  }
  
  public String getNiftyprice()
  {
    return this.niftyprice;
  }
  
  public void setNiftyprice(String niftyprice)
  {
    this.niftyprice = niftyprice;
  }
  
  public String toString()
  {
    return "FIIDIIDataModle [date=" + this.date + " fii_BuyValue=" + this.fii_BuyValue + " fii_SellValue=" + this.fii_SellValue + " fii_net=" + this.fii_net + " dii_BuyValue=" + this.dii_BuyValue + " dii_SellValue=" + this.dii_SellValue + " dii_net=" + this.dii_net + " niftyprice=" + this.niftyprice + " niftyChange=" + this.niftyChange + " stocks_Advance=" + this.stocks_Advance + " stocks_Decline=" + this.stocks_Decline + "]";
  }
}
