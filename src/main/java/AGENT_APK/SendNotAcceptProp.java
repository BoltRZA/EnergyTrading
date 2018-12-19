package AGENT_APK;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendNotAcceptProp extends OneShotBehaviour {

	private Agent agent;
	private AID topic;	
	public SendNotAcceptProp (Agent agent, AID topic) {
		this.agent = agent;
		this.topic = topic;		
	}
	@Override
	public void action() {
		 ACLMessage msg = new ACLMessage(ACLMessage.FAILURE);
         msg.addReceiver(topic);
         myAgent.send(msg);

       
	}

}