//����� ��������� ������ ����� ������ ��� ����������� ������������� ������� � �� ������ ����� �� ����� �����������
package AGENT_LOAD;


import POMXMLLOAD.Setting;
import POMXMLLOAD.WorkWithConfiGFiles;
import TIME.TimeUtil;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgentLoad extends Agent {
	public AID[] links;
	public int loadNow;
	public int[] buingLoad;
	public double[] spentMoney;
	
	@Override
	protected void setup() {
		super.setup();
		links = new AID[10];
		String agentName = this.getLocalName();
		Setting properties = WorkWithConfiGFiles.unMarshalAny(Setting.class, agentName+".xml");
		int[] load = properties.getLoad();
		//�����������
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd  = new ServiceDescription();
		sd.setType("LOAD");
		sd.setName(agentName);
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		//����� ���
		addBehaviour(new FindAPK(this,10000,links));

		buingLoad = new int[1];
		spentMoney = new double[1];
		//��������� ������ ������
			addBehaviour(new StartBuy(this,15000, buingLoad,spentMoney,links,load));
	}
	
}
