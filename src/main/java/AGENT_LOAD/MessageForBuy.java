package AGENT_LOAD;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

public class MessageForBuy extends WakerBehaviour {
	private Agent agent;
	private int[] buingLoad;
	private double[] spentMoney;
	private AID[] links;
	private int[] load;
	private AID topic;
	private long time;

	public MessageForBuy(Agent agent, int[] buingLoad, double[] spentMoney, AID[] links, int[] load, AID topic, long time) {
		super(agent,time);
		this.agent = agent;
		this.buingLoad = buingLoad;
		this.links = links;
		this.spentMoney = spentMoney;
		this.load = load;
		this.topic = topic;
	}
	@Override
	public void onWake() {
		int counterMSG = 0;
		//System.out.println(agent.getLocalName()+"need one VT");
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(topic);
			msg.setContent("FASS");
			myAgent.send(msg);
			counterMSG = 1;
			
		if (counterMSG==1) {
			agent.removeBehaviour(this);
		}
	}
	
	
}