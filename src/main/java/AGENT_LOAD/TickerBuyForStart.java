package AGENT_LOAD;

import TIME.TimeUtil;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class TickerBuyForStart extends OneShotBehaviour {
	private Agent agent;
	private int[] buingLoad;
	private double[] spentMoney;
	private AID[] links;
	private int[] load;
	private AID topic;

	public TickerBuyForStart(Agent agent, int[] buingLoad, double[] spentMoney, AID[] links, int[] load, AID topic) {
		this.agent = agent;
		this.buingLoad = buingLoad;
		this.links = links;
		this.spentMoney = spentMoney;
		this.load = load;
		this.topic = topic;
	}

	@Override
	public void action() {
		int hourD = TimeUtil.CurrentHour();
		buingLoad[0]=load[hourD];
		//Поведение отправка сообщения о начале торгов
		myAgent.addBehaviour(new MessageForBuy(this.agent, buingLoad,spentMoney,links,load,topic,100));
		//Поведения по сохранению лучшей цены
		myAgent.addBehaviour(new BestPriseBeh(this.agent, buingLoad,spentMoney,links,load,topic));

	}

}
