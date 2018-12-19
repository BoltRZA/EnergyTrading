package AGENT_LOAD;
import javax.crypto.CipherInputStream;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BestPriseBeh extends Behaviour{
	private Agent agent;
	private int[] buingLoad;
	private double[] spentMoney;
	private AID[] links;
	private int[] load;
	private AID topic;
	public int buyLoad = 0;
	public int countARK;
	public double bestPrise;
	public int countDisconfirm = 0;
	public String[] winner;
	

	public BestPriseBeh(Agent agent, int[] buingLoad, double[] spentMoney, AID[] links, int[] load, AID topic) {
		this.agent = agent;
		this.buingLoad = buingLoad;
		this.links = links;
		this.spentMoney = spentMoney;
		this.load = load;
		this.topic = topic;
		countARK = 0;
		bestPrise = 1000;
		winner = new String[1];
		for(int i = 0; i < links.length; i++)
			if(links[i] != null)
				countARK++;
		
	}

	@Override
	public void action() {
		//Приём отказов
		MessageTemplate mt5 = MessageTemplate.and(MessageTemplate.MatchTopic(topic), 
				MessageTemplate.MatchPerformative(ACLMessage.DISCONFIRM));
		ACLMessage otkaz = null;
		do {
				otkaz = myAgent.receive(mt5);
				if(otkaz == null) continue;{
				countDisconfirm = countDisconfirm + 1;
				countARK = countARK -1;
				if (countDisconfirm == 1 & countARK==0) {
					countARK = 0;
					for(int i = 0; i < links.length; i++)
						if(links[i] != null)
							countARK++;
					myAgent.addBehaviour(new MessageForTorg(this.agent,topic,bestPrise,winner));	
					
				}
				if(countDisconfirm == (2) & countARK==0) {
					myAgent.addBehaviour(new MessageForFinalPrise(this.agent,topic,bestPrise,winner));
				}
				if(countDisconfirm ==3) {
					countARK = 0;
					for(int i = 0; i < links.length; i++)
						if(links[i] != null)
							countARK++;
					bestPrise = 1000;
					countDisconfirm = 0;
					myAgent.addBehaviour(new MessageForBuy(agent, buingLoad, spentMoney, links, buingLoad, topic, 100));
				

			}
				}
		} while (otkaz != null) ;
		

		//Приём информов о цене и отправка меньшей цены
		MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.MatchTopic(topic),
		MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		ACLMessage torg = null;
		do  {
			torg = myAgent.receive(mt1);
			if (torg==null) continue;{
			if (countARK >= 0 ) {
				
				if(Double.parseDouble(torg.getContent())<bestPrise) {
					bestPrise = Double.parseDouble(torg.getContent());
					winner[0] = torg.getSender().getLocalName();
					countARK = countARK - 1;
					//System.out.println(countARK);
					//System.out.println(bestPrise );
					
					
				}
				else if(Double.parseDouble(torg.getContent())>=bestPrise) {
					countARK = countARK - 1;
					//System.out.println(countARK);
				}
				if (countARK==0 & countDisconfirm < (2)) {
					myAgent.addBehaviour(new MessageForTorg(this.agent,topic,bestPrise,winner));
					countARK = 0;
					for(int i = 0; i < links.length; i++)
						if(links[i] != null)
							countARK++;	
					//System.out.println(countARK+"обновленный тикер");

				}
				if(countDisconfirm == (2) & countARK==0) {
					myAgent.addBehaviour(new MessageForFinalPrise(this.agent,topic,bestPrise,winner));
				}

			}
			}

		} while (torg != null);
		

		//Приём сообщений об согласии или отказе сетевой компании продажи ЭЭ по данной цене
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchTopic(topic), MessageTemplate
				.or(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),MessageTemplate.MatchPerformative(ACLMessage.FAILURE)));
		ACLMessage fin= myAgent.receive(mt);
		if(fin != null) {
			if(fin.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
				buingLoad[0]=buingLoad[0] - 1;
				buyLoad = buyLoad +1;
				spentMoney[0] = spentMoney[0]+Double.parseDouble(fin.getContent());

				if(buingLoad[0]==0) {
					System.out.println("потребитель "+myAgent.getLocalName()+" купил за текущий час "+ buyLoad+"Вт энергии и потратил"+spentMoney[0]+" рублей");
					agent.removeBehaviour(this);
				}
				if(buingLoad[0]!=0) {
					countARK = 0;
					for(int i = 0; i < links.length; i++)
						if(links[i] != null)
							countARK++;
					bestPrise = 1000;
					countDisconfirm = 0;
					myAgent.addBehaviour(new MessageForBuy(agent, buingLoad, spentMoney, links, buingLoad, topic, 100));
				}

			}
			else if (fin.getPerformative() == ACLMessage.FAILURE) {
				countARK = 0;
				for(int i = 0; i < links.length; i++)
					if(links[i] != null)
						countARK++;
				bestPrise = 1000;
				countDisconfirm = 0;
				myAgent.addBehaviour(new MessageForBuy(agent, buingLoad, spentMoney, links, buingLoad, topic, 100));
			}

		} 
		else {
			block();
		}
	



	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}


}

