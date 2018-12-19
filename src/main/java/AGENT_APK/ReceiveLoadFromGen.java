package AGENT_APK;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveLoadFromGen extends Behaviour {
	private Agent agent;
	private double[] akum;
	private long timeout;
	public ReceiveLoadFromGen(Agent agent, double[] akum) {
		this.agent = agent;
		this.akum = akum;

	}

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
				MessageTemplate.MatchProtocol("loadToAPK"));
		ACLMessage msg = agent.receive(mt);
		if(msg!=null) {
			akum[0] = akum[0]+Double.parseDouble(msg.getContent());
			System.out.println(akum[0]+"����� �����"+"� ������ "+myAgent.getLocalName());
		}
		else {
			block();
		}

	}


	@Override
	public boolean done() {
		return false;
	}
}
