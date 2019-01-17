package org.stocksrin.ai;

import java.util.Map;

import org.stocksrin.common.utils.AppConstant;
import org.stocksrin.common.utils.FileUtils;

public class Util {

	public static void main(String[] args) throws Exception {
		System.out.println(FileUtils.lasFilePath(AppConstant.STOCKSRIN_AI));
		//NIftyAIData.straddleData
		Map<String, Map<String, Straddle>>  data= (Map<String, Map<String, Straddle>> )FileUtils.fromJson(FileUtils.lasFilePath(AppConstant.STOCKSRIN_AI));
		//System.out.println(data);
		NIftyAIData.add(data);
		System.out.println(NIftyAIData.straddleData);
	}
	
	
	
}
