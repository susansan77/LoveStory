package romance.route;
import java.util.ArrayList;


public class SelectCity {
	
	public result getResult(String startstr,String endstr)
	{
		constants c=new constants();//加载全国铁路网距离信息和结点，构建图
        System.out.println(constants.vexs.length);
       Dijkstra a =new Dijkstra();//将Dijkstra类初始化，将要记录的路径结点队列初始化
       //传入起点城市和终点城市
       int start=-1,end=-1;
       for(int j=0;j<constants.n;j++){
           if(startstr.equals(constants.vexs[j])) start=j+1;
           if(endstr.equals(constants.vexs[j])) end=j+1;       
       }
      if( start==1&&end==-1)   
           System.exit(0);
       //
       a.dijkstra(c.map, start, end);//将生成好的地图加载到Dijkstra算法里进行实现，运行过后，路径结点队列生产，最短路径所跨距离生成
       result result1=new result();//结果类，我构造的一个得到我们期许结果的类，在这个类中，Dijkstra类会将生成好的结果统统传入这个result类
       //做这个类的目的只是为了实现类的职责更加明确，增加内聚性
       //#################Dijkstra类会将生成好的结果传入这个result类的过程#################
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
