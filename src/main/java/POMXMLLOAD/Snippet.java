package POMXMLLOAD;



import java.util.List;

public class Snippet {
	public static  void main(String[] args) {
		//�������� ��1
		int [] load = {1, 1, 2, 3, 2, 2, 2, 1, 2, 3, 4, 2, 2, 2, 2, 1, 2, 3, 4, 4, 2, 1, 1, 1};
		//�������� ��2
		//int [] load = {3, 3, 2, 1, 1, 1, 1, 2, 1, 1, 4, 4, 3, 3, 1, 3, 2, 2, 3, 1, 2, 1, 1, 1};
		//�������� ��3
		//int [] load = {1, 1, 1, 2, 3, 4, 4, 4, 3, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 1, 3, 1};
		//�������� ��4
		//int[] load = {2, 2, 4, 3, 1, 3, 3, 1, 4, 1, 3, 1, 2, 2, 1, 1, 2, 4, 3, 2, 1, 1, 1, 1};
		Setting s  = new Setting();
		s.setLoad(load);
		WorkWithConfiGFiles.marshalAny(Setting.class, s, "AP1.xml");
		//Setting s2 = WorkWithConfiGFiles.unMarshalAny(Setting.class, "Agent1.xml");
		//System.out.println();
		//�������� ����������� ��� ������ ����� ����� 
		
//		 -gui -agents Buyer:AgentBuyer;Seller1:AgentSeller;Seller2:AgentSeller;Seller3:AgentSeller;
	}

}
