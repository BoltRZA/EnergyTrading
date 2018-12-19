package POMXMLAPK;

import java.util.ArrayList;
import java.util.List;

public class Snippet {
	public static  void main(String[] args) {
		List<Link> links = new ArrayList<Link>();
		//АРК1
		links.add(new Link("SES", 40));
		//АРК2
		//links.add(new Link("SES", 30));
		//АРК3
		//links.add(new Link("VES", 25));
		Setting s  = new Setting();
		s.setLinks(links);
		//создать xml
		WorkWithConfiGFiles.marshalAny(Setting.class, s, "ARK1.xml");
		//Setting s2 = WorkWithConfiGFiles.unMarshalAny(Setting.class, "Agent1.xml");
		//System.out.println();
		//Добавить ограничение для связей после шести 
		
//		 -gui -agents Buyer:AgentBuyer;Seller1:AgentSeller;Seller2:AgentSeller;Seller3:AgentSeller;
	}

}
