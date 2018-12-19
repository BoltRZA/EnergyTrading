package AGENT_APK;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendOtkaz extends OneShotBehaviour {

	private Agent agent;
	private AID topic;

	public SendOtkaz(Agent agent, AID topic) {
		this.agent = agent;
		this.topic = topic;
		
	}

	@Override
	public void action() {
		 ACLMessage msg = new ACLMessage(ACLMessage.DISCONFIRM);
         msg.setContent("PASS");
         msg.addReceiver(topic);
         myAgent.send(msg);

	}

}
