package AGENT_APK;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendAcceptProp extends OneShotBehaviour {

	private Agent agent;
	private AID topic;
	private  double[] priceNow;
	
	public SendAcceptProp (Agent agent, AID topic, double[] priceNow) {
		this.agent = agent;
		this.priceNow = priceNow;
		this.topic = topic;
		
		
	}


	@Override
	public void action() {
		 ACLMessage msg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
         msg.setContent(priceNow[0]+"");
         msg.addReceiver(topic);
         myAgent.send(msg);
         //System.out.println(agent.getLocalName()+"отправил энергию"+topic.getLocalName()+"по цене"+priceNow[0]);
        
	}

}
