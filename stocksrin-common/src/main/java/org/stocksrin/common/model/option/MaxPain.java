package org.stocksrin.common.model.option;

public class MaxPain
{
  private Double strickPrice;
  private Integer ce_oi;
  private Integer pe_oi;
  private Double cumulativeCe;
  private Double cumulativePe;
  private Double total;
  
  public MaxPain() {}
  
  public MaxPain(Double strickPrice, Integer ce_oi, Integer pe_oi)
  {
    this.strickPrice = strickPrice;
    this.ce_oi = ce_oi;
    this.pe_oi = pe_oi;
  }
  
  public Double getStrickPrice()
  {
    return this.strickPrice;
  }
  
  public void setStrickPrice(Double strickPrice)
  {
    this.strickPrice = strickPrice;
  }
  
  public Integer getCe_oi()
  {
    return this.ce_oi;
  }
  
  public void setCe_oi(Integer ce_oi)
  {
    this.ce_oi = ce_oi;
  }
  
  public Integer getPe_oi()
  {
    return this.pe_oi;
  }
  
  public void setPe_oi(Integer pe_oi)
  {
    this.pe_oi = pe_oi;
  }
  
  public Double getCumulativeCe()
  {
    return this.cumulativeCe;
  }
  
  public void setCumulativeCe(Double cumulativeCe)
  {
    this.cumulativeCe = cumulativeCe;
  }
  
  public Double getCumulativePe()
  {
    return this.cumulativePe;
  }
  
  public void setCumulativePe(Double cumulativePe)
  {
    this.cumulativePe = cumulativePe;
  }
  
  public Double getTotal()
  {
    return this.total;
  }
  
  public void setTotal(Double total)
  {
    this.total = total;
  }
  
  public String toString()
  {
    return "MaxPain [strickPrice=" + this.strickPrice + " ce_oi=" + this.ce_oi + " pe_oi=" + this.pe_oi + " cumulativeCe=" + this.cumulativeCe + " cumulativePe=" + this.cumulativePe + " total=" + this.total + "]";
  }
}
