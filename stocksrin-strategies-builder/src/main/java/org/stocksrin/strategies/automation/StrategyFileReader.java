package org.stocksrin.strategies.automation;

import java.util.Map;
import java.util.Set;

import org.stocksrin.collector.option.data.InMemoryStrategyies;
import org.stocksrin.collector.option.data.utils.BreakEvenCalUtils;
import org.stocksrin.common.model.strategies.Strategy;
import org.stocksrin.common.model.strategies.Strategy.UnderLying;
import org.stocksrin.common.utils.StrategyUtil;

public class StrategyFileReader {

	public static synchronized void startManualStrategies(String starttegyDir) {
		try {
			try {

				LockObject.getWriteLock();

				// reading strategy frm file
				Map<String, Strategy> strategyMap = StrategyUtil.getStrategy2(starttegyDir);
				Set<String> strategies = strategyMap.keySet();

				for (String string : strategies) {

					Strategy startgy = strategyMap.get(string);

					if (startgy.getUnderlying().equals(UnderLying.BANKNIFTY)) {

						try {
							BreakEvenCalUtils.calculatebreakEven(startgy);
							BreakEvenCalUtils.findBreakEven(startgy);
						} catch (Exception e) {
							e.printStackTrace();
						}

						// Result
						// update in inmemeory and after that we have to update
						// ltp
						// based on dir it ll be updated in intra day or
						// positional
						InMemoryStrategyies.put(string, startgy, starttegyDir);

					} else if (startgy.getUnderlying().equals(UnderLying.NIFTY)) {

						// Result
						InMemoryStrategyies.put(string, startgy, starttegyDir);

					} else {
						System.out.println("****** Underlying unknown ****");
					}
				}
			} finally {
				LockObject.realseWriteLock();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}