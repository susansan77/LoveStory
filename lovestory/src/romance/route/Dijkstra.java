package romance.route;

import java.util.ArrayList;
/**
 *
 * @author TOSHIBA
 */
public class Dijkstra {
   ArrayList passingnodes=new ArrayList();
   int[] passnodedis;
   int shortestdis;
   int getshortestdis(){
       return shortestdis;
   }
   ArrayList getpassnodes(){
       return passingnodes;
   }
   void dijkstra(int[][] C,int start,int end){
   int[] D=new int[constants.n];          //各顶点的距离值
    int[] P=new int[constants.n];
    int [] S=new int[constants.n];
    int i,j,k=0,vl,pre;//先让k默认为0,否则第42行会报错
    int min,inf=constants.max;
    vl=start-1;
     //#################################下面这段打印语句用于调试###########################
    if(start<0&&start>constants.n&&end<0&&end>constants.n) ;
   //System.out.println("起点有误");
  //#################################
    	else
	{
		for(i=0;i<constants.n;i++)  
		{
			D[i]=C[vl][i];//vl是start城市的下标
			//D为从start点出发到可达顶点的距离值=C[vl][i]
			if(D[i]!=constants.max) P[i]=start; //当与某城市可达时，P[i]代表start城市的序号
			else P[i]=0;//否则均默认为0---？
		}
		for(i=0;i<constants.n;i++) S[i]=0;  //初始化，记录被考虑过的城市的表，
		S[vl]=1;D[vl]=0;//S[I]标为1表示的是这25个城市已经被考虑过的，start城市现在被考虑，置为1
		for(i=0;i<constants.n-1;i++)
		{
			min=inf;//初使为一个很大的值
			for(j=0;j<constants.n;j++) 
				if((S[j]==0)&&(D[j]<min))
				{
					min=D[j];k=j;//找到最小的可达城市，记录城市下标
				}
				S[k]=1;//找到最小的可达城市，被考虑，置为1
				for(j=0;j<constants.n;j++)
					if((S[j])==0&&(D[j]>D[k]+C[k][j])) //如果j下标代表的城市还没被考虑过，而且原来的距离（可能是默认的max，
						//也可能是修改过的一个值，也有可能是直达的值）比经过k点后的距离要大，那么重新修改start城市到j下标
						//代表城市的距离
					{
						D[j]=D[k]+C[k][j];
						P[j]=k+1;//可达城市的序号被记录下来，不可达的还是保持为0
					}
		}
        }
    
    //#################################下面这段打印语句用于调试###########################
//    System.out.println(D[end-1]);
//    System.out.print(constants.vexs[end-1]);
//    pre=P[end-1];
//    	while(pre!=0)   
//		{
//			System.out.print("<-"+constants.vexs[pre-1]);
//     		       pre=P[pre-1];
//		}
//      
//	System.out.println();
   
   //#####################################################################################
    //将经过的城市放入队列中
    passingnodes.add(constants.vexs[end-1]);
    pre=P[end-1];
    while(pre!=0){
        
        passingnodes.add(constants.vexs[pre-1]);
        
        pre=P[pre-1];
    }
    //到达终点的最短距离
    shortestdis=D[end-1];
    //下面是该条路径经过结点的距离
    int count=passingnodes.size();
    int x=0;
    passnodedis=new int[count];
    passnodedis[x]=D[end-1];
    int pre2=P[end-1];
     while(pre2!=0){
        x++;
        passnodedis[x]=D[pre2-1];        
        pre2=P[pre2-1];
        
    }

   }
//    public static void main(String[] args) {
//       constants c=new constants();
//        Dijkstra a =new Dijkstra();
//        a.dijkstra(c.map, 5, 1);
//        result result1=new result();
//        result1.setshortestdis(a.shortestdis);
//        result1.setpassingcities(a.passingnodes);
//        result1.setpasscitydis(a.passnodedis);
//        System.out.println(result1.choosefitcity(result1.passnodecity));
//        ;
//        //result1.setpasscitydis(a.passnodedis);
//       
//    }
}
class constants{
   public static int n=36;
   public  static int max=100000;
   public static String []vexs={"深圳","广州","南宁","昆明","柳州","贵阳","株洲",
       "南昌","福州","上海","武汉","成都","厦门","兰州","西安","郑州","徐州","天津",
       "大连","乌鲁木齐","呼和浩特","北京","沈阳","长春","哈尔滨","济南","太原","合肥",
       "长沙","南京","杭州","银川","拉萨","重庆","石家庄","西宁"};
   public int[][] map=new int[n][n] ;
   constants(){
       initmap();
       setmap();
   }
   void initmap(){
   for(int i=0;i<n;i++)
   for(int j=0;j<n;j++)
   map[i][j]=max;
   }
   void setmap(){
       // 1、深圳      2、广州     3、南宁     4、昆明      5、柳州
//6、贵阳      7、株洲     8、南昌     9、福州     10、上海
//11、武汉     12、成都    13、厦门    14、兰州     15、西安
//16、郑州     17、徐州    18、天津    19、大连     20、乌鲁木齐
//21、呼和浩特 22、北京    23、沈阳    24、长春     25、哈尔滨2
//26 济南     27 太原     28 合肥     29 长沙      30 南京
//31 杭州      32 银川    33 拉萨     34 重庆      35 石家庄
  //36 西宁
       //!!!!!!!!!南京存了两次！
map[0][1]=140;
map[1][0]=140;map[1][2]=506;map[1][6]=675;map[1][7]=673;map[1][8]=693;map[1][28]=707;
map[2][1]=506;map[2][3]=629;map[2][4]=255;map[2][28]=979;
map[3][2]=629;map[3][5]=639;map[3][11]=1100;
map[4][2]=255;map[4][5]=607;map[4][6]=672;
map[5][3]=639;map[5][4]=607;map[5][6]=902;map[5][11]=976;map[5][28]=949;
map[6][1]=675;map[6][4]=672;map[6][5]=902;map[6][7]=367;map[6][10]=409;
map[7][1]=673;map[7][6]=367;map[7][8]=622;map[7][9]=825;map[7][10]=263;map[7][27]=462;map[7][28]=419;
map[8][1]=693;map[8][7]=622;map[8][9]=609;map[8][30]=740;map[8][12]=276;
map[9][7]=825;map[9][8]=609;map[9][10]=688;map[9][15]=829;map[9][16]=651;map[9][29]=315;map[9][30]=188;//map[9][21]=1318;
map[10][6]=409;map[10][7]=263;map[10][9]=688;map[10][11]=977;map[10][14]=650;map[10][15]=534;map[10][28]=362;map[10][33]=1237;map[10][27]=362;
map[11][3]=1100;map[11][5]=976;map[11][10]=977;map[11][13]=601;map[11][14]=842;map[11][32]=3360;map[11][33]=315;map[11][35]=1400;
map[12][8]=276;
map[13][11]=601;map[13][14]=676;map[13][19]=1892;map[13][20]=1145;map[13][31]=468;map[13][35]=228;
map[14][10]=650;map[14][11]=842;map[14][13]=676;map[14][15]=511;map[14][20]=774;map[14][26]=767;map[14][33]=777;map[14][31]=806;
map[15][9]=829;map[15][10]=534;map[15][14]=511;map[15][16]=349;map[15][17]=575;map[15][34]=412;
map[15][20]=694;map[15][21]=695;map[15][25]=668;;map[15][26]=916;map[15][27]=644;map[15][33]=495;
map[16][9]=651;map[16][15]=349;map[16][17]=674;map[16][25]=319;map[16][29]=348;
map[17][15]=575;map[17][16]=674;map[17][21]=137;map[17][22]=704;map[17][25]=357;map[17][34]=387;
map[18][22]=397;
map[19][13]=1892;
map[20][13]=1145;map[20][14]=774;map[20][15]=694;map[20][21]=668;map[20][26]=632;map[20][31]=676;
map[21][15]=695;map[21][17]=137;map[21][20]=668;map[21][22]=627;map[21][34]=277;//map[21][9]=1318;
map[22][17]=704;map[22][18]=397;map[22][21]=627;map[22][23]=305;
map[23][22]=305;map[23][24]=242;
map[24][23]=242;
// 1、深圳      2、广州     3、南宁     4、昆明      5、柳州
//6、贵阳      7、株洲     8、南昌     9、福州     10、上海
//11、武汉     12、成都    13、南京    14、兰州     15、西安
//16、郑州     17、徐州    18、天津    19、大连     20、乌鲁木齐
//21、呼和浩特 22、北京    23、沈阳    24、长春     25、哈尔滨
//26 济南     27 太原     28 合肥     29 长沙      30 南京
//31 杭州      32 银川    33 拉萨     34 重庆      35 石家庄

map[25][16]=319;map[25][30]=1155;map[25][15]=668;map[25][34]=307;map[25][17]=357;
map[26][14]=767;map[26][15]=916;map[26][20]=632;map[26][34]=225;
map[27][29]=154;map[27][15]=644;map[27][10]=362;map[27][7]=462;map[27][30]=445;
map[28][10]=362;map[28][7]=419;map[28][1]=707;map[28][2]=979;map[28][5]=949;map[28][33]=1094;
map[29][27]=154;map[29][9]=315;map[29][16]=348;map[29][30]=503;
map[30][29]=503;map[30][9]=188;map[30][27]=445;map[30][8]=740;map[30][25]=503;
map[31][20]=676;map[31][14]=806;map[31][13]=468;
map[32][11]=3360;map[32][35]=1960;
map[33][14]=777;map[33][11]=315;map[33][15]=495;map[33][28]=1094;map[33][10]=1155;
map[34][25]=307;map[34][26]=225;map[34][21]=277;map[34][17]=387;map[34][15]=412;
map[35][32]=1960;map[35][11]=1400;map[35][13]=228;
   }
   
}
class result{
     ArrayList passingcities=new ArrayList();
     int[] passnodecity;
     int shortestdis;
     
     String choosefitcity(int[] D){
         int min=100000000;
         int flag=0;
         int x;
        for(int i=1;i<D.length-1;i++){
            
            if(((D[i]-D[0])*(D[i]-D[0])+(D[D.length-1]-D[i])*(D[D.length-1]-D[i]))<min) {
               
                min=(D[i]-D[0])*(D[D.length-1]-D[i]);
                flag=i;           
            }
        }
         return (passingcities.get(flag)).toString();
     }
     result(){
         passingcities=null;
         shortestdis=0;
     }
     void setpassingcities(ArrayList nodes){
         passingcities=nodes;
     }
     void setshortestdis(int a){
         shortestdis=a;
     }
     int getshortestdis(){
       return shortestdis;
   }
   ArrayList getpassnodes(){
       return passingcities;
   }
   void setpasscitydis(int[] a){
         int i=0;
         passnodecity=new int[a.length];
         for (i = 0; i < a.length; i++){
            passnodecity[i]=a[i];   
            
         }
     }
}