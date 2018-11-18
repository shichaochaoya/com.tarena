import java.util.Random;

/**
 * 蜜蜂，可左右上下移动
 */
public class Bee extends FlyingObject implements Award {
    private int xSpeed=1;//x坐标移动速度
    private int ySpeed=2;//y坐标移动速度
    private int awardType;//奖励类型
    public Bee(){
        this.image=ShootGame.bee;//ShootGame类加载的图片
        width=image.getWidth();//图片宽度
        height=image.getHeight();//图片高度

        y=-height;//蜜蜂的负高度

        Random rand=new Random();

         x=rand.nextInt(ShootGame.WIDTH-width);//x坐标初始化为这个范围内的随机数

        //x=100;
        //y=200;

        awardType=rand.nextInt(2);//奖励类型为2以内的随机数，即0或1
    }
    public int getType(){
        return awardType;
    }
    /**实现越界判断方法*/
    @Override
    public boolean outOfBounds(){
        return y>ShootGame.HEIGHT;
    }

    /**
     * 蜜蜂可以左右移动因此移动到屏幕最左端时向右移动，临界位置是WIDTH-width
     */
@Override
    public void step(){//可斜飞
        x+=xSpeed;
        y+=ySpeed;
        if (x>ShootGame.WIDTH-width){
            xSpeed=-1;
        }
        if (x<0){
            xSpeed=1;
        }
}
}
