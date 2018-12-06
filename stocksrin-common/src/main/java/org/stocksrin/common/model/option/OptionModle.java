package org.stocksrin.common.model.option;

public class OptionModle
{
  private Integer c_oi;
  private Integer c_change_oi;
  private Integer c_volume;
  private Double c_iv;
  private Double c_ltp;
  private Double c_net_change;
  private Double strike_price;
  private Double p_net_change;
  private Double p_ltp;
  private Double p_iv;
  private Integer p_volume;
  private Integer p_change_oi;
  private Integer p_oi;
  
  public void setValue(int i, String value)
  {
    if (i != 0) {
      if (i == 1)
      {
        if (!value.equals("-")) {
          this.c_oi = Integer.valueOf(Integer.parseInt(value.replace(",", "")));
        }
      }
      else if (i == 2)
      {
        if (!value.equals("-")) {
          this.c_change_oi = Integer.valueOf(Integer.parseInt(value.replace(",", "")));
        }
      }
      else if (i != 3) {
        if (i == 4)
        {
          if (!value.equals("-")) {
            this.c_iv = Double.valueOf(Double.parseDouble(value));
          }
        }
        else if (i == 5)
        {
          if (!value.equals("-")) {
            this.c_ltp = Double.valueOf(Double.parseDouble(value.replace(",", "")));
          }
        }
        else if (i != 6) {
          if (i != 7) {
            if (i != 8) {
              if (i != 9) {
                if (i == 11) {
                  this.strike_price = Double.valueOf(Double.parseDouble(value));
                } else if (i != 12) {
                  if (i != 13) {
                    if (i != 14) {
                      if (i != 15) {
                        if (i == 16)
                        {
                          if (!value.equals("-")) {
                            this.p_net_change = Double.valueOf(Double.parseDouble(value.replace(",", "")));
                          }
                        }
                        else if (i == 17)
                        {
                          if (!value.equals("-")) {
                            this.p_ltp = Double.valueOf(Double.parseDouble(value.replace(",", "")));
                          }
                        }
                        else if (i == 18)
                        {
                          if (!value.equals("-")) {
                            this.p_iv = Double.valueOf(Double.parseDouble(value));
                          }
                        }
                        else if (i != 19) {
                          if (i == 20)
                          {
                            if (!value.equals("-")) {
                              this.p_change_oi = Integer.valueOf(Integer.parseInt(value.replace(",", "")));
                            }
                          }
                          else if (i == 21)
                          {
                            if (!value.equals("-")) {
                              this.p_oi = Integer.valueOf(Integer.parseInt(value.replace(",", "")));
                            }
                          }
                          else if (i != 22) {}
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  public Integer getC_oi()
  {
    return this.c_oi;
  }
  
  public void setC_oi(Integer c_oi)
  {
    this.c_oi = c_oi;
  }
  
  public Integer getC_change_oi()
  {
    return this.c_change_oi;
  }
  
  public void setC_change_oi(Integer c_change_oi)
  {
    this.c_change_oi = c_change_oi;
  }
  
  public Integer getC_volume()
  {
    return this.c_volume;
  }
  
  public void setC_volume(Integer c_volume)
  {
    this.c_volume = c_volume;
  }
  
  public Double getC_ltp()
  {
    return this.c_ltp;
  }
  
  public void setC_ltp(Double c_ltp)
  {
    this.c_ltp = c_ltp;
  }
  
  public Double getC_net_change()
  {
    return this.c_net_change;
  }
  
  public void setC_net_change(Double c_net_change)
  {
    this.c_net_change = c_net_change;
  }
  
  public Double getStrike_price()
  {
    return this.strike_price;
  }
  
  public void setStrike_price(Double strike_price)
  {
    this.strike_price = strike_price;
  }
  
  public Double getP_net_change()
  {
    return this.p_net_change;
  }
  
  public void setP_net_change(Double p_net_change)
  {
    this.p_net_change = p_net_change;
  }
  
  public Double getP_ltp()
  {
    return this.p_ltp;
  }
  
  public void setP_ltp(Double p_ltp)
  {
    this.p_ltp = p_ltp;
  }
  
  public Integer getP_volume()
  {
    return this.p_volume;
  }
  
  public void setP_volume(Integer p_volume)
  {
    this.p_volume = p_volume;
  }
  
  public Integer getP_change_oi()
  {
    return this.p_change_oi;
  }
  
  public void setP_change_oi(Integer p_change_oi)
  {
    this.p_change_oi = p_change_oi;
  }
  
  public Integer getP_oi()
  {
    return this.p_oi;
  }
  
  public void setP_oi(Integer p_oi)
  {
    this.p_oi = p_oi;
  }
  
  public Double getC_iv()
  {
    return this.c_iv;
  }
  
  public void setC_iv(Double c_iv)
  {
    this.c_iv = c_iv;
  }
  
  public Double getP_iv()
  {
    return this.p_iv;
  }
  
  public void setP_iv(Double p_iv)
  {
    this.p_iv = p_iv;
  }
  
  public String toString()
  {
    return "OptionModle [c_oi=" + this.c_oi + ", c_change_oi=" + this.c_change_oi + ", c_volume=" + this.c_volume + ", c_iv=" + this.c_iv + ", c_ltp=" + this.c_ltp + ", c_net_change=" + this.c_net_change + ", strike_price=" + this.strike_price + ", p_net_change=" + this.p_net_change + ", p_ltp=" + this.p_ltp + ", p_iv=" + this.p_iv + ", p_volume=" + this.p_volume + ", p_change_oi=" + this.p_change_oi + ", p_oi=" + this.p_oi + "]";
  }
}
