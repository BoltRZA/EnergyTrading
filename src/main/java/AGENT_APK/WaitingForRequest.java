package AGENT_APK;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitingForRequest extends Behaviour {
	private Agent agent;
	private AID[] links;
	private double[] money;
	private double[] akum;
	private int[] countLink;
	private AID topic;
	private double maxPrice;
	public double[] priceNow= new double[1]; 
	public int[] countDisconfirm = new int[1];
	public int countMyselfDisconfirm;
	public int coun = 0;
	public WaitingForRequest(Agent agent, AID[] links, double[] money, double[] akum, int[] countLink,double maxPrice, AID topic) {
		this.agent = agent;
		this.myAgent = agent;
		this.akum = akum;
		this.links = links;
		this.money = money;
		this.countLink = countLink;
		this.maxPrice = maxPrice;
		this.topic = topic;
		priceNow[0]=maxPrice;
		countMyselfDisconfirm = 0;
		countDisconfirm[0] = 0;
	}

	@Override
	public void action() {


		//Приём первого сообщения от нагрузки и отправка первой цены
		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchTopic(topic), MessageTemplate.and(
						MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
						MessageTemplate.not(MessageTemplate.MatchSender(myAgent.getAID()))));
		ACLMessage msg = myAgent.receive(mt);
		if ((msg != null)) {
			countMyselfDisconfirm = 0;
			priceNow[0]=maxPrice;
			myAgent.addBehaviour(new SendPrice(myAgent,topic,priceNow));


			//System.out.println("Запрос принял агент "+myAgent.getLocalName()+" от агента "+msg.getSender().getLocalName());

		}
		else {
			block();
		}
		//Приём сообщения  торги
		MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.MatchTopic(topic), MessageTemplate
				.and(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM),MessageTemplate.not(MessageTemplate.MatchSender(myAgent.getAID()))));
		ACLMessage torg = null;
		do {
			torg = myAgent.receive(mt1);
			if (torg == null) continue;
			String [] subStr1 = torg.getContent().split("-");
			//System.out.println(subStr1[1]);
			//Отправка сообщения отказа если предложенная кем то цена ниже чем ту которую агент может предложить
			double minPrice = maxPrice*(1-(akum[0]/100));
			minPrice = minPrice* 1000;
			int i = (int) Math.round(minPrice);
			minPrice = (double)i / 1000;
			if((Double.parseDouble(subStr1[0])<=minPrice & subStr1[1].equals(myAgent.getLocalName()+"")  )) {
				priceNow[0]=Double.parseDouble(subStr1[0]);
				myAgent.addBehaviour(new SendPrice(myAgent,topic,priceNow));	
			//	System.out.println("Мин цена");
			}
			else if((Double.parseDouble(subStr1[0])<=minPrice&(!subStr1[1].equals(myAgent.getLocalName()+"") ))) {
				myAgent.addBehaviour(new SendOtkaz(myAgent,topic));
				countMyselfDisconfirm = 1;
				//System.out.println(myAgent.getLocalName()+"Пасанул в топике"+topic.getLocalName());
			}
			//Отправка сообщения с меньшей ценой, если таковую агент может предложить
			if((Double.parseDouble(subStr1[0])>=minPrice)){

				if (priceNow[0]*0.9>minPrice) {
					priceNow[0]=(priceNow[0]*0.9);
					myAgent.addBehaviour(new SendPrice(myAgent,topic,priceNow));
					//System.out.println("Торги2");
					//System.out.println(priceNow[0]);
				}
				else {
					priceNow[0]=minPrice;
					myAgent.addBehaviour(new SendPrice(myAgent,topic,priceNow));	
					//System.out.println("Мин цена");
				}

			}

		} while (torg != null);

	
	//Приём сообщений о финальной цене
	MessageTemplate mt4 = MessageTemplate.and(MessageTemplate.MatchTopic(topic), MessageTemplate
			.and(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),MessageTemplate.not(MessageTemplate.MatchSender(myAgent.getAID()))));
	ACLMessage fin = null;
	do {
		fin = myAgent.receive(mt4);
		if(fin == null) continue;
		String [] subStr = fin.getContent().split("-");
		//System.out.println(subStr[1]);
		//System.out.println(Double.parseDouble(subStr[0]));
		//System.out.println(myAgent.getLocalName());
		if(subStr[1].equals(myAgent.getLocalName()+"")) {
			if(Double.parseDouble(subStr[0])==priceNow[0]) {
				myAgent.addBehaviour(new SendAcceptProp(myAgent,topic,priceNow));
				akum[0] = akum[0]-1;
			}
			else if(Double.parseDouble(subStr[0])!=priceNow[0]) {
				myAgent.addBehaviour(new SendNotAcceptProp(myAgent,topic));
			}
		}

	} while  (fin != null);


}

@Override
public boolean done() {
	// TODO Auto-generated method stub
	return false;
}

}


