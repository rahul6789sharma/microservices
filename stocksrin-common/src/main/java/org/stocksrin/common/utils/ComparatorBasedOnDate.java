package org.stocksrin.common.utils;

import java.util.Comparator;
import java.util.Date;

public class ComparatorBasedOnDate
  implements Comparator<String>
{
  public int compare(String o1, String o2)
  {
    Date date1 = null;
    Date date2 = null;
    try
    {
      date1 = DateUtils.stringToDate(o1, "ddMMMyyyy");
      date2 = DateUtils.stringToDate(o2, "ddMMMyyyy");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return date1.compareTo(date2);
  }
}
