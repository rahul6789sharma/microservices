package org.stocksrin.common.model.intraday;

import java.io.Serializable;

import org.stocksrin.common.model.future.Future;
import org.stocksrin.common.model.option.AdvanceDecline;
import org.stocksrin.common.model.option.OptionModles;

public class IntraDayOptionModles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OptionModles optionModles;
	private AdvanceDecline allAdvanceDecline;
	private AdvanceDecline niftyAdvanceDecline;
	private OHCL previousDayohcl;
	private Future future;

	public Future getFuture() {
		return future;
	}

	public void setFuture(Future future) {
		this.future = future;
	}

	public OHCL getPreviousDayohcl() {
		return previousDayohcl;
	}

	public void setPreviousDayohcl(OHCL previousDayohcl) {
		this.previousDayohcl = previousDayohcl;
	}

	public OptionModles getOptionModles() {
		return optionModles;
	}

	public void setOptionModles(OptionModles optionModles) {
		this.optionModles = optionModles;
	}

	public AdvanceDecline getAllAdvanceDecline() {
		return allAdvanceDecline;
	}

	public void setAllAdvanceDecline(AdvanceDecline allAdvanceDecline) {
		this.allAdvanceDecline = allAdvanceDecline;
	}

	public AdvanceDecline getNiftyAdvanceDecline() {
		return niftyAdvanceDecline;
	}

	public void setNiftyAdvanceDecline(AdvanceDecline niftyAdvanceDecline) {
		this.niftyAdvanceDecline = niftyAdvanceDecline;
	}

}
