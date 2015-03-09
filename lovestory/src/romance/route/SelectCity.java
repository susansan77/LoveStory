package romance.route;
import java.util.ArrayList;


public class SelectCity {
	
	public result getResult(String startstr,String endstr)
	{
		constants c=new constants();//����ȫ����·��������Ϣ�ͽ�㣬����ͼ
        System.out.println(constants.vexs.length);
       Dijkstra a =new Dijkstra();//��Dijkstra���ʼ������Ҫ��¼��·�������г�ʼ��
       //���������к��յ����
       int start=-1,end=-1;
       for(int j=0;j<constants.n;j++){
           if(startstr.equals(constants.vexs[j])) start=j+1;
           if(endstr.equals(constants.vexs[j])) end=j+1;       
       }
      if( start==1&&end==-1)   
           System.exit(0);
       //
       a.dijkstra(c.map, start, end);//�����ɺõĵ�ͼ���ص�Dijkstra�㷨�����ʵ�֣����й���·�����������������·�������������
       result result1=new result();//����࣬�ҹ����һ���õ��������������࣬��������У�Dijkstra��Ὣ���ɺõĽ��ͳͳ�������result��
       //��������Ŀ��ֻ��Ϊ��ʵ�����ְ�������ȷ�������ھ���
       //#################Dijkstra��Ὣ���ɺõĽ���������result��Ĺ���#################
       result1.setshortestdis(a.shortestdis);
       result1.setpassingcities(a.passingnodes);
       result1.setpasscitydis(a.passnodedis);
       
		return result1;
		
	};
	public ArrayList<String> getPassingNodes(String startstr,String endstr)
	{
		ArrayList<String> cities = new ArrayList<String>();
	       result aResult = getResult(startstr,endstr); 
	       for(int i=0;i<aResult.getpassnodes().size();i++){
	       	cities.add((String) aResult.getpassnodes().get(i));
	           //System.out.println(result1.getpassnodes().get(i));
	       }
		 
        return cities;		
	}
	public int getShortestDis(String startstr,String endstr)
	{
		result aResult = getResult(startstr,endstr); 
		return aResult.getshortestdis();	
	}
	public String getFitCity(String startstr,String endstr)
	{
		result aResult = getResult(startstr,endstr);
		return aResult.choosefitcity(aResult.passnodecity);
	}
	

}
