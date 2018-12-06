package org.stocksrin.common.model.option;

import java.util.ArrayList;
import java.util.List;

public class MaxPains
{
  private String expiry;
  private String symbol;
  private Double maxPainStrick;
  private List<MaxPain> dataSet = new ArrayList();
  
  public String getExpiry()
  {
    return this.expiry;
  }
  
  public void setExpiry(String expiry)
  {
    this.expiry = expiry;
  }
  
  public String getSymbol()
  {
    return this.symbol;
  }
  
  public void setSymbol(String symbol)
  {
    this.symbol = symbol;
  }
  
  public List<MaxPain> getDataSet()
  {
    return this.dataSet;
  }
  
  public void setDataSet(List<MaxPain> dataSet)
  {
    this.dataSet = dataSet;
  }
  
  public Double getMaxPainStrick()
  {
    return this.maxPainStrick;
  }
  
  public void setMaxPainStrick(Double maxPainStrick)
  {
    this.maxPainStrick = maxPainStrick;
  }
  
  public String toString()
  {
    return "MaxPains [expiry=" + this.expiry + " symbol=" + this.symbol + " maxPainStrick=" + this.maxPainStrick + " dataSet=" + this.dataSet + "]";
  }
}
