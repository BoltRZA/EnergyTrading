package POMXLGEN;



import java.util.List;

public class Snippet {
	public static  void main(String[] args) {
		//Создание СЭС
		//int [] gen = {0, 0, 0, 0, 0, 0, 1, 3, 8, 13, 14, 15, 15, 14, 13, 10, 8, 6, 2, 0, 0, 0, 0, 0};
		//Создание ВЭС
		int [] gen = {5, 3, 5, 2, 4, 3, 1, 0, 4, 5, 4, 5, 7, 4, 5, 2, 1, 2, 4, 3, 4, 2, 4, 2};
		Setting s  = new Setting();
		s.setGen(gen);
		WorkWithConfiGFiles.marshalAny(Setting.class, s, "VES.xml");
		//Setting s2 = WorkWithConfiGFiles.unMarshalAny(Setting.class, "Agent1.xml");
		//System.out.println();
		//Добавить ограничение для связей после шести 
		
//		 -gui -agents Buyer:AgentBuyer;Seller1:AgentSeller;Seller2:AgentSeller;Seller3:AgentSeller;
	}

}
