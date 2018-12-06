package org.stocksrin.common.utils.options;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.option.OptionType;
import org.stocksrin.common.model.strategies.StrategyModel;
import org.stocksrin.common.utils.CommonUtils;
import org.stocksrin.common.utils.FileUtils;
import org.stocksrin.common.utils.html.CommonHTMLDocParsher;
import org.stocksrin.common.utils.html.HTMLPageDocumentDownloader;

public class OptionUtils
{
  public static OptionModles getOptionChain(String url, String expiry)
    throws Exception
  {
    Document doc = HTMLPageDocumentDownloader.getDocument(url);
    try
    {
      Elements c = CommonHTMLDocParsher.getOptionChainTable(doc, "octable", 0);
      OptionModles columns = CommonHTMLDocParsher.parseNSEColumn(doc, c);
      
      List<String> expiryList = CommonHTMLDocParsher.getSelectBoxById(doc, "date", 0);
      List<String> firstExpirys = new ArrayList();
      for (int i = 0; i < 6; i++) {
        firstExpirys.add(expiryList.get(i));
      }
      if (expiry != null) {
        columns.setExpiry(expiry);
      } else {
        columns.setExpiry((String)firstExpirys.get(0));
      }
      columns.setExpiryList(firstExpirys);
      return columns;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new Exception("ERROR OptionUtils !" + e.getMessage());
    }
  }
  
  public static Map<String, List<StrategyModel>> getStrategy(String dir)
  {
    Map<String, List<StrategyModel>> strategy = new HashMap();
    
    List<String> lst = FileUtils.listFilesForFolder(new File(dir));
    for (String string : lst)
    {
      String file = dir + string;
      List<StrategyModel> str = getStrategyModel(file);
      
      strategy.put(string, str);
    }
    return strategy;
  }
  
  public static synchronized List<StrategyModel> getStrategyModel(String file)
  {
    List<StrategyModel> result = new ArrayList(5);
    
    List<String[]> lst = CommonUtils.getCSVData(file);
    for (String[] strings : lst)
    {
      StrategyModel strategyModel = new StrategyModel();
      strategyModel.setStrategySerial(strings[0]);
      strategyModel.setExpiry(strings[1]);
      if (strings[2].equals("CALL")) {
        strategyModel.setType(OptionType.CALL);
      } else {
        strategyModel.setType(OptionType.PUT);
      }
      strategyModel.setStrike(Double.parseDouble(strings[3]));
      strategyModel.setAvgPrice(Double.parseDouble(strings[4]));
      strategyModel.setQuantity(Integer.parseInt(strings[5]));
      strategyModel.setTarget(Double.parseDouble(strings[6]));
      strategyModel.setStopLoss(Double.parseDouble(strings[7]));
      strategyModel.setSpot_close(Double.parseDouble(strings[8]));
      strategyModel.setDes(strings[9]);
      strategyModel.setStatus(Boolean.parseBoolean(strings[10]));
      strategyModel.setTraded_IV(Double.parseDouble(strings[11]));
      strategyModel.setTradeDate(strings[12]);
      result.add(strategyModel);
    }
    return result;
  }
}
