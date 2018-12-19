package AGENT_LOAD;

import TIME.TimeUtil;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

public class WakerForBuy extends WakerBehaviour {
	private Agent agent;
	private int[] buingLoad;
	private double[] spentMoney;
	private AID[] links;
	private int[] load;
	private long nextHour;
	private AID topic;
	public WakerForBuy(Agent agent,long nextHour, int[] buingLoad, double[] spentMoney, AID[] links, int[] load, AID topic) {
		super(agent, nextHour);
		this.agent = agent;
		this.buingLoad = buingLoad;
		this.links = links;
		this.spentMoney = spentMoney;
		this.load = load;
		this.topic = topic;
	}
@Override
protected void onWake() {
	super.onWake();
	long hourDuration = TimeUtil.hourDuration;
	myAgent.addBehaviour(new TickerBuy(this.agent,hourDuration, buingLoad,spentMoney,links,load,topic));
	myAgent.addBehaviour(new TickerBuyForStart(this.agent, buingLoad,spentMoney,links,load,topic));
	System.out.println("ЗапускТикера!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	
}
}
