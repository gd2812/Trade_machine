/*Author: Zhechen DU
 Created: Oct 1, 2016
 This the the trade matching algorithm, where a trade request from user will be matched with trade request
 from database using h2*/
 
//USD ID: 1
//CAD ID: 2
//RMB ID: 3
import java.sql.SQLException;
import java.util.*;

public class Trade {

	public static void no_Trade (Stack<Integer> trans)
	{
		
	}
	public static void match (traderinfo buyerinfo)
	{
		//get buyerinfo
		int currencyid=buyerinfo.getcid();
		double buyerID= buyerinfo.getID();
		double amountleft=buyerinfo.getamount();
		
		double buyeramount=amountleft;
		//swith to seller exchang rate
		double buyerrate=1/buyerinfo.getrate();
		double buyerextotamount=0;		
		System.out.println("ID: " + buyerinfo.getID() + " rate: " + buyerinfo.getrate() + " amount: "
		+ amountleft + " time: " + buyerinfo.gettime());
		
		int exratebig=0;
		traderinfo sellerinfo=new traderinfo(0,0,0,0,0);
		while (amountleft>0)
		{
			try {
				sellerinfo=H2currenyPool.poolQuary(currencyid, amountleft);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("poolQuary not found");
			}
			double selleramount=sellerinfo.getamount()*sellerinfo.getrate();
			//int sellererID= sellerinfo.getID();
			//double sellerrate=sellerinfo.getrate();
			System.out.println("ID: " + sellerinfo.getID() + " C_ID: " + sellerinfo.getcid() 
			+ " rate: " + sellerinfo.getrate() + " amount: "
			+ selleramount + " time: " + sellerinfo.gettime());
			System.out.println("amountleft:"+amountleft);
			//if return is zero, break the while loop
			if (selleramount==0)
			{
				break;
			}
			//if amount left is qual to amount 
			else if (Math.round(amountleft*10000)==Math.round(selleramount*10000))
			{
				amountleft=0;
				seller_Stack.stackpush(sellerinfo);
				
			}
			else if (amountleft>selleramount)
			{
				amountleft=amountleft-selleramount;
				seller_Stack.stackpush(sellerinfo);
			}
			else
			{
				System.out.println("match error");
				break;
			}



		}
		
		//output the updated table
		try {
			H2currenyPool.readtable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(buyerextotamount);
		//small seller exchange=good 
	}

}