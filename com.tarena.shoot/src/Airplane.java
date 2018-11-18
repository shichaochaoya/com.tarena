/**
 * 敌飞机：是飞行物也是敌人
 */
public class Airplane extends FlyingObject implements Enemy {
    private int speed=2;//移动速度
    /**初始化数据*/
    public Airplane(){//构造方法，与Bee类似
        this.image=ShootGame.airplane;
        width=image.getWidth();
        height=image.getHeight();
        y=-height;
        x=(int)(Math.random()*(ShootGame.WIDTH-width));
        //x=100;
        //y=100;
    }
    public int getScore(){
        return 5;
    }
    /**实现越界判断方法*/
    @Override
    public boolean outOfBounds(){//越界处理
        return y>ShootGame.HEIGHT;
    }
    @Override
    public void step(){//移动
        y+=speed;
    }

}

