package AGENT_LOAD;

import TIME.TimeUtil;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class TickerBuy extends TickerBehaviour {
	private Agent agent;
	private int[] buingLoad;
	private double[] spentMoney;
	private AID[] links;
	private int[] load;
	private long hourD;
	private AID topic;

	public TickerBuy(Agent agent,long hourD, int[] buingLoad, double[] spentMoney, AID[] links, int[] load, AID topic) {
		super(agent, hourD);
		this.agent = agent;
		this.buingLoad = buingLoad;
		this.links = links;
		this.spentMoney = spentMoney;
		this.load = load;
		this.topic = topic;
	
	}

	@Override
	protected void onTick() {
		int hourD = TimeUtil.CurrentHour();
		buingLoad[0]=load[hourD];
		//Поведение отправка сообщения о начале торгов
		myAgent.addBehaviour(new MessageForBuy(this.agent, buingLoad,spentMoney,links,load,topic,1000));
		//Поведения по сохранению лучшей цены
		myAgent.addBehaviour(new BestPriseBeh(this.agent, buingLoad,spentMoney,links,load,topic));
		
	}

}
