package asystent;

public class DayData
{
	DayData(double open_, double close_, double high_, double low_)
	{
		open=open_;
		close=close_;
		high=high_;
		low=low_;
	}
	
	double open, close, high, low, 
	pDM, mDM, emaP, emaM, tr, pDI, mDI, absoluteValue, adx,    //ADX
	typicalPrice, sma, ad, cci,                                                            //CCI
	momentum, roc, atr;
	
}
