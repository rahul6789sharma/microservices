package org.stocksrin.common.model.option;

public class OptionModle {
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

	// for OI Notification
	private Integer c_oiLimits = 900000;
	private Integer p_oiLimits = 800000;

	public void setValueUSDINR(int i, String value) {
		// if(value.contains("71")) {
		//
		// }
		// System.out.println("i "+i + " ->" + value);
		if (i == 0) {
			// CE_Chart = value;
		} else if (i == 1) {
			// System.out.println("c_oi "+ i + " -> " + value);
			if (!value.equals("-")) {

				c_oi = Integer.parseInt(value.replace(",", ""));
			}

		} else if (i == 2) {
			// System.out.println("c_change_oi "+ i + " -> " + value);
			if (!value.equals("-")) {
				c_change_oi = Integer.parseInt(value.replace(",", ""));
			}
		} else if (i == 3) {
			// CE_Volume = value;
		} else if (i == 4) {
			// System.out.println("c_iv "+ i + " -> " + value);
			if (!value.equals("-")) {
				c_iv = Double.parseDouble(value);
			}
		} else if (i == 5) {
			// System.out.println("c_ltp "+ i + " -> " + value);
			if (!value.equals("-")) {
				c_ltp = Double.parseDouble(value.replace(",", ""));
			}
		} else if (i == 6) {
			// CE_Net_Change = value;
		} else if (i == 7) {
			// CE_Bid_Price = value;
		} else if (i == 8) {
			// CE_Ask_Price = value;
		} else if (i == 9) {
			// CE_Ask_Qty = value;
		} else if (i == 10) {
			// System.out.println("strike_price "+ i + " -> " + value);
			if (!value.equals("-")) {
				value = value.substring(1, value.length() - 1);
				strike_price = Double.parseDouble(value);
			}
		} else if (i == 11) {
			// PE_Bid_Price = value;
		} else if (i == 12) {
			// PE_Ask_Price = value;
		} else if (i == 13) {
			// PE_Ask_Qty = value;
		} else if (i == 14) {
			// PE_Ask_Qty = value;
		} else if (i == 15) {
			// System.out.println("p_ltp "+ i + " -> " + value);
			if (!value.equals("-")) {
				p_ltp = Double.parseDouble(value.replace(",", ""));
			}
		} else if (i == 16) {
			// System.out.println("p_iv "+ i + " -> " + value);
			if (!value.equals("-")) {
				p_iv = Double.parseDouble(value.replace(",", ""));
			}
		} else if (i == 17) {
			// pe volume
		} else if (i == 18) {
			// System.out.println("p_change_oi "+ i + " -> " + value);
			if (!value.equals("-")) {
				p_change_oi = Integer.parseInt(value.replace(",", ""));
			}
		} else if (i == 19) {
			// System.out.println("p_oi "+ i + " -> " + value);
			if (!value.equals("-")) {
				p_oi = Integer.parseInt(value.replace(",", ""));
			}
		} else if (i == 20) {
			//
		} else if (i == 21) {
			// PE_Chart = value;
		}
		// System.out.println("\n ");
		// System.out.println(this);
	}

	public void setValue(int i, String value) {
		if (i != 0) {
			if (i == 1) {
				if (!value.equals("-")) {
					this.c_oi = Integer.valueOf(Integer.parseInt(value.replace(",", "")));
				}
			} else if (i == 2) {
				if (!value.equals("-")) {
					this.c_change_oi = Integer.valueOf(Integer.parseInt(value.replace(",", "")));
				}
			} else if (i != 3) {
				if (i == 4) {
					if (!value.equals("-")) {
						this.c_iv = Double.valueOf(Double.parseDouble(value));
					}
				} else if (i == 5) {
					if (!value.equals("-")) {
						this.c_ltp = Double.valueOf(Double.parseDouble(value.replace(",", "")));
					}
				} else if (i != 6) {
					if (i != 7) {
						if (i != 8) {
							if (i != 9) {
								if (i == 11) {
									this.strike_price = Double.valueOf(Double.parseDouble(value));
								} else if (i != 12) {
									if (i != 13) {
										if (i != 14) {
											if (i != 15) {
												if (i == 16) {
													if (!value.equals("-")) {
														this.p_net_change = Double.valueOf(Double.parseDouble(value.replace(",", "")));
													}
												} else if (i == 17) {
													if (!value.equals("-")) {
														this.p_ltp = Double.valueOf(Double.parseDouble(value.replace(",", "")));
													}
												} else if (i == 18) {
													if (!value.equals("-")) {
														this.p_iv = Double.valueOf(Double.parseDouble(value));
													}
												} else if (i != 19) {
													if (i == 20) {
														if (!value.equals("-")) {
															this.p_change_oi = Integer.valueOf(Integer.parseInt(value.replace(",", "")));
														}
													} else if (i == 21) {
														if (!value.equals("-")) {
															this.p_oi = Integer.valueOf(Integer.parseInt(value.replace(",", "")));
														}
													} else if (i != 22) {
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
	}

	public Integer getC_oi() {
		return this.c_oi;
	}

	public void setC_oi(Integer c_oi) {
		this.c_oi = c_oi;
	}

	public Integer getC_change_oi() {
		return this.c_change_oi;
	}

	public void setC_change_oi(Integer c_change_oi) {
		this.c_change_oi = c_change_oi;
	}

	public Integer getC_volume() {
		return this.c_volume;
	}

	public void setC_volume(Integer c_volume) {
		this.c_volume = c_volume;
	}

	public Double getC_ltp() {
		return this.c_ltp;
	}

	public void setC_ltp(Double c_ltp) {
		this.c_ltp = c_ltp;
	}

	public Double getC_net_change() {
		return this.c_net_change;
	}

	public void setC_net_change(Double c_net_change) {
		this.c_net_change = c_net_change;
	}

	public Double getStrike_price() {
		return this.strike_price;
	}

	public void setStrike_price(Double strike_price) {
		this.strike_price = strike_price;
	}

	public Double getP_net_change() {
		return this.p_net_change;
	}

	public void setP_net_change(Double p_net_change) {
		this.p_net_change = p_net_change;
	}

	public Double getP_ltp() {
		return this.p_ltp;
	}

	public void setP_ltp(Double p_ltp) {
		this.p_ltp = p_ltp;
	}

	public Integer getP_volume() {
		return this.p_volume;
	}

	public void setP_volume(Integer p_volume) {
		this.p_volume = p_volume;
	}

	public Integer getP_change_oi() {
		return this.p_change_oi;
	}

	public void setP_change_oi(Integer p_change_oi) {
		this.p_change_oi = p_change_oi;
	}

	public Integer getP_oi() {
		return this.p_oi;
	}

	public void setP_oi(Integer p_oi) {
		this.p_oi = p_oi;
	}

	public Double getC_iv() {
		return this.c_iv;
	}

	public void setC_iv(Double c_iv) {
		this.c_iv = c_iv;
	}

	public Double getP_iv() {
		return this.p_iv;
	}

	public void setP_iv(Double p_iv) {
		this.p_iv = p_iv;
	}

	/*
	 * public String toString() { return "OptionModle [c_oi=" + this.c_oi +
	 * ", c_change_oi=" + this.c_change_oi + ", c_volume=" + this.c_volume +
	 * ", c_iv=" + this.c_iv + ", c_ltp=" + this.c_ltp + ", c_net_change=" +
	 * this.c_net_change + ", strike_price=" + this.strike_price + ", p_net_change="
	 * + this.p_net_change + ", p_ltp=" + this.p_ltp + ", p_iv=" + this.p_iv +
	 * ", p_volume=" + this.p_volume + ", p_change_oi=" + this.p_change_oi +
	 * ", p_oi=" + this.p_oi + "]"; }
	 */

	public Integer getC_oiLimits() {
		return c_oiLimits;
	}

	public void setC_oiLimits(Integer c_oiLimits) {
		this.c_oiLimits = c_oiLimits;
	}

	public Integer getP_oiLimits() {
		return p_oiLimits;
	}

	public void setP_oiLimits(Integer p_oiLimits) {
		this.p_oiLimits = p_oiLimits;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((strike_price == null) ? 0 : strike_price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OptionModle other = (OptionModle) obj;
		if (strike_price == null) {
			if (other.strike_price != null)
				return false;
		} else if (!strike_price.equals(other.strike_price))
			return false;
		return true;
	}

}
