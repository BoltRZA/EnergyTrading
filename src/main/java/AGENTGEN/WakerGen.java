package AGENTGEN;
import TIME.TimeUtil;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

public class WakerGen extends WakerBehaviour {
	private Agent agent;
	private AID[] links;
	private long nextSend;
	private int[] gen;
	public boolean startgen = false;
	public WakerGen(Agent agent, long nextSend, AID[] links, int[] gen) {
		super(agent, nextSend);
		this.agent = agent;
		this.links =links;
		this.gen = gen;
		this.nextSend = nextSend;
	}
	@Override
	protected void onWake() {
		super.onWake();
		long hourDuration = TimeUtil.hourDuration;
		myAgent.addBehaviour(new TickerGenerate(this.agent,hourDuration,links,gen));
		myAgent.addBehaviour(new TickerGenerateForStart(this.agent,links,gen));
	}
}
