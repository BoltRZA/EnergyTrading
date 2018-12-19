package AGENT_LOAD;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;


public class MessageForTorg extends OneShotBehaviour {
	private Agent agent;
	private AID topic;
	private double bestPrise;
	private String[] winner;

	public MessageForTorg(Agent agent, AID topic, double bestPrise,String[] winner) {
		this.agent = agent;
		this.bestPrise = bestPrise;
		this.topic = topic;
		this.winner = winner;
	}

	@Override
	public void action() {
		ACLMessage msg =new ACLMessage(ACLMessage.CONFIRM);
		msg.addReceiver(topic);
		msg.setContent(bestPrise+""+"-"+winner[0]);
		myAgent.send(msg);
		//System.out.println(myAgent.getLocalName()+" best Price now "+bestPrise+"предложенная агентом"+winner[0]);

	}

}
