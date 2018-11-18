import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BirdGame extends JPanel {
    Bird bird;
    Column column1,column2;
    Ground ground;
    BufferedImage background;
    int score;//分数
   // boolean gameOver;
    int state;
    public static final int START =0;
    public static final int RUNNING=1;
    public static final int GAME_OVER=2;
    BufferedImage gameOverImage;
    BufferedImage startImage;

    /**初始化Birdgame的属性变量*/
    public BirdGame()throws Exception{
        state=START;
        //gameOver=false;
        startImage=ImageIO.read(getClass().getResource("start.png"));
        gameOverImage=ImageIO.read(getClass().getResource("gameOver.png"));
        bird=new Bird();
        column1=new Column(1);
        column2=new Column(2);
        ground=new Ground();
        background= ImageIO.read(getClass().getResource("bg.png"));
    }

    /**重写（修改）paint方法实现绘制*/
    public void paint(Graphics g){
        g.drawImage(background,0,0,null);
        g.drawImage(column1.image,column1.x-column1.width/2,column1.y-column1.height/2,null);
        g.drawImage(column2.image,column2.x-column2.width/2,column2.y-column2.height/2,null);
        g.drawImage(ground.image,ground.x,ground.y,null);
        //旋转绘图坐标系，API方法
        Graphics2D g2=(Graphics2D) g;
        g2.rotate(-bird.alpha,bird.x,bird.y);
        g.drawImage(bird.image,bird.x-bird.width/2,bird.y-bird.hight/2,null);
        g2.rotate(bird.alpha,bird.x,bird.y);
        //绘制分数
        Font f=new Font(Font.SANS_SERIF,Font.BOLD,40);
        g.setFont(f);
        g.drawString(""+score,40,60);
        g.setColor(Color.WHITE);
        g.drawString(""+score,40-3,60-3);
       /* if (gameOver){
            g.drawImage(gameOverImage,0,0,null);
        }*/
       //显示游戏结束代码
        switch (state){
            case GAME_OVER:
                g.drawImage(gameOverImage,50,background.getHeight()/4,null);
                break;
            case START:
                g.drawImage(startImage,background.getWidth()/4,background.getHeight()/2,null);
                break;
        }
    }
/**action方法*/
public void action()throws Exception{
    MouseListener l=new MouseAdapter() {
        //鼠标按下
        public void mousePressed(MouseEvent e){
            //鸟向上飞杨
           //bird.flappy();
            try{
                switch (state){
                    case GAME_OVER:
                        column1=new Column(1);
                        column2=new Column(2);
                        bird=new Bird();
                        score=0;
                        state=START;
                        break;
                    case START:
                        state=RUNNING;
                    case RUNNING:
                        //鸟向上飞杨
                        bird.flappy();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    };
    //将l挂接到当前面板（game）上
    addMouseListener(l);
    while (true){
        /*if (!gameOver) {
            ground.step();
            column1.step();
            column2.step();
            bird.step();
            bird.fly();
        }
        if ((bird.hit(ground)||bird.hit(column1)||bird.hit(column2))){
            gameOver=true;
        }
        //积分逻辑
        if (bird.x==column1.x||bird.x==column2.x){
            score++;
        }*/
        switch (state){
            case START:
                bird.fly();
                ground.step();
                break;
            case RUNNING:
                column1.step();
                column2.step();
                bird.step();//上下移动
                bird.fly();//挥动翅膀
                ground.step();//地面移动
                if (bird.x==column1.x||bird.x==column2.x){
                    score++;
                }
                if ((bird.hit(ground)||bird.hit(column1)||bird.hit(column2))){
                    state=GAME_OVER;
                }
                break;
        }
        repaint();
        Thread.sleep(1000/30);//休眠1/30秒，即设置屏幕刷新率为一秒三十次，调用Thread的sleep方法实现
    }
}
/**启动软件的方法*/
public static void main(String[] args) throws Exception{
    JFrame frame=new JFrame();
    BirdGame game=new BirdGame();
    frame.add(game);
    frame.setSize(440,670);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    game.action();
}

}
/**地面*/
class Ground{
    BufferedImage image;
    int x,y;
    int width;
    int height;
    public Ground()throws Exception{
        image=ImageIO.read(getClass().getResource("ground.png"));
        width=image.getWidth();
        height=image.getHeight();
        x=0;
        y=500;
        }
    //地面的类体中添加方法，地面移动一步
    public void step(){
        x--;
        if (x==-109){
            x=0;
        }
    }
}

/**柱子属性，x,y是柱子中心点的位置*/
class Column{
    BufferedImage image;
    int x,y;
    int width,height;
    /**柱子中间的缝隙*/
    int gap;
    int distance;//距离，两个柱子之间的距离

    Random random=new Random();
    /**构造器：初始化数据，n代表敌几个柱子*/
    public Column(int n)throws Exception{
        image=ImageIO.read(getClass().getResource("column.png"));
        width=image.getWidth();
        height=image.getHeight();
        gap=144;
        distance=245;
        x=550+(n-1)*distance;
        y=random.nextInt(218)+132;
    }
    public void step(){
        x--;
        if (x==-width/2){
            x=distance*2-width/2;
            y=random.nextInt(218)+132;
        }
    }
}

/**鸟类型，x,y是鸟类型的中心位置*/
class Bird{
    BufferedImage image;
    int x,y;//中心位置
    int width,hight;//图片宽和高
    int size;//鸟的大小，用于碰撞检测

    //在Bird类中增加属性，用于计算鸟的位置
    double g;//重力加速度
    double t;//两次位置的时间间隔
    double v0;//初始上抛速度
    double speed;//是当期的上抛速度
    double s;//是经过时间t以后的位移
    double alpha;//是鸟的倾角 弧度单位
    //定义一图片，是鸟的动画帧
    BufferedImage[]images;
    //是动画帧数组元素的下标位置
    int index;

    public Bird()throws Exception {
        image=ImageIO.read(getClass().getResource("0.png"));
        width=image.getWidth();
        hight=image.getHeight();
        x=132;
        y=280;
        size=40;
        g=10;
        v0=30;
        t=0.25;
        s=0;
        alpha=0;
        //创建数组，创建8个元素的数组
        //是8个空位置，没有图片对象，序号01234567
        images=new BufferedImage[8];
        for (int i = 0; i <8 ; i++) {
            images[i]=ImageIO.read(getClass().getResource(i+".png"));
        }
        index=0;
    }
    //飞翔代码
    public void fly(){
        index++;
        image=images[(index/12)%8];
    }

    //鸟类的移动方法
    public void step(){
        double v0=speed;
        s=v0*t+g*t*t/2;//计算上抛运动位移
        y=y-(int)s;//计算鸟的坐标位置
        double v=v0-g*t;//计算下次的速度
        speed=v;
        //调用API的反正切函数，计算倾角
        alpha=Math.atan(s/8);
    }
    public void flappy(){
        //重新设置初始速度，重新向上飞
        speed=v0;
    }
    //碰撞方法,地面
    public boolean hit(Ground ground){
        boolean hit=y+size/2>ground.y;
        if (hit){
            //将鸟放置的地面上
            y=ground.y-size/2;
            //使鸟落地面时，有摔倒的效果
            alpha=-3.14159265358979323/2;
        }
        return hit;
    }
    //碰撞方法,柱子
    public boolean hit(Column column){
        //检测是否在柱子范围以内
        if(x>column.x-column.width/2-size/2&&x<column.x+column.width/2+size/2){
            //检测是否在缝隙中
            if (y>column.y-column.gap/2+size/2&&y<column.y+column.gap/2-size/2){
                return false;
            }
            return true;
        }
        return false;
    }
}

